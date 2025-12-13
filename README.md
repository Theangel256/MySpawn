<p align="center">
   <a href="https://github.com/Theangel256/MySpawn/releases">
    <img width="256" height="256" src="https://github.com/user-attachments/assets/c4c54842-4cc9-4451-b70b-9ea00fdddcfb" />
    <h1 align="center">Thanks for 150K DOWNLOADS??!!</h1>
   </a>
</p>


![Features](https://i.imgur.com/Y7OmghT.png)

- Easy setup and config.
- Customizable messages.
- Player join, first join, Player leave
- Void fall, Sounds, Motd, Fireworks, Up Placeholder API!.

![Commands](https://i.imgur.com/XoJ17Cl.png)

- /setspawn - Set spawn
- /spawn - Teleport to spawn
- /myspawn, /myspawn help, /ms or /ms help - Help command
- /myspawn reload or /ms reload - Reload config **Note: when you change the language it is updated when the server is
  restarted**

![Permissions](https://i.imgur.com/Y81n0QC.png)

- myspawn.setspawn - /setspawn
- myspawn.setfirstspawn - /setfirstspawn
- myspawn.reload - /myspawn reload
- myspawn.sounds.admin or Op - Sound AdminJoin is enabled
- myspawn.update-check or Op - Notify an Update

![Installation](https://i.imgur.com/B8pEPS7.png)

- Download the plugin.
- Put MySpawn.jar into the plugin's folder.
- Start your server.
- Set spawn.
- Modify config.yml (Optional).
- Reload config (if you modified config.yml).

<center>
  
  ![Supported Software](https://fontmeme.com/permalink/190729/02635b41102f76f0b4fc5f0e1b12f91d.png)
  
  ![Spigot](https://i.imgur.com/wsKRH9m.png)
  ![PaperMC](https://i.imgur.com/9M9CiTD.png)

![Supported Versions](https://fontmeme.com/permalink/190729/7b782e0eae0d8edacda8b057fa218ae7.png)

  **1.8.8 - 1.9.x - 1.10.x - 1.11.x - 1.12.x - 1.13.x - 1.14.x - 1.21.x**</center>

![Pictures](https://i.imgur.com/Cg1Z6lR.png)

![Console](https://cdn.modrinth.com/data/PncVNOiL/images/19016c560d40f75375eb145783adf6833e8e94fb.png)
![Welcome](https://cdn.modrinth.com/data/PncVNOiL/images/6e267932d5ec1174791b5d4f1a0b9e7cfd623e61.png)

![Config](https://fontmeme.com/permalink/190729/1247f66facf111858ae3985d57ff8641.png)
<details name="config.yml">
<summary>config.yml</summary>
  
  ```yaml
  #Check for a plugin update
  Update-check: true
  Options:
    #LANGUAGES: EN ES
    Language: 'EN'
    #Send a message when the player first joins
    First-join: true
    #Send a message when the player joins
    Player-join: true
    #Send a message when the player leaves
    Player-quit: true
    #Send a list of messages when the player joins, example:
    #- '&m                                             '
    #- '&6&lWelcome to &c&lserver &a&l{player}'
    #- '&m                                             '
    Motd: true
    #Teleport a player when he joins for the first time
    Teleport-to-firstjoin: true
    #Teleport a player when he joins
    Teleport-to-join: true
  # ─────────────── VOID TELEPORT SETTINGS ───────────────
  # Automatically teleports players to spawn if they fall into the void.
  # ──────────────────────────────────────────────────────

  Void-Teleport:
    # Enable or disable void teleport feature
    Enabled: true

    # Prevents fall damage when teleported from the void
    No-Damage: true

    # Minimum Y-coordinate before the player is considered falling into the void
    # Suggested: -15 for 1.8.x — -95 for 1.14+ due to new world generation
    Trigger-Y-Level: -90

    # Choose whether to use a whitelist or blacklist for allowed worlds
    # Options: whitelist / blacklist
    World-Filter-Type: whitelist

    # List of worlds for which void teleport is allowed/blocked (based on filter type above)
    Filtered-Worlds:
      - world
      - theangel256
      - donate-me
  # ─────────────── /SPAWN TELEPORT SETTINGS ───────────────
  # Controls related to /spawn behavior, damage prevention, and cooldown.
  # ─────────────────────────────────────────────────────────

  Spawn-Teleport:
    # Prevents fall damage after using /spawn (e.g., if falling into void)
    No-Damage: true

    # Enable or disable cooldown for the /spawn command
    Cooldown-Enabled: true

    # Time in seconds before teleporting the player after using /spawn
    Cooldown-Time: 15
  Sounds:
    # ─────────────── SOUND CONFIGURATION GUIDE ───────────────
    # Check the full list of Minecraft sound names for your version:
    #   • 1.8.x - 1.12.x → https://pastebin.com/W8ZnDx3V
    #   • 1.13+          → https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html
    #
    # Format:
    #   SOUND_NAME;VOLUME;PITCH
    #
    # Values:
    #   ▸ Volume: 1 - 10   (1 = quiet, 10 = loud)
    #   ▸ Pitch:  1 - 10   (1 = deep/slow, 5 = normal, 10 = high/fast)
    #
    # Examples:
    #   ▸ BLOCK_ANVIL_LAND;10;5 → Loud volume, normal pitch
    #   ▸ BLOCK_ANVIL_LAND;5;1  → Medium volume, deep pitch (slower)
    #   ▸ BLOCK_ANVIL_LAND;1;10 → Quiet, high pitch (chipmunk style)
    # ──────────────────────────────────────────────────────────

    # Sound when using /spawn
    Spawn:
      Enabled: true
      Sound: ENTITY_ENDER_DRAGON_GROWL;7;1

    # Sound when an admin joins the server
    Admin-Join:
      Enabled: true
      Sound: BLOCK_ANVIL_LAND;5;5

    # Sound for all players on join
    Join:
      Enabled: true
      Sound: BLOCK_NOTE_BLOCK_PLING;7;5

    # Sound for a player's first time joining
    First-Join:
      Enabled: true
      Sound: ENTITY_PLAYER_LEVELUP;7;5

    # Sound when a player falls into the void
    Void-Fall:
      Enabled: true
      Sound: ENTITY_GENERIC_EXPLODE;7;5
 
  Fireworks:
    # Fireworks when the player joins
    Join:
      Enabled: true
      Power: 1            # Power level (1 to 3)
      Colors:             # List of colors (Named or Hex) RED, GREEN, BLUE, YELLOW, PURPLE, WHITE, BLACK, 
                          # PINK, ORANGE, CYAN, MAGENTA, LIME, LIGHT_BLUE, BROWN, LIGHT_GRAY, GRAY
        - "RED"
        - "GREEN"
      Type: "BALL"        # Type: BALL, BURST, STAR, CREEPER, BALL_LARGE
      Trail: true         # Whether the firework has a trail effect
      Flicker: false      # Whether the firework flickers
      No-Damage: true     # Whether the firework deals damage

    # Fireworks on first join
    First-join:
      Enabled: true
      Power: 2
      Colors:
        - "BLUE"
        - "YELLOW"
      Type: "STAR"
      Trail: true
      Flicker: false
      No-Damage: true

    # Fireworks when using /spawn command
    Spawn:
      Enabled: true
      Power: 3
      Colors:
        - "PURPLE"
        - "WHITE"
      Type: "BURST"
      Trail: true
      Flicker: false
      No-Damage: true

  Permissions:
    Update-check: 'myspawn.update-check'
    Admin-join: 'myspawn.admin'
    Reload: 'myspawn.reload'
    Set-Spawn: 'myspawn.setspawn'
    Set-FirstSpawn: 'myspawn.setfirstspawn'
    Bypass-Cooldown: 'myspawn.cooldown-bypass'
    Set-Firework: 'myspawn.setfirework'
  ```
    
</details>

<details name="Messages_EN.yml">
<summary>Messages_EN.yml</summary>
  
  ```yaml
  Messages:
    Motd:
      - '&m                                             '
      - '&6&lWelcome to &c&lserver &a&l{player}'
      - '&m                                             '
    SpawnDefined: '&aspawn defined correctly'
    UndefinedSpawn: '&cthere is no server spawn'
    Spawn: '&ayou have been teleported'
    Player-join: '&6{player} joined the server!'
    Player-quit: '&6{player} left the server!'
    First-join: '&dWelcome {player} to the server, give him the welcome'
    Voidfall: '&c&lWARNING: &7Do not fall'
    Cooldown: '&cYou must wait &e{time} &cto execute this command'
  ```
  
</details>
