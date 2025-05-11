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

import static theangel256.myspawn.Main.color;

public class SetSpawn implements CommandExecutor {
    private final Main plugin;

    public SetSpawn(final Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(final CommandSender sender, final Command comando, final String label, final String[] args) {
        if (!(sender instanceof Player p)) {
            String notAllowedfromConsole = plugin.lang.equalsIgnoreCase("messages_es")
                    ? " &cNo puedes usar este comando desde la consola"
                    : " &cYou can not use this command from the console";
            Bukkit.getConsoleSender().sendMessage(color(plugin.nombre + " " + notAllowedfromConsole));
            return true;
        }
        final FileConfiguration config = this.plugin.getConfig();
        final String setspawn = config.getString("Permissions.Set-Spawn");
        if (!p.hasPermission(setspawn)) {
            String missingReloadPermissions = this.plugin.lang.equalsIgnoreCase("messages_es")
                    ? "&c Necesitas el permiso &a" + setspawn + "&c para acceder al comando"
                    : "&c You need permission &a" + setspawn + "&c to access the command";
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.nombre + " " + missingReloadPermissions));
            return true;
        }
        LocationManager spawnCoords1 = LocationManager.getManager();
        spawnCoords1.getConfig().set("Spawn.world", Objects.requireNonNull(p.getLocation().getWorld()).getName());
        spawnCoords1.getConfig().set("Spawn.x", p.getLocation().getX());
        spawnCoords1.getConfig().set("Spawn.y", p.getLocation().getY());
        spawnCoords1.getConfig().set("Spawn.z", p.getLocation().getZ());
        spawnCoords1.getConfig().set("Spawn.yaw", p.getLocation().getYaw());
        spawnCoords1.getConfig().set("Spawn.pitch", p.getLocation().getPitch());
        spawnCoords1.saveConfig();
        final String setSpawnText = Main.getMessages().getString("Messages.SpawnDefined");
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', setSpawnText));
        return true;
    }
}
