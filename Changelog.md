# MySpawn 2.1!

### Diffs to 2.0:

### Features:

- Refactor /setfirework command to support multiple settings and improve error handling
- Add configuration files and update plugin settings for MySpawn
- Sync latest changes with beta, alpha updates 2.1b28
- Automatically validate and suggest converting new sounds to Legacy (1.8.8) sounds when reading from
  config.yml
- Extend Configuration to Support Customization Fireworks!
- Reworked FireworkHandler to handle fireworks
- Added /SetFirework command
- Updated config.yml with new options for the fireworks & much more documentation!
- Added the following permissions 'myspawn.setfirstspawn', 'myspawn.setfirework', 'myspawn.cooldown-bypass'
- Added a /version command

### Fixes:
- Refactor command permissions for better error handling
- Update Checker now works
- Refactor duplicated codes in a single function (Like SoundHandler, FireworkHandler, etc.)
- Removed trash lines from the decompiler (2020)
- Removed unused imports
- Fixes Automatic CI Builds (GitHub Actions) to release the jar file alpha and beta versions
- Some functions moved to the main class as opposed to duplicate functions
- Using a never used function lmao
- Fixed some null pointer exceptions
- Fixes getCommand SetFirework == null
- Fixed Firework Type nulleable
- Adapt Sound Float numbers to scale of 0.0 to 10.0
- Fixes Sound Type nulleable (if you are using 1.12.x or 1.13.x, you need to change the sound type in
  config.yml)
- Added back /setfirstspawn command
- Backward compatibility between versions 1.8.x - 1.21.x has been restored.
- Tested: 1.8.8, 1.12.2, 1.21.5
- Re-structure help command
- Fixes 'myspawn.setspawn' permission
