package theangel256.myspawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import theangel256.myspawn.Main;
import theangel256.myspawn.utils.LocationManager;

import java.util.Objects;

import static theangel256.myspawn.Main.color;

public class SetFirstSpawn implements CommandExecutor {
    private final Main plugin;

    public SetFirstSpawn(final Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(final CommandSender sender, final Command comando, final String label,
            final String[] args) {

        final FileConfiguration config = plugin.getConfig();
        final String setFirstSpawnPermission = config.getString("Permissions.Set-FirstSpawn");
        if (!(sender instanceof Player p)) {
            String notAllowedfromConsole = plugin.lang.equalsIgnoreCase("messages_es")
                    ? " &cNo puedes usar este comando desde la consola"
                    : " &cYou can not use this command from the console";
            Bukkit.getConsoleSender().sendMessage(color(plugin.nombre + " " + notAllowedfromConsole));
            return true;
        }
        if (!sender.hasPermission(setFirstSpawnPermission)) {
            String missingReloadPermissions = plugin.lang.equalsIgnoreCase("messages_es")
                    ? "&c Necesitas el permiso &a" + setFirstSpawnPermission + "&c para acceder al comando"
                    : "&c You need permission &a" + setFirstSpawnPermission + "&c to access the command";
            p.sendMessage(color(plugin.nombre + " " + missingReloadPermissions));
            return true;
        }
        LocationManager spawnCoords1 = LocationManager.getManager();
        spawnCoords1.getSpawnConfig().set("FirstSpawn.world",
                Objects.requireNonNull(p.getLocation().getWorld()).getName());
        spawnCoords1.getSpawnConfig().set("FirstSpawn.x", p.getLocation().getX());
        spawnCoords1.getSpawnConfig().set("FirstSpawn.y", p.getLocation().getY());
        spawnCoords1.getSpawnConfig().set("FirstSpawn.z", p.getLocation().getZ());
        spawnCoords1.getSpawnConfig().set("FirstSpawn.yaw", p.getLocation().getYaw());
        spawnCoords1.getSpawnConfig().set("FirstSpawn.pitch", p.getLocation().getPitch());
        spawnCoords1.saveConfig();
        final String setSpawnText = Main.getMessages().getString("Messages.SpawnDefined");
        p.sendMessage(color(setSpawnText));
        return true;
    }
}
