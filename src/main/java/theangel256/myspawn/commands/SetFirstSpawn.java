package theangel256.myspawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import theangel256.myspawn.Main;
import theangel256.myspawn.util.LocationManager;

import java.util.Objects;

public class SetFirstSpawn implements CommandExecutor {
    private final Main plugin;

    public SetFirstSpawn(final Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(final CommandSender sender, final Command comando, final String label, final String[] args) {

        final FileConfiguration config = plugin.getConfig();
        final String setspawn = config.getString("Permissions.Set-FirstSpawn");
        if (!sender.hasPermission(setspawn)) {
            String missingReloadPermissions = plugin.lang.equalsIgnoreCase("messages_es")
                    ? "&c Necesitas el permiso &a" + setspawn + "&c para acceder al comando"
                    : "&c You need permission &a" + setspawn + "&c to access the command";
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.nombre + " " + missingReloadPermissions));
            return true;
        }
        if (sender instanceof Player p) {
            LocationManager spawnCoords1 = LocationManager.getManager();
            spawnCoords1.getConfig().set("FirstSpawn.world", Objects.requireNonNull(p.getLocation().getWorld()).getName());
            spawnCoords1.getConfig().set("FirstSpawn.x", p.getLocation().getX());
            spawnCoords1.getConfig().set("FirstSpawn.y", p.getLocation().getY());
            spawnCoords1.getConfig().set("FirstSpawn.z", p.getLocation().getZ());
            spawnCoords1.getConfig().set("FirstSpawn.yaw", p.getLocation().getYaw());
            spawnCoords1.getConfig().set("FirstSpawn.pitch", p.getLocation().getPitch());
            spawnCoords1.saveConfig();
            final String setSpawnText = Main.getMessages().getString("Messages.SpawnDefined");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', setSpawnText));
        } else if (plugin.lang.equalsIgnoreCase("messages_es")) {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(plugin.nombre) + ChatColor.RED + " No puedes usar comandos desde la consola");
        } else if (plugin.lang.equalsIgnoreCase("messages_en")) {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(plugin.nombre) + ChatColor.RED + " You can not use commands from the console");
        }
        return true;
    }
}
