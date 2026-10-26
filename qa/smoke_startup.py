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


def adb(*args, binary=False):
    return subprocess.run(
        ["adb", *args], check=True, capture_output=True,
        text=not binary, timeout=30,
    ).stdout


def capture(label):
    (REPORTS / f"{label}-logcat.txt").write_text(adb("logcat", "-d"))
    (REPORTS / f"{label}-activities.txt").write_text(
        adb("shell", "dumpsys", "activity", "activities")
    )
    (REPORTS / f"{label}.png").write_bytes(
        adb("exec-out", "screencap", "-p", binary=True)
    )


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
        for setting in ("window_animation_scale", "transition_animation_scale", "animator_duration_scale"):
            adb("shell", "settings", "put", "global", setting, "1")


if __name__ == "__main__":
    main()
