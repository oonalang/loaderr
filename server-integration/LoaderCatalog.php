<?php

namespace App\Controllers;

use App\Libraries\LoaderCatalogFormatter;

final class LoaderCatalog extends BaseController
{
    public function index()
    {
        try {
            $rows = db_connect()->table('loader_catalog')
                ->select('name, download_url, game_package, game_type, architecture, jni_key, version_name, logo_url, status')
                ->where('owner_user_id IS NULL', null, false)
                ->orderBy('sort_order', 'ASC')->orderBy('id', 'ASC')
                ->get()->getResultArray();
            $payload = LoaderCatalogFormatter::payload($rows, site_url('connect'));
            $encoded = LoaderCatalogFormatter::encode($payload);
        } catch (\Throwable $error) {
            log_message('error', 'Unable to serve loader catalog: {error}', ['error' => $error->getMessage()]);
            return $this->response->setStatusCode(503)->setHeader('Cache-Control', 'no-store')
                ->setBody('Catalog unavailable');
        }

        return $this->response->setHeader('Content-Type', 'text/plain; charset=UTF-8')
            ->setHeader('Cache-Control', 'no-store')
            ->setHeader('X-Content-Type-Options', 'nosniff')
            ->setBody($encoded);
    }

    public function assigned()
    {
        $key = trim((string) $this->request->getPost('user_key'));
        $serial = trim((string) $this->request->getPost('serial'));
        $game = trim((string) $this->request->getPost('game'));
        if (! preg_match('/^[A-Za-z0-9_-]{1,190}$/D', $key)
            || ! preg_match('/^[A-Za-z0-9_-]{1,190}$/D', $serial)
            || ! preg_match('/^[A-Za-z0-9_-]{1,32}$/D', $game)) {
            return $this->reject('Invalid request');
        }
        try {
            $db = db_connect();
            $keyRow = $db->table('keys_code')
                ->select('game, registrator, status, expired_date, devices')
                ->where('user_key', $key)->where('game', $game)->get()->getRowArray();
            if (! $keyRow || (int) $keyRow['status'] !== 1) {
                return $this->reject('License unavailable');
            }
            if (empty($keyRow['expired_date']) || strtotime((string) $keyRow['expired_date']) <= time()) {
                return $this->reject('License expired or not activated');
            }
            $registeredDevices = array_filter(array_map('trim', explode(',', (string) ($keyRow['devices'] ?? ''))));
            if (! in_array($serial, $registeredDevices, true)) {
                return $this->reject('Device has not been authorized');
            }
            $owner = $db->table('users')->select('id_users')
                ->where('username', (string) $keyRow['registrator'])->get()->getRowArray();
            if (! $owner) {
                return $this->reject('Library owner unavailable');
            }
            $rows = $db->table('loader_catalog')
                ->select('name, download_url, game_package, game_type, architecture, jni_key, version_name, logo_url, status')
                ->where('owner_user_id', (int) $owner['id_users'])
                ->where('game_type', $game)
                ->whereIn('status', ['ACTIVE', 'MAINTENANCE'])
                ->orderBy('sort_order', 'ASC')->orderBy('id', 'ASC')
                ->get()->getResultArray();
            return $this->response->setHeader('Cache-Control', 'no-store')->setJSON([
                'status' => true,
                'game' => $game,
                'catalog' => LoaderCatalogFormatter::payload($rows, site_url('connect')),
            ]);
        } catch (\Throwable $error) {
            log_message('error', 'Assigned library lookup failed: {error}', ['error' => $error->getMessage()]);
            return $this->response->setStatusCode(503)->setHeader('Cache-Control', 'no-store')
                ->setJSON(['status' => false, 'reason' => 'Catalog service unavailable']);
        }
    }

    private function reject(string $reason)
    {
        return $this->response->setStatusCode(403)->setHeader('Cache-Control', 'no-store')
            ->setJSON(['status' => false, 'reason' => $reason]);
    }
}
