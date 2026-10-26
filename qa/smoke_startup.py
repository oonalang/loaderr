"""Launch the installed APK on a disposable emulator, including RenderThread.

Usage: python3 qa/smoke_startup.py path/to/app-debug.apk
The smoke test uses a clean install, grants notification permission on API 33+,
and checks both ordinary motion and Android's reduced-motion setting. No key,
library download, or injection is involved. Logs and screenshots go to
build/startup even when launch fails.
"""
from pathlib import Path
import subprocess
import sys
import time


PACKAGE = "astral.loader"
REPORTS = Path("build/startup")

# The CI emulator's adb link drops intermittently (adb exits 255/1 with
# "device offline") right after the launch assertions pass. Retry those
# transient drops instead of failing a run that actually succeeded.
ADB_ATTEMPTS = 4


def _warn(message):
    print(f"warning: {message}", file=sys.stderr, flush=True)


def adb(*args, binary=False, attempts=ADB_ATTEMPTS):
    delay = 1.0
    for attempt in range(1, attempts + 1):
        try:
            return subprocess.run(
                ["adb", *args], check=True, capture_output=True,
                text=not binary, timeout=30,
            ).stdout
        except (subprocess.CalledProcessError, subprocess.TimeoutExpired) as exc:
            if attempt == attempts:
                raise
            code = getattr(exc, "returncode", "timeout")
            _warn(f"adb {' '.join(args)} failed ({code}); "
                  f"retry {attempt}/{attempts - 1}")
            time.sleep(delay)
            delay *= 2


def _capture_or_warn(label, suffix, args, binary=False):
    """One diagnostics artifact; never fails the run."""
    try:
        payload = adb(*args, binary=binary)
        path = REPORTS / f"{label}{suffix}"
        if binary:
            path.write_bytes(payload)
        else:
            path.write_text(payload)
    except Exception as exc:
        # Broad on purpose: nothing in diagnostics may mask a launch result.
        _warn(f"could not capture {label}{suffix}: {exc}")


def capture(label):
    # Diagnostics only: the launch assertions already ran in launch(). A dropped
    # adb link here must not turn a passing startup check into a failed job.
    _capture_or_warn(label, "-logcat.txt", ("logcat", "-d"))
    _capture_or_warn(
        label, "-activities.txt",
        ("shell", "dumpsys", "activity", "activities"),
    )
    _capture_or_warn(label, ".png", ("exec-out", "screencap", "-p"), binary=True)
    try:
        crashes = adb("logcat", "-b", "crash", "-d")
    except Exception as exc:
        _warn(f"could not read the crash buffer for {label}: {exc}")
        return
    if f"Process: {PACKAGE}" in crashes or f">>> {PACKAGE} <<<" in crashes:
        print(crashes, flush=True)


def launch(label, scale):
    for setting in ("window_animation_scale", "transition_animation_scale", "animator_duration_scale"):
        adb("shell", "settings", "put", "global", setting, scale)
    adb("shell", "am", "force-stop", PACKAGE)
    adb("logcat", "-c")
    try:
        result = adb("shell", "am", "start", "-W", "-n", f"{PACKAGE}/.SplashActivity")
        if "Status: ok" not in result:
            raise AssertionError(f"Launcher did not start: {result}")
        # Keep drawing through the delayed arrow animation and catalog timeout.
        # A single am start success only proves that SplashActivity was requested.
        deadline = time.monotonic() + 20
        while time.monotonic() < deadline:
            time.sleep(1)
            if not adb("shell", "pidof", PACKAGE).strip():
                raise AssertionError("App process disappeared during startup")
            crashes = adb("logcat", "-b", "crash", "-d")
            if f"Process: {PACKAGE}" in crashes or f">>> {PACKAGE} <<<" in crashes:
                raise AssertionError(f"App crashed:\n{crashes}")
        activities = adb("shell", "dumpsys", "activity", "activities")
        if not any(
            "ResumedActivity" in line and f"{PACKAGE}/.MainActivity" in line
            for line in activities.splitlines()
        ):
            raise AssertionError("MainActivity did not remain resumed after launch")
        print(f"PASS: {label}, MainActivity survived launch and entrance animation")
    finally:
        capture(label)


def main():
    if len(sys.argv) != 2 or not Path(sys.argv[1]).is_file():
        raise SystemExit("Usage: python3 qa/smoke_startup.py path/to/app-debug.apk")
    REPORTS.mkdir(parents=True, exist_ok=True)
    adb("install", "-r", sys.argv[1])
    # Only run this destructive reset against the disposable CI emulator.
    adb("shell", "pm", "clear", PACKAGE)
    sdk = int(adb("shell", "getprop", "ro.build.version.sdk").strip())
    if sdk >= 33:
        adb("shell", "pm", "grant", PACKAGE, "android.permission.POST_NOTIFICATIONS")
    try:
        launch("animated", "1")
        launch("reduced-motion", "0")
    finally:
        # Hygiene on a disposable emulator: a failed restore must not mask the
        # launch result or fail the job after every assertion passed.
        for setting in ("window_animation_scale", "transition_animation_scale", "animator_duration_scale"):
            try:
                adb("shell", "settings", "put", "global", setting, "1")
            except Exception as exc:
                _warn(f"could not restore {setting}: {exc}")


if __name__ == "__main__":
    main()
