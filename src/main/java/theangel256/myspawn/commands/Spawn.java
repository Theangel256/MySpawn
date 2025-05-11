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
            boolean isCooldownEnabled = config.getBoolean("Options.Cooldown", true);
            double timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p.getUniqueId());
            double cooldownInSeconds = TimeUnit.MILLISECONDS.toSeconds((long) timeLeft);
            boolean canBypassCooldown = p.hasPermission(config.getString("Permissions.Bypass-Cooldown"));
            if (isCooldownEnabled) {
                if (cooldownInSeconds < cooldownManager.getDefaultCooldown() && (!canBypassCooldown)) {
                    String cooldownMessage = Main.getMessages().getString("Messages.Cooldown");
                    p.sendMessage(color(cooldownMessage).replace("{time}", String.valueOf(cooldownManager.getDefaultCooldown() - cooldownInSeconds)));
                    return true;
                }
            }
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
                return true;
            }
            p.sendMessage(color(Main.getMessages().getString("Messages.UndefinedSpawn")));
        } else {
            String notAllowedfromConsole = plugin.lang.equalsIgnoreCase("messages_es")
                    ? " &cNo puedes usar este comando desde la consola"
                    : " &cYou can not use this command from the console";
            Bukkit.getConsoleSender().sendMessage(color(plugin.nombre + " " + notAllowedfromConsole));
        }
        return true;
    }

}
