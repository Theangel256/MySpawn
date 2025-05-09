package theangel256.myspawn.events;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import theangel256.myspawn.Main;
import theangel256.myspawn.util.LocationManager;
import theangel256.myspawn.util.SoundHandler;
import theangel256.myspawn.util.UpdateChecker;
import theangel256.myspawn.util.VersionUtils;

import java.util.ArrayList;
import java.util.List;

public class Join implements Listener {
    private final Main plugin;

    public Join(final Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnJoin(final PlayerJoinEvent event) {
        FileConfiguration config = plugin.getConfig();
        Player p = event.getPlayer();
        boolean firstJoinSoundEnabled = config.getBoolean("Sounds.First-join");
        boolean joinSoundEnabled = config.getBoolean("Sounds.Join");
        boolean adminJoinSoundEnabled = config.getBoolean("Sounds.Admin-join");
        boolean isAdmin = p.isOp() || p.hasPermission(config.getString("Permissions.Admin-join"));
        final List<String> motdText = Main.getMessages().getStringList("Messages.Motd");

        if (config.getBoolean("Options.Motd")) {
            for (String line : motdText) {
                p.sendMessage(color(line).replace("{player}", p.getName()));
            }
        }
        if (adminJoinSoundEnabled && isAdmin) {
            SoundHandler.playSoundToPlayer(config, "Sounds.Admin-join", p, plugin.nombre, plugin.lang);
        }
        if (p.hasPlayedBefore()) {
            if (config.getBoolean("Options.Teleport-to-join")) {
                final LocationManager spawnCoords = LocationManager.getManager();
                if (spawnCoords.getConfig().contains("Spawn.x")) {
                    final World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("Spawn.world"));
                    final double x = spawnCoords.getConfig().getDouble("Spawn.x");
                    final double y = spawnCoords.getConfig().getDouble("Spawn.y");
                    final double z = spawnCoords.getConfig().getDouble("Spawn.z");
                    final float yaw = (float) spawnCoords.getConfig().getDouble("Spawn.yaw");
                    final float pitch = (float) spawnCoords.getConfig().getDouble("Spawn.pitch");
                    final Location loc = new Location(w, x, y, z, yaw, pitch);
                    p.teleport(loc);
                }
            }
            final String joinText = Main.getMessages().getString("Messages.Player-join");
            event.setJoinMessage(null);
            if (config.getBoolean("Options.Player-join")) {
                Bukkit.broadcastMessage(color(joinText).replace("{player}", p.getName()));
            }
            if (config.getBoolean("Fireworks.Join")) {
                launchFirework(p);
            }
            if (joinSoundEnabled && !isAdmin) {
                SoundHandler.playSoundToPlayer(config, "Sounds.Join", p, plugin.nombre, plugin.lang);
            }

        } else {
            if (config.getBoolean("Options.Teleport-to-firstjoin")) {
                final LocationManager spawnCoords = LocationManager.getManager();
                if (spawnCoords.getConfig().contains("FirstSpawn.x")) {
                    final World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("FirstSpawn.world"));
                    final double x = spawnCoords.getConfig().getDouble("FirstSpawn.x");
                    final double y = spawnCoords.getConfig().getDouble("FirstSpawn.y");
                    final double z = spawnCoords.getConfig().getDouble("FirstSpawn.z");
                    final float yaw = (float) spawnCoords.getConfig().getDouble("FirstSpawn.yaw");
                    final float pitch = (float) spawnCoords.getConfig().getDouble("FirstSpawn.pitch");
                    final Location loc = new Location(w, x, y, z, yaw, pitch);
                    p.teleport(loc);
                }
            }
            if (config.getBoolean("Options.First-join")) {
                final String joinFirstText = Main.getMessages().getString("Messages.First-join");
                event.setJoinMessage(null);
                Bukkit.broadcastMessage(color(joinFirstText).replace("{player}", p.getName()));
            }
            if (config.getBoolean("Fireworks.First-join")) {
                launchFirework(p);
            }
            if (firstJoinSoundEnabled) {
                SoundHandler.playSoundToPlayer(config, "Sounds.First-join", p, plugin.nombre, plugin.lang);
            }
        }
        final String updatePermission = config.getString("Permissions.Update-check");
        if (config.getBoolean("Update-check")) {
            final UpdateChecker updater = new UpdateChecker(plugin, 64762);
            if (p.isOp() || p.hasPermission(updatePermission)) {
                try {
                    if (updater.checkForUpdates()) {
                        if (plugin.lang.equalsIgnoreCase("messages_es")) {
                            p.sendMessage(color(plugin.nombre + "&a Nueva version disponible."));
                            p.sendMessage(color(plugin.nombre + "&e Puedes descargarlo en: &fhttps://www.spigotmc.org/resources/64762"));
                        } else if (plugin.lang.equalsIgnoreCase("messages_en")) {
                            p.sendMessage(color(plugin.nombre + "&a New version available."));
                            p.sendMessage(color(plugin.nombre + "&e You can download it in: &fhttps://www.spigotmc.org/resources/64762"));
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

    private static void launchFirework(Player p) {
        EntityType fireworkType = VersionUtils.isLegacy() ? EntityType.FIREWORK : EntityType.valueOf("FIREWORK_ROCKET");
        final Firework firework = (Firework) p.getWorld().spawnEntity(p.getLocation(), fireworkType);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.setPower(0);
        List<Color> colors = new ArrayList<>();
        colors.add(Color.ORANGE);
        colors.add(Color.WHITE);
        meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(FireworkEffect.Type.BALL_LARGE).withColor(colors).build());
        firework.setFireworkMeta(meta);
    }

    private static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
