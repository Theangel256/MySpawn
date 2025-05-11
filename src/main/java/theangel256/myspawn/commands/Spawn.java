package theangel256.myspawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import theangel256.myspawn.Main;
import theangel256.myspawn.util.CooldownManager;
import theangel256.myspawn.util.CraftCooldownManager;
import theangel256.myspawn.util.LocationManager;
import theangel256.myspawn.util.SoundHandler;

import java.util.concurrent.TimeUnit;

import static theangel256.myspawn.Main.color;
import static theangel256.myspawn.util.FireworkHandler.launchFirework;

public class Spawn implements CommandExecutor {
    private final Main plugin;
    private final CooldownManager cooldownManager;

    public Spawn(final Main plugin) {
        this.plugin = plugin;
        this.cooldownManager = new CraftCooldownManager(plugin);
    }

    public boolean onCommand(final CommandSender sender, final Command comando, final String label, final String[] args) {
        final FileConfiguration config = plugin.getConfig();
        if (sender instanceof Player p) {
            final double timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p.getUniqueId());
            if (TimeUnit.MILLISECONDS.toSeconds((long) timeLeft) >= cooldownManager.getDefaultCooldown()) {
                plugin.saveDefaultConfig();
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
                    if (config.getBoolean("Fireworks.Spawn.Enabled")) {
                        launchFirework(config, "Fireworks.Spawn", p, plugin.nombre, plugin.lang);
                    }
                    if (config.getBoolean("Sounds.Spawn")) {
                        SoundHandler.playSoundToPlayer(config, "Sounds.Spawn", p, plugin.nombre, plugin.lang);
                    }
                    p.sendMessage(color(Main.getMessages().getString("Messages.Spawn")));
                    cooldownManager.setCooldown(p.getUniqueId(), (double) System.currentTimeMillis());
                    return true; // Comando exitoso
                }
                p.sendMessage(color(Main.getMessages().getString("Messages.UndefinedSpawn")));
                return true;
            }
            final String CooldownMessage = Main.getMessages().getString("Messages.Cooldown");
            p.sendMessage(color(CooldownMessage).replace("{time}", String.valueOf(Math.round(cooldownManager.getDefaultCooldown() - TimeUnit.MILLISECONDS.toSeconds((long) timeLeft)))));
        } else if (plugin.lang.equalsIgnoreCase("messages_es")) {
            Bukkit.getConsoleSender().sendMessage(color(plugin.nombre + " &cNo puedes usar comandos desde la consola"));
        } else if (plugin.lang.equalsIgnoreCase("messages_en")) {
            Bukkit.getConsoleSender().sendMessage(color(plugin.nombre + " &cYou can not use commands from the console"));
        }
        return true;
    }

}
