// 
// Decompiled by Procyon v0.5.36
// 

package theangel256.myspawn.events;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import theangel256.myspawn.Main;
import theangel256.myspawn.util.LocationManager;
import theangel256.myspawn.util.SoundHandler;

public class Playervoid implements Listener {
    private final Main plugin;

    public Playervoid(final Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnPlayerDamage(final PlayerMoveEvent e) {
        final FileConfiguration config = this.plugin.getConfig();
        if (config.getBoolean("Options.VoidSpawn")) {
            if (e.getTo().getBlockY() > this.plugin.getConfig().getInt("Options.Void-fall", -5)) {
                return;
            }
            final Player p = e.getPlayer();
            final LocationManager spawnCoords = LocationManager.getManager();
            if (spawnCoords.getConfig().contains("Spawn.x")) {
                final World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("Spawn.world"));
                final double x = spawnCoords.getConfig().getDouble("Spawn.x");
                final double y = spawnCoords.getConfig().getDouble("Spawn.y");
                final double z = spawnCoords.getConfig().getDouble("Spawn.z");
                final float yaw = (float) spawnCoords.getConfig().getDouble("Spawn.yaw");
                final float pitch = (float) spawnCoords.getConfig().getDouble("Spawn.pitch");
                final Location loc = new Location(w, x, y, z, yaw, pitch);
                if (config.getString("Options.Worlds-option").equals("whitelist") && config.getStringList("Options.Worlds") != null && !config.getStringList("Options.Worlds").contains(p.getWorld().getName())) {
                    return;
                }
                if (config.getString("Options.Worlds-option").equals("blacklist") && config.getStringList("Options.Worlds") != null && config.getStringList("Options.Worlds").contains(p.getWorld().getName())) {
                    return;
                }
                p.teleport(loc);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getMessages().getString("Messages.Voidfall")));
                if (config.getBoolean("Sounds.Voidfall")) {
                    SoundHandler.playSoundToPlayer(config, "Sounds.Voidfall", p, plugin.nombre, plugin.lang);
                }
            }
        }
    }
}
