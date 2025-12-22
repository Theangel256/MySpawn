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
import theangel256.myspawn.util.LocationManager;
import theangel256.myspawn.util.SoundHandler;

import static theangel256.myspawn.Main.color;
import static theangel256.myspawn.util.FireworkHandler.launchFirework;

public class Spawn implements CommandExecutor {
    private final Main plugin;
    private final CooldownManager cooldownManager;

    public Spawn(final Main plugin) {
        this.plugin = plugin;
        this.cooldownManager = new CooldownManager(plugin);
    }

    public boolean onCommand(final CommandSender sender, final Command comando, final String label,
            final String[] args) {
        final FileConfiguration config = plugin.getConfig();
        if (!(sender instanceof Player p)) {
            String notAllowedfromConsole = plugin.lang.equalsIgnoreCase("messages_es")
                    ? " &cNo puedes usar este comando desde la consola"
                    : " &cYou can not use this command from the console";
            Bukkit.getConsoleSender().sendMessage(color(plugin.nombre + " " + notAllowedfromConsole));
            return true;
        }
        boolean isCooldownEnabled = config.getBoolean("Spawn-Teleport.Cooldown-Time", true);
        double timeLeft = (System.currentTimeMillis() - cooldownManager.getCooldown(p.getUniqueId())) / 1000.0;
        boolean canBypassCooldown = p.hasPermission(config.getString("Permissions.Bypass-Cooldown"));
        if (isCooldownEnabled) {
            if (timeLeft < cooldownManager.getDefaultCooldown() && (!canBypassCooldown)) {
                String cooldownMessage = Main.getMessages().getString("Messages.Cooldown");
                String remainingTime = String.format("%.1f", cooldownManager.getDefaultCooldown() - timeLeft);
                p.sendMessage(color(cooldownMessage).replace("{time}", remainingTime));
                return true;
            }
        }
        plugin.saveDefaultConfig();
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
            if (config.getBoolean("Spawn-Teleport.No-Damage")) {
                p.setFallDistance(0F);
            }
            if (config.getBoolean("Fireworks.Spawn.Enabled")) {
                launchFirework(plugin, config, "Fireworks.Spawn", p);
            }
            if (config.getBoolean("Sounds.Spawn.Enabled")) {
                SoundHandler.playSoundToPlayer(plugin, config, "Sounds.Spawn", p);
            }
            p.sendMessage(color(Main.getMessages().getString("Messages.Spawn")));
            cooldownManager.setCooldown(p.getUniqueId(), (double) System.currentTimeMillis());
            return true;
        }
        p.sendMessage(color(Main.getMessages().getString("Messages.UndefinedSpawn")));
        return true;
    }
}
