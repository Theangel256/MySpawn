package theangel256.myspawn.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import theangel256.myspawn.Main;
import theangel256.myspawn.utils.LocationManager;
import theangel256.myspawn.utils.SoundHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Playervoid implements Listener {
    private final Main plugin;
    private final Set<UUID> teleported = new HashSet<>();

    public Playervoid(final Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnPlayerDamage(final PlayerMoveEvent e) {
        final FileConfiguration config = plugin.getConfig();
        final Player p = e.getPlayer();
        if (e.getFrom().getBlockY() == e.getTo().getBlockY())
            return;
        if (!config.getBoolean("Void-Teleport.Enabled"))
            return;
        if (e.getTo().getBlockY() > config.getInt("Void-Teleport.Trigger-Y-Level", -90))
            return;
        if (teleported.contains(p.getUniqueId()))
            return;

        if (config.getString("Void-Teleport.World-Filter-Type").equals("whitelist")
                && config.getStringList("Void-Teleport.World-Filter-Type") != null
                && !config.getStringList("Void-Teleport.Filtered-Worlds").contains(p.getWorld().getName())) {
            return;
        }
        if (config.getString("Void-Teleport.World-Filter-Type").equals("blacklist")
                && config.getStringList("Void-Teleport.World-Filter-Type") != null
                && config.getStringList("Void-Teleport.Filtered-Worlds").contains(p.getWorld().getName())) {
            return;
        }
        final LocationManager spawnCoords = LocationManager.getManager();
        if (spawnCoords.getSpawnConfig().contains("Spawn.x")) {
            final World w = Bukkit.getServer().getWorld(spawnCoords.getSpawnConfig().getString("Spawn.world"));
            final double x = spawnCoords.getSpawnConfig().getDouble("Spawn.x");
            final double y = spawnCoords.getSpawnConfig().getDouble("Spawn.y");
            final double z = spawnCoords.getSpawnConfig().getDouble("Spawn.z");
            final float yaw = (float) spawnCoords.getSpawnConfig().getDouble("Spawn.yaw");
            final float pitch = (float) spawnCoords.getSpawnConfig().getDouble("Spawn.pitch");
            final Location loc = new Location(w, x, y, z, yaw, pitch);
            p.teleport(loc);
            teleported.add(p.getUniqueId());
            Bukkit.getScheduler().runTaskLater(plugin, () -> teleported.remove(p.getUniqueId()), 60L);
            // después de 3 segundos
            if (config.getBoolean("Void-Teleport.No-Damage")) {
                p.setFallDistance(0F);
            }
            p.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', Main.getMessages().getString("Messages.Voidfall")));
            if (config.getBoolean("Sounds.Void-Fall.Enabled")) {
                SoundHandler.playSoundToPlayer(plugin, config, "Sounds.Void-Fall", p);
            }
        }
    }
}
