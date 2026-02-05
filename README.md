# Sayna (Cool Room) ❄️📲

Industrial cold-room control app built for large refrigeration rooms used in the food industry. Sayna lets operators manage and monitor cold rooms remotely via SMS, so no one needs to physically enter the cold room. It was delivered for the TM-BAX band and their large restaurants in Iran, and it worked like magic for real-world operations. ✅

This project is complete and production-proven. A small operator could control big devices from home while resting, without being on-site.

## Why this project exists 🧊
- Avoid sending staff into extreme cold environments.
- Keep large refrigeration equipment under control at all times.
- Use SMS as a reliable, low-bandwidth channel that works anywhere.
- Centralize multi-room settings, reports, and alerts in one app.

## Key features ✨
- SMS-based remote control and monitoring (no internet required).
- Receive cold-room status reports (temperature, humidity, defrost, voltage, current, pressure).
- Manual commands for compressor, evaporator, defrost, and pressure systems.
- Alerts and notifications for errors and critical conditions.
- Multi-room support with rich room metadata (dimensions, equipment, product type).
- Multi-user access with permission levels and assignment per room.
- Pattern lock / security flow for protected access.
- Custom SMS encoding/decoding to compact structured data.

## Diagnostics & metrics covered 📊
- Phase monitoring (R/S/T) and phase failure/asymmetry detection.
- Over/under voltage per phase and compressor overload checks.
- Compressor and evaporator current thresholds.
- High gas, low gas, and oil pressure states.
- Under-zero room classification and defrost timing.

## What this app can do (capabilities table) 🧰
| Area | What it can do | Examples |
| --- | --- | --- |
| Remote Control | Send operational commands via SMS | Defrost control, compressor/evaporator toggles |
| Monitoring | Pull live room status over SMS | Temp, humidity, voltage, current, pressure |
| Safety & Alerts | Detect errors and alert operators | Phase failure, over/under voltage, overload |
| Rooms & Assets | Store cold room details | Dimensions, insulation, equipment specs |
| Users & Access | Manage who can operate what | Per-room permission levels |
| Reporting | Log and review events | Room/user reports and direct command history |
| Offline Reliability | Works without internet | GSM/SMS-only deployments |

## How it works (high level) 🧩
1. The app sends structured SMS commands to the cold-room controller.
2. The controller replies with encoded status data.
3. The app decodes and displays values, logs the report, and triggers alerts.
4. Operators can issue manual commands for urgent adjustments.

## Technology stack 🧱
- Android (min SDK 19, target SDK 26)
- SMS receivers/senders and background services
- Local persistence (SQLite via app database)
- Pattern lock UI and custom UI components

## Permissions used 🔐
- `RECEIVE_SMS`, `READ_SMS`, `SEND_SMS`, `WRITE_SMS` for SMS control and parsing
- `READ_PHONE_STATE` for device/SIM state handling
- `INTERNET` for auxiliary features if available

## Project structure 📁
- `app/src/main/java/ir/coolroom2/Activity` — UI flows and screens
- `app/src/main/java/ir/coolroom2/Sms` — SMS send/receive, encode/decode
- `app/src/main/java/ir/coolroom2/Model` — Room, user, report, settings models
- `app/src/main/java/ir/coolroom2/Utils` — background and notification helpers

## App details from code 🔎
- Package name: `ir.coolroom2`
- App name: **Cool Room**
- Version: `6.2` (versionCode `6`)
- Minimum Android: `API 19` (Android 4.4)
- Target Android: `API 26` (Android 8.0)

## Core screens (Activities) 🧭
- `Splash`, `LoginActivity`, `MainActivity`
- `AddUserActivity`, `SettingActivity`, `AlertActivity`
- `LockActivity`, `PatternActivity`, `AuthActivity`
- `AboutUsActivity`

## Data model highlights 🧱
- Cold room metadata: number, location, dimensions, insulation thickness, compressor, evaporator, condenser, product type, under-zero flag.
- Users linked to rooms with permission levels.
- Reports and settings persisted locally.

## SMS protocol notes 📡
- Incoming and outgoing SMS are encoded/decoded to compact structured data.
- SMS receivers listen for `SMS_RECEIVED` and parse controller responses.
- Manual command types include defrost, compressor, evaporator, and pressure control.

## Signals & measurements 🧪
- Temperature and humidity
- Voltage and phase monitoring (R/S/T)
- Compressor & evaporator current
- High gas, low gas, oil pressure
- Defrost timing and mode

## Build & run 🛠️
1. Open the project in Android Studio.
2. Sync Gradle and install dependencies.
3. Run on a device with SMS capability (real device recommended).

## Status ✅
Finished and deployed for real industrial cold-room operations. The system was stable, reliable, and removed the need for on-site control.

## Credits 🙌
Built for TM-BAX band and restaurant operations in Iran.
