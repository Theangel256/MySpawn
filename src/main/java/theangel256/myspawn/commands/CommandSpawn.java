package theangel256.myspawn.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import theangel256.myspawn.Main;
import theangel256.myspawn.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandSpawn implements CommandExecutor {
    private final Main plugin;
    private final CooldownManager cooldownManager;

    public CommandSpawn(final Main plugin) {
        this.plugin = plugin;
        this.cooldownManager = new CraftCooldownManager(plugin);
    }

    public boolean onCommand(final CommandSender sender, final Command comando, final String label, final String[] args) {
        final FileConfiguration config = this.plugin.getConfig();
        if (sender instanceof Player p) {
            final double timeLeft = System.currentTimeMillis() - this.cooldownManager.getCooldown(p.getUniqueId());
            if (TimeUnit.MILLISECONDS.toSeconds((long) timeLeft) >= this.cooldownManager.getDefaultCooldown()) {
                this.plugin.saveDefaultConfig();
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
                    p.sendMessage(color(Main.getMessages().getString("Messages.Spawn")));

                    if (config.getBoolean("Fireworks.Spawn")) {
                        EntityType fireworkType = VersionUtils.isLegacy() ? EntityType.FIREWORK : EntityType.valueOf("FIREWORK_ROCKET");
                        final Firework firework = (Firework) p.getWorld().spawnEntity(p.getLocation(), fireworkType);
                        FireworkMeta meta = firework.getFireworkMeta();
                        meta.setPower(0);
                        final List<Color> colores = new ArrayList<>();
                        colores.add(Color.ORANGE);
                        colores.add(Color.WHITE);
                        meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(FireworkEffect.Type.BALL_LARGE).withColor(colores).build());
                        firework.setFireworkMeta(meta);
                    }
                    if (config.getBoolean("Sounds.Spawn")) {
                        SoundHandler.playSoundToPlayer(config, "Sounds.Spawn", p, plugin.nombre, plugin.lang);
                    }
                    this.cooldownManager.setCooldown(p.getUniqueId(), (double) System.currentTimeMillis());
                    return true; // Comando exitoso
                }

                p.sendMessage(color(Main.getMessages().getString("Messages.UndefinedSpawn")));
                return true;
            }

            final String CooldownMessage = Main.getMessages().getString("Messages.Cooldown");
            p.sendMessage(color(CooldownMessage).replace("{time}", String.valueOf(Math.round(this.cooldownManager.getDefaultCooldown() - TimeUnit.MILLISECONDS.toSeconds((long) timeLeft)))));
        } else if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
            Bukkit.getConsoleSender().sendMessage(color(this.plugin.nombre + " &cNo puedes usar comandos desde la consola"));
        } else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
            Bukkit.getConsoleSender().sendMessage(color(this.plugin.nombre + " &cYou can not use commands from the console"));
        }
        return true;
    }

    private static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
