package theangel256.myspawn.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import theangel256.myspawn.Main;
import theangel256.myspawn.util.LocationManager;
import theangel256.myspawn.util.SoundHandler;
import theangel256.myspawn.util.UpdateChecker;

import java.util.List;

import static theangel256.myspawn.Main.color;
import static theangel256.myspawn.util.FireworkHandler.launchFirework;

public class Join implements Listener {
    private final Main plugin;

    public Join(final Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnJoin(final PlayerJoinEvent event) {
        FileConfiguration config = plugin.getConfig();
        Player p = event.getPlayer();
        boolean firstJoinSoundEnabled = config.getBoolean("Sounds.First-Join.Enabled");
        boolean joinSoundEnabled = config.getBoolean("Sounds.Join.Enabled");
        boolean adminJoinSoundEnabled = config.getBoolean("Sounds.Admin-Join.Enabled");
        boolean isAdmin = p.isOp() || p.hasPermission(config.getString("Permissions.Admin-join"));
        final List<String> motdText = Main.getMessages().getStringList("Messages.Motd");

        if (config.getBoolean("Options.Motd")) {
            for (String line : motdText) {
                p.sendMessage(color(line).replace("{player}", p.getName()));
            }
        }
        if (adminJoinSoundEnabled && isAdmin) {
            SoundHandler.playSoundToPlayer(plugin, config, "Sounds.Admin-Join", p);
        }
        if (p.hasPlayedBefore()) {
            if (config.getBoolean("Options.Teleport-to-join")) {
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
                }
            }
            final String joinText = Main.getMessages().getString("Messages.Player-join");
            event.setJoinMessage(null);
            if (config.getBoolean("Options.Player-join")) {
                Bukkit.broadcastMessage(color(joinText).replace("{player}", p.getName()));
            }
            if (config.getBoolean("Fireworks.Join.Enabled")) {
                launchFirework(plugin, config, "Fireworks.Join", p);
            }
            if (joinSoundEnabled && !isAdmin) {
                SoundHandler.playSoundToPlayer(plugin, config, "Sounds.Join", p);
            }

        } else {
            if (config.getBoolean("Options.Teleport-to-firstjoin")) {
                final LocationManager spawnCoords = LocationManager.getManager();
                if (spawnCoords.getSpawnConfig().contains("FirstSpawn.x")) {
                    final World w = Bukkit.getServer()
                            .getWorld(spawnCoords.getSpawnConfig().getString("FirstSpawn.world"));
                    if (w != null) {
                        final double x = spawnCoords.getSpawnConfig().getDouble("FirstSpawn.x");
                        final double y = spawnCoords.getSpawnConfig().getDouble("FirstSpawn.y");
                        final double z = spawnCoords.getSpawnConfig().getDouble("FirstSpawn.z");
                        final float yaw = (float) spawnCoords.getSpawnConfig().getDouble("FirstSpawn.yaw");
                        final float pitch = (float) spawnCoords.getSpawnConfig().getDouble("FirstSpawn.pitch");
                        final Location loc = new Location(w, x, y, z, yaw, pitch);
                        p.teleport(loc);
                    }
                } else if (spawnCoords.getSpawnConfig().contains("Spawn.x")) {
                    final World w = Bukkit.getServer()
                            .getWorld(spawnCoords.getSpawnConfig().getString("Spawn.world"));
                    if (w != null) {
                        final double x = spawnCoords.getSpawnConfig().getDouble("Spawn.x");
                        final double y = spawnCoords.getSpawnConfig().getDouble("Spawn.y");
                        final double z = spawnCoords.getSpawnConfig().getDouble("Spawn.z");
                        final float yaw = (float) spawnCoords.getSpawnConfig().getDouble("Spawn.yaw");
                        final float pitch = (float) spawnCoords.getSpawnConfig().getDouble("Spawn.pitch");
                        final Location loc = new Location(w, x, y, z, yaw, pitch);
                        p.teleport(loc);
                    }
                }
            }
            if (config.getBoolean("Options.First-join")) {
                final String joinFirstText = Main.getMessages().getString("Messages.First-join");
                event.setJoinMessage(null);
                Bukkit.broadcastMessage(color(joinFirstText).replace("{player}", p.getName()));
            }
            if (config.getBoolean("Fireworks.First-join.Enabled")) {
                launchFirework(plugin, config, "Fireworks.First-join", p);
            }
            if (firstJoinSoundEnabled) {
                SoundHandler.playSoundToPlayer(plugin, config, "Sounds.First-Join", p);
            }
        }
        final String updatePermission = config.getString("Permissions.Update-check");
        if (config.getBoolean("Update-check")) {
            final UpdateChecker updater = new UpdateChecker(plugin, 64762);
            if (p.isOp() || p.hasPermission(updatePermission)) {
                try {
                    if (updater.checkForUpdates(plugin)) {
                        if (plugin.lang.equalsIgnoreCase("messages_es")) {
                            p.sendMessage(color(plugin.nombre + " &aNueva version disponible."));
                            p.sendMessage(
                                    color(plugin.nombre + " &ePuedes descargarlo en: &f" + updater.getResourceUrl()));
                        } else if (plugin.lang.equalsIgnoreCase("messages_en")) {
                            p.sendMessage(color(plugin.nombre + " &aNew version available."));
                            p.sendMessage(
                                    color(plugin.nombre + " &eYou can download it in: &f" + updater.getResourceUrl()));
                        }
                    }
                } catch (Exception e) {
                    String missingPageSpigot = plugin.lang.equalsIgnoreCase("messages_es")
                            ? "&c El plugin no se encuentra en la pagina de spigot"
                            : "&c The plugin is not found in the spigot page";
                    Bukkit.getConsoleSender().sendMessage(color(plugin.nombre + " " + missingPageSpigot));
                }
            }
        }
    }
}
