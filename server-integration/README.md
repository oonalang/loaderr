# FreeSHAI integration

The giveaway loader uses `https://freeshai.x10.mx`.

Account-owned libraries need the server to support `POST /loader/catalog/assigned`. Copy `server-integration/LoaderCatalog.php` to the panel's `app/Controllers/LoaderCatalog.php`, then add the route below in `app/Config/Routes.php`:

```php
$routes->post('loader/catalog/assigned', 'LoaderCatalog::assigned');
```

Preserve the existing `/loader/catalog`, `/Loader/index.php`, and `/connect` routes. The server validates license state, expiration, device registration, and ownership before returning account-owned entries. Do not distribute the panel database or `.env` with the Android project.
