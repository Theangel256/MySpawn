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
import theangel256.myspawn.MySpawn;
import theangel256.myspawn.util.CooldownManager;
import theangel256.myspawn.util.CraftCooldownManager;
import theangel256.myspawn.util.LocationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandSpawn implements CommandExecutor {
    private final MySpawn plugin;
    private final CooldownManager cooldownManager;

    public CommandSpawn(final MySpawn plugin) {
        this.plugin = plugin;
        this.cooldownManager = new CraftCooldownManager(plugin);
    }

    public boolean onCommand(final CommandSender sender, final Command comando, final String label, final String[] args) {
        final FileConfiguration config = this.plugin.getConfig();
        if (sender instanceof Player) {
            final Player p = (Player) sender;
            final double timeLeft = System.currentTimeMillis() - this.cooldownManager.getCooldown(p.getUniqueId());
            if (TimeUnit.MILLISECONDS.toSeconds((long) timeLeft) >= this.cooldownManager.getDefaultCooldown()) {
                this.plugin.saveDefaultConfig();
                final LocationManager spawnCoords = LocationManager.getManager();
                Label_0592:
                {
                    if (spawnCoords.getConfig().contains("Spawn.x")) {
                        final World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("Spawn.world"));
                        final double x = spawnCoords.getConfig().getDouble("Spawn.x");
                        final double y = spawnCoords.getConfig().getDouble("Spawn.y");
                        final double z = spawnCoords.getConfig().getDouble("Spawn.z");
                        final float yaw = (float) spawnCoords.getConfig().getDouble("Spawn.yaw");
                        final float pitch = (float) spawnCoords.getConfig().getDouble("Spawn.pitch");
                        final Location loc = new Location(w, x, y, z, yaw, pitch);
                        p.teleport(loc);
                        final String tpSpawn = MySpawn.getMessages().getString("Messages.Spawn");
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', tpSpawn));
                        final String fireworkjoin = "Fireworks.Spawn";
                        if (config.getBoolean(fireworkjoin)) {
                            final Firework firework = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK_ROCKET);
                            final FireworkMeta meta = firework.getFireworkMeta();
                            meta.setPower(0);
                            final List<Color> colores = new ArrayList<Color>();
                            colores.add(Color.ORANGE);
                            colores.add(Color.WHITE);
                            meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(FireworkEffect.Type.BALL_LARGE).withColor((Iterable) colores).build());
                            firework.setFireworkMeta(meta);
                        }
                        final String sounds = "Sounds.Spawn";
                        Label_0619:
                        {
                            if (config.getBoolean(sounds)) {
                                final String path = config.getString("Sounds.Spawn-Sound");
                                final String[] separados = path.split(";");
                                try {
                                    final int volumen = Integer.valueOf(separados[1]);
                                    final float pitch2 = Float.valueOf(separados[2]);
                                    final Sound sound = Sound.valueOf(separados[0]);
                                    p.playSound(p.getLocation(), sound, (float) volumen, pitch2);
                                    break Label_0619;
                                } catch (IllegalArgumentException e) {
                                    if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.plugin.nombre) + " &cERROR: El Sonido &e" + separados[0] + " &cEs Invalido"));
                                    } else if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.plugin.nombre) + " &cERROR: The Sound &e" + separados[0] + " &cIs Invalid"));
                                    }
                                    return false;
                                }
                            }
                        }
                        this.cooldownManager.setCooldown(p.getUniqueId(), (double) System.currentTimeMillis());
                        return false;
                    }
                }
                final String undefinedSpawn = MySpawn.getMessages().getString("Messages.UndefinedSpawn");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', undefinedSpawn));
                return true;
            }
            final String CooldownMessage = MySpawn.getMessages().getString("Messages.Cooldown");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', CooldownMessage).replace("{time}", new StringBuilder(String.valueOf(Math.round(this.cooldownManager.getDefaultCooldown() - TimeUnit.MILLISECONDS.toSeconds((long) timeLeft)))).toString()));
        } else if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(this.plugin.nombre) + ChatColor.RED + " No puedes usar comandos desde la consola");
        } else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(this.plugin.nombre) + ChatColor.RED + " You can not use commands from the console");
        }
        return false;
    }
}
