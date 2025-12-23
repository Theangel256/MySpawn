package theangel256.myspawn.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import theangel256.myspawn.Main;
import theangel256.myspawn.utils.LocationManager;

public class Respawn implements Listener {
    private final Main plugin;

    public Respawn(final Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent event) {
        FileConfiguration config = plugin.getConfig();
        // Player p = event.getPlayer();

        if (config.getBoolean("Options.Teleport-to-respawn")) {
            final LocationManager spawnCoords = LocationManager.getManager();
            if (spawnCoords.getSpawnConfig().contains("Spawn.x")) {
                final World w = Bukkit.getServer().getWorld(spawnCoords.getSpawnConfig().getString("Spawn.world"));
                if (w != null) {
                    final double x = spawnCoords.getSpawnConfig().getDouble("Spawn.x");
                    final double y = spawnCoords.getSpawnConfig().getDouble("Spawn.y");
                    final double z = spawnCoords.getSpawnConfig().getDouble("Spawn.z");
                    final float yaw = (float) spawnCoords.getSpawnConfig().getDouble("Spawn.yaw");
                    final float pitch = (float) spawnCoords.getSpawnConfig().getDouble("Spawn.pitch");
                    final Location loc = new Location(w, x, y, z, yaw, pitch);
                    event.setRespawnLocation(loc);
                }
            }
        }
    }
}
