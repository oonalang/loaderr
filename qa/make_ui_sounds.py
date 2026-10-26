"""Generates the bundled UI cues. Self-authored synthesis, no sampled assets:
- ui_tap:    ~45ms soft tactile tick (low body + short high transient), quiet
- ui_success:two soft bell notes, only played after a verified redemption
- ui_error:  one low, unresolved two-step tone for real failures
- ui_ready:  a warm short chord after preparation finishes
Everything peaks well below full scale so nothing is loud or gamey, and the
files are mono 44.1k 16-bit PCM WAV, a few KB each.
"""
import math
import struct
from pathlib import Path

RATE = 44100


def env(t, attack, length):
    a = min(t / attack, 1.0) if attack > 0 else 1.0
    decay = math.exp(-t / length)
    return a * decay


def note(freq, dur, amp, attack=0.004, decay=0.09, tone=1.0, bend=1.0):
    n = int(dur * RATE)
    out = [0.0] * n
    for i in range(n):
        t = i / RATE
        f = freq * (1.0 + (bend - 1.0) * (t / dur))
        out[i] = amp * env(t, attack, decay) * (
            math.sin(2 * math.pi * f * t) + tone * 0.35 * math.sin(2 * math.pi * 2 * f * t)
        )
    return out


def mixinto(base, extra, offset_seconds):
    start = int(offset_seconds * RATE)
    need = start + len(extra)
    if need > len(base):
        base.extend([0.0] * (need - len(base)))
    for i, sample in enumerate(extra):
        base[start + i] += sample


def peak_norm(samples, peak):
    m = max(1e-9, max(abs(s) for s in samples))
    scale = peak / m if m > peak else 1.0
    return [s * scale for s in samples]


def write_wav(path, samples):
    peak = max(1e-9, max(abs(s) for s in samples))
    norm = 0.9 / peak
    data = b"".join(struct.pack("<h", int(max(-1.0, min(1.0, s * norm)) * 32767)) for s in samples)
    with open(path, "wb") as f:
        f.write(b"RIFF")
        f.write(struct.pack("<I", 36 + len(data)))
        f.write(b"WAVEfmt ")
        f.write(struct.pack("<IHHIIHH", 16, 1, 1, RATE, RATE * 2, 2, 16))
        f.write(b"data")
        f.write(struct.pack("<I", len(data)))
        f.write(data)


RAW = Path(__file__).resolve().parents[1] / "app/src/main/res/raw"
RAW.mkdir(parents=True, exist_ok=True)

# TAP: a 45ms lingual tick — a soft low thud plus one short high transient.
tap = note(1180, 0.028, 0.16, attack=0.0015, decay=0.007, tone=0.4)
mixinto(tap, note(240, 0.05, 0.2, attack=0.002, decay=0.022, tone=0.15), 0.0)
write_wav(RAW / "ui_tap.wav", peak_norm(tap, 0.16))

# SUCCESS: two quiet bell notes a fourth apart, no wobble, no tail louder than the note.
success = []
mixinto(success, note(659.3, 0.28, 0.5, attack=0.006, decay=0.11, tone=0.5), 0.0)
mixinto(success, note(880, 0.34, 0.5, attack=0.006, decay=0.14, tone=0.5), 0.11)
write_wav(RAW / "ui_success.wav", peak_norm(success, 0.2))

# ERROR: one low two-step motion, resolved as "not accepted", still under 250ms.
error = []
mixinto(error, note(233, 0.16, 0.4, attack=0.004, decay=0.09, tone=0.2), 0.0)
mixinto(error, note(196, 0.17, 0.4, attack=0.004, decay=0.10, tone=0.2), 0.08)
write_wav(RAW / "ui_error.wav", peak_norm(error, 0.2))

# READY: a short warm triad, arpeggiated a few ms apart, soft attacks.
ready = []
for i, freq in enumerate((554.4, 659.3, 880.0)):
    mixinto(ready, note(freq, 0.3, 0.34, attack=0.01, decay=0.16, tone=0.4), i * 0.055)
write_wav(RAW / "ui_ready.wav", peak_norm(ready, 0.2))

print("wrote:", [p.name for p in sorted(RAW.glob("ui_*.wav"))])
