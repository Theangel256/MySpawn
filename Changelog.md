# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.2.3] - 2024-12-24

### Fixed
- **Critical:** Fixed firework `No-Damage: true` configuration not working on first `/spawn` execution after server startup
  - Players would receive damage from fireworks on first use, even with `No-Damage: true` configured
  - Root cause: Configuration was accessed before `saveDefaultConfig()` was called during plugin initialization
  - Solution: Moved configuration initialization to proper order in `onEnable()` method
  - Now works correctly from the very first execution, even on fresh installations

### Changed
- Improved plugin initialization order to ensure all configurations are loaded before use
- Removed redundant `saveDefaultConfig()` call from Spawn command
- Added defensive default value for `Options.Language` configuration to prevent potential NPE

---

## [2.2.2] - 2024-12-23

### Fixed
- Resolved a `NullPointerException` in commands when file messages was missing
- Configuration files (`locations.yml`, `messages_en.yml`, `config.yml`) are now automatically restored if deleted when the plugin reloads

### Changed
- **Refactoring:** Renamed `Spawn.yml` to `locations.yml`
- **Refactoring:** Renamed message files to lowercase format (`messages_en.yml`, `messages_es.yml`)
- **Refactoring:** Renamed `theangel256.myspawn.events` package to `theangel256.myspawn.listeners` to follow standard conventions
- Updated README.md documentation

---

## [2.2.1] - 2024-12-22

### Added
- Implemented teleport on respawn feature (configurable via `Options.Teleport-to-respawn`)
- Added fallback logic to teleport new players to main spawn if "First Spawn" is not set

### Changed
- Cooldown messages now display precise decimal seconds (e.g., "4.5s") instead of rounding to integers
- Updated SnakeYAML and Gson dependencies

### Fixed
- Players are now correctly teleported to spawn upon respawning when enabled in config

---

## [2.2] - 2024-12-13 - "The Architecture Update" 🏗️

### Added
- **New:** Centralized configuration architecture with `LocationManager` as the central brain for all config I/O
- **New:** Update checker with version utility classes
- **New:** Smart sound engine with automatic legacy (1.8-1.12) fallback
- **New:** Firework `No-Damage` sub-option to prevent player damage from fireworks
- **New:** Hot-reloading support - `/myspawn reload` now atomically reloads all configs without server restart
- **New:** Dynamic language switching - changing `Options.Language` and reloading switches language in real-time
- Added `api-version: 1.13` to `plugin.yml` to suppress legacy warnings on modern servers

### Changed
- **Refactor:** Centralized all config logic into `LocationManager`
- **Refactor:** Isolated `Main` class from direct file management
- **Refactor:** Made `PluginConfig` mutable to support runtime reloading
- Enhanced `VersionUtils` with semantic version analysis for accurate legacy detection (1.7-1.12)

### Fixed
- **Critical:** Fixed firework damage issue where fireworks could damage players even with `No-Damage` enabled
  - Now uses persistent metadata (`myspawn_nodamage`) to guarantee safety
- Resolved `IllegalArgumentException` for sounds on 1.13+ servers
- Fixed race conditions in firework damage negation
- Perfect sound playback on any server version from 1.8.8 to 1.21.x

---

## [2.1.1] - 2024-05-11 - "Minor Changes & Bug Fixes Update"

### Added
- Added void fall damage prevention option when teleporting to spawn location
- Better structure for `config.yml` file with added comments and organized sections
- ⚠️ **Breaking Change:** Requires config.yml regeneration

### Fixed
- Fixed spawn teleportation on join option not working correctly
- Plugin now works correctly on 1.21.x with Java 17+
- Fixed sounds not working correctly in versions 1.9+ through 1.12.x

### Known Issues
- Fireworks make damage to the player (⚠️ Fixed in v2.2)

---

## [2.1] - 2024-05-11

### Added
- **New Command:** `/setfirework` - Configure firework effects with multiple settings
- **New Feature:** Customizable fireworks system with configuration options
- Added new permissions: `myspawn.setfirstspawn`, `myspawn.setfirework`, `myspawn.cooldown-bypass`
- Automatic validation and conversion of modern sounds to legacy (1.8.8) format
- Extended configuration to support full firework customization
- Added `/version` command

### Changed
- Refactored `/setfirework` command to support multiple settings and improve error handling
- Reworked `FireworkHandler` to handle fireworks more efficiently
- Updated `config.yml` with extensive documentation for fireworks and new options
- Re-structured help command for better clarity
- Adapted sound float numbers to scale of 0.0 to 10.0

### Fixed
- **Critical:** Update checker now works correctly
- Refactored command permissions for better error handling
- Refactored duplicated code into single functions (`SoundHandler`, `FireworkHandler`, etc.)
- Removed decompiler trash lines from 2020 codebase
- Removed unused imports
- Fixed automatic CI builds (GitHub Actions) for alpha and beta releases
- Fixed `NullPointerException` issues in various commands
- Fixed `getCommand SetFirework == null` error
- Fixed firework type nullable issue
- Fixed sound type nullable (backward compatibility for 1.12.x and 1.13.x)
- Added back `/setfirstspawn` command
- **Backward compatibility:** Restored compatibility between versions 1.8.x - 1.21.x
- Fixed `myspawn.setspawn` permission

### Tested On
- Minecraft 1.8.8
- Minecraft 1.12.2  
- Minecraft 1.21.x

---

## Version Compatibility

| MySpawn Version | Minecraft Versions | Java Version |
|-----------------|-------------------|--------------|
| 2.2.3           | 1.8.8 - 1.21.x    | 17+          |
| 2.2.2           | 1.8.8 - 1.21.x    | 17+          |
| 2.2.1           | 1.8.8 - 1.21.x    | 17+          |
| 2.2             | 1.8.8 - 1.21.x    | 17+          |
| 2.1.1           | 1.8.8 - 1.21.x    | 17+          |
| 2.1             | 1.8.8 - 1.21.x    | 17+          |

---

## Links

- [GitHub Repository](https://github.com/Theangel256/MySpawn)
- [Spigot Resource](https://www.spigotmc.org/resources/myspawn.64762/)
- [Issue Tracker](https://github.com/Theangel256/MySpawn/issues)
