# MySpawn Plugin

**Thanks for 5K downloads!!**

![](https://i.imgur.com/Y7OmghT.png)

- Easy setup and config.
- Customizable messages.
- Player join, first join, Player leave
- Void fall, Sounds, Motd, Fireworks, Up Placeholder API!.

![](https://i.imgur.com/XoJ17Cl.png)

- /setspawn - Set spawn
- /spawn - Teleport to spawn
- /myspawn, /myspawn help, /ms or /ms help - Help command
- /myspawn reload or /ms reload - Reload config **Note: when you change the language it is updated when the server is
  restarted**

![](https://i.imgur.com/Y81n0QC.png)

- myspawn.setspawn - /setspawn
- myspawn.setfirstspawn - /setfirstspawn
- myspawn.reload - /myspawn reload
- myspawn.sounds.admin or Op - Sound AdminJoin is enabled
- myspawn.update-check or Op - Notify an Update

![](https://i.imgur.com/B8pEPS7.png)

- Download the plugin.
- Put MySpawn.jar into the plugin's folder.
- Start your server.
- Set spawn.
- Modify config.yml (Optional).
- Reload config (if you modified config.yml).

![](https://fontmeme.com/permalink/190729/02635b41102f76f0b4fc5f0e1b12f91d.png)

![](https://i.imgur.com/wsKRH9m.png)  ![](https://i.imgur.com/9M9CiTD.png)

![](https://fontmeme.com/permalink/190729/7b782e0eae0d8edacda8b057fa218ae7.png)

**1.8.8 - 1.9.x - 1.10.x - 1.11.x - 1.12.x - 1.13.x - 1.14.x**

![](https://i.imgur.com/Cg1Z6lR.png)

![](https://i.imgur.com/ePaX53K.png)

![](https://i.imgur.com/nxDq7wQ.png)

![](https://fontmeme.com/permalink/190729/1247f66facf111858ae3985d57ff8641.png)
Config.yml

    #Check for a plugin update 
    Update-check: true
    Options:
        #LANGUAGES: EN ES
        Language: 'ES'
        #Send a message when the player first joins
        First-join: true
        #Send a message when the player joins
        Player-join: true
        #Send a message when the player leaves
        Player-quit: true
        #Send a list of messages when the player joins, example:
        #- '&m                                             '
        #- '&6&lWelcome to &c&lserver &a&l{player} '
        #- '&m                                             '
        Motd: true
        #Teleport a player when he first joined
        Teleport-to-firstjoin: true
        #Teleport a player when he joins
        Teleport-to-join: true
        #Teleport a player when he falls into the void
        VoidSpawn: true
        # whitelist or blacklist
        Worlds-option: 'whitelist'
        #List of worlds
        Worlds:
        - world
        #The amount of blocks before the player falls into the void
        # Void-fall: -15 for 1.8.x
        # Void-fall: -95 for 1.14.x+ in this version change due to the new world generation
        # Try by yourself
        Void-fall: -95
        #Time in Seconds - ONLY 1.8.x
        Spawn-Cooldown: 15
    Sounds:
        # You can check here the whole list of minecraft sounds.
        # 1.8.x - 1.12.x https://pastebin.com/W8ZnDx3V
        # 1.13.x - 1.14.x https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html
        #How to use Sound;Volume(10);pitch(recommended leave it in 2)
        Spawn: true
        Spawn-Sound: ENTITY_ENDER_DRAGON_GROWL;3;2
        Admin-join: true
        Admin-join-Sound: BLOCK_ANVIL_LAND;10;2
        Join: true
        Join-Sound: BLOCK_NOTE_BLOCK_PLING;10;2
        First-join: true
        First-join-Sound: WITHER_SPAWN;3;2
        Voidfall: true
        Voidfall-Sound: ENTITY_GENERIC_EXPLODE;3;2
    Fireworks:
        Join: true
        First-join: true
        Spawn: true
    Permissions:
        Update-check: 'myspawn.update-check'
        Admin-join: 'myspawn.admin'
        Reload: 'myspawn.reload'
        Set-Spawn: 'myspawn.setspawn'

Messages_EN.yml

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