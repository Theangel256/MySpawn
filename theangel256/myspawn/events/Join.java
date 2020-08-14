// 
// Decompiled by Procyon v0.5.36
// 

package theangel256.myspawn.events;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.meta.FireworkMeta;
import java.util.List;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import theangel256.myspawn.util.UpdateChecker;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect;
import org.bukkit.Color;
import java.util.ArrayList;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import theangel256.myspawn.util.LocationManager;
import org.bukkit.event.player.PlayerJoinEvent;
import theangel256.myspawn.MySpawn;
import org.bukkit.event.Listener;

public class Join implements Listener
{
    private MySpawn plugin;
    
    public Join(final MySpawn plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void OnJoin(final PlayerJoinEvent event) {
        final FileConfiguration config = this.plugin.getConfig();
        final Player p = event.getPlayer();
        if (p.hasPlayedBefore()) {
            if (config.getBoolean("Options.Teleport-to-join")) {
                final LocationManager spawnCoords = LocationManager.getManager();
                if (spawnCoords.getConfig().contains("Spawn.x")) {
                    final World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("Spawn.world"));
                    final double x = spawnCoords.getConfig().getDouble("Spawn.x");
                    final double y = spawnCoords.getConfig().getDouble("Spawn.y");
                    final double z = spawnCoords.getConfig().getDouble("Spawn.z");
                    final float yaw = (float)spawnCoords.getConfig().getDouble("Spawn.yaw");
                    final float pitch = (float)spawnCoords.getConfig().getDouble("Spawn.pitch");
                    final Location loc = new Location(w, x, y, z, yaw, pitch);
                    p.teleport(loc);
                }
            }
        }
        else if (config.getBoolean("Options.Teleport-to-firstjoin")) {
            final LocationManager spawnCoords = LocationManager.getManager();
            if (spawnCoords.getConfig().contains("FirstSpawn.x")) {
                final World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("FirstSpawn.world"));
                final double x = spawnCoords.getConfig().getDouble("FirstSpawn.x");
                final double y = spawnCoords.getConfig().getDouble("FirstSpawn.y");
                final double z = spawnCoords.getConfig().getDouble("FirstSpawn.z");
                final float yaw = (float)spawnCoords.getConfig().getDouble("FirstSpawn.yaw");
                final float pitch = (float)spawnCoords.getConfig().getDouble("FirstSpawn.pitch");
                final Location loc = new Location(w, x, y, z, yaw, pitch);
                p.teleport(loc);
            }
        }
        final List<String> motdText = MySpawn.getMessages().getStringList("Messages.Motd");
        if (config.getBoolean("Options.Motd")) {
            for (int i = 0; i < motdText.size(); ++i) {
                final String texto = motdText.get(i);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', texto).replace("{player}", event.getPlayer().getName()));
            }
        }
        if (p.hasPlayedBefore()) {
            final String joinText = MySpawn.getMessages().getString("Messages.Player-join");
            event.setJoinMessage((String)null);
            if (config.getBoolean("Options.Player-join")) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', joinText).replace("{player}", event.getPlayer().getName()));
            }
            if (config.getBoolean("Fireworks.Join")) {
                final Firework firework = (Firework)p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                final FireworkMeta meta = firework.getFireworkMeta();
                meta.setPower(0);
                final List<Color> colores = new ArrayList<Color>();
                colores.add(Color.ORANGE);
                colores.add(Color.WHITE);
                meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(FireworkEffect.Type.BALL_LARGE).withColor((Iterable)colores).build());
                firework.setFireworkMeta(meta);
            }
            if (config.getBoolean("Sounds.Join")) {
                final String SoundJoin = config.getString("Sounds.Join-Sound");
                final String[] separados = SoundJoin.split(";");
                try {
                    final int volumen = Integer.valueOf(separados[1]);
                    final float pitch2 = Float.valueOf(separados[2]);
                    final Sound sound = Sound.valueOf(separados[0]);
                    p.playSound(p.getLocation(), sound, (float)volumen, pitch2);
                }
                catch (IllegalArgumentException e) {
                    if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.plugin.nombre) + " &cERROR: El Sonido &e" + separados[0] + " &cEs Invalido"));
                    }
                    else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.plugin.nombre) + " &cERROR: The Sound &e" + separados[0] + " &cIs Invalid"));
                    }
                }
            }
        }
        else {
            if (config.getBoolean("Options.First-join")) {
                final String joinFirstText = MySpawn.getMessages().getString("Messages.First-join");
                event.setJoinMessage((String)null);
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', joinFirstText).replace("{player}", event.getPlayer().getName()));
            }
            if (config.getBoolean("Fireworks.First-join")) {
                final Firework firework2 = (Firework)p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                final FireworkMeta meta2 = firework2.getFireworkMeta();
                meta2.setPower(0);
                final List<Color> colores2 = new ArrayList<Color>();
                colores2.add(Color.ORANGE);
                colores2.add(Color.WHITE);
                meta2.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(FireworkEffect.Type.BALL_LARGE).withColor((Iterable)colores2).build());
                firework2.setFireworkMeta(meta2);
            }
            if (config.getBoolean("Sounds.First-join")) {
                final String soundFirstJoin = config.getString("Sounds.First-join-Sound");
                final String[] separados2 = soundFirstJoin.split(";");
                try {
                    final int volumen2 = Integer.valueOf(separados2[1]);
                    final float pitch3 = Float.valueOf(separados2[2]);
                    final Sound sound2 = Sound.valueOf(separados2[0]);
                    p.playSound(p.getLocation(), sound2, (float)volumen2, pitch3);
                }
                catch (IllegalArgumentException e2) {
                    if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.plugin.nombre) + " &cERROR: El Sonido &e" + separados2[0] + " &cEs Invalido"));
                    }
                    else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.plugin.nombre) + " &cERROR: The Sound &e" + separados2[0] + " &cIs Invalid"));
                    }
                }
            }
        }
        final String updatePermission = config.getString("Permissions.Update-check");
        if (config.getBoolean("Options.Update-check")) {
            if (!p.isOp()) {
                if (!p.hasPermission(updatePermission)) {
                    return;
                }
            }
            try {
                final UpdateChecker updater = new UpdateChecker(this.plugin, 64762);
                if (updater.checkForUpdates()) {
                    if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(this.plugin.nombre) + ChatColor.GREEN + " Nueva version disponible.");
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(this.plugin.nombre) + ChatColor.YELLOW + " Puedes descargarlo en: " + ChatColor.WHITE + "https://www.spigotmc.org/resources/64762");
                    }
                    else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(this.plugin.nombre) + ChatColor.GREEN + " New version available.");
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(this.plugin.nombre) + ChatColor.YELLOW + " You can download it in: " + ChatColor.WHITE + "https://www.spigotmc.org/resources/64762");
                    }
                }
            }
            catch (Exception e3) {
                if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(this.plugin.nombre) + ChatColor.RED + " El plugin no se encuentra en la pagina de spigot");
                }
                else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(this.plugin.nombre) + ChatColor.RED + " The plugin is not found in the spigot page");
                }
            }
        }
    }
}
