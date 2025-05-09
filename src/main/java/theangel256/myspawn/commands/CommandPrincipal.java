package theangel256.myspawn.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import theangel256.myspawn.Main;
import theangel256.myspawn.util.LocationManager;

public class CommandPrincipal implements CommandExecutor {
    private final Main plugin;

    public CommandPrincipal(final Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(final CommandSender sender, final Command comando, final String label, final String[] args) {
        final FileConfiguration config = plugin.getConfig();
        final String reload = config.getString("Permissions.Reload");

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            if (plugin.lang.equalsIgnoreCase("messages_es")) {
                sender.sendMessage(color("&e&l        MySpawn"));
                sender.sendMessage(color("&c<----------------------->"));
                sender.sendMessage("");
                sender.sendMessage(color("&e/MySpawn help&7 Informacion sobre todos los comandos."));
                sender.sendMessage(color("&e/MySpawn reload&7 Recarga el complemento."));
                sender.sendMessage(color("&e/SetFirstSpawn &7 Establece el spawn inicial de los usuarios."));
                sender.sendMessage(color("&e/SetSpawn&7 Establece el spawn de los usuarios."));
                sender.sendMessage(color("&e/Spawn&7 Te teletransporta al spawn"));
                sender.sendMessage("");
                sender.sendMessage(color("&c<----------------------->"));
            } else if (plugin.lang.equalsIgnoreCase("messages_en")) {
                sender.sendMessage(color("&e&l        MySpawn"));
                sender.sendMessage(color("&c<----------------------->"));
                sender.sendMessage("");
                sender.sendMessage(color("&e/MySpawn help&7 Information about all the commands."));
                sender.sendMessage(color("&e/MySpawn reload&7 Reload the plugin."));
                sender.sendMessage(color("&e/SetFirstSpawn &7 Set the initial spawn of users."));
                sender.sendMessage(color("&e/SetSpawn &7 Define the spawn of users."));
                sender.sendMessage(color("&e/Spawn &7 Teleport to spawn."));
                sender.sendMessage("");
                sender.sendMessage(color("&c<----------------------->"));
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("v")) {
            sender.sendMessage(color(plugin.nombre + "&a Version: &7" + plugin.version));
            return true;
        }
        if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
            if (!sender.hasPermission(reload)) {
                String missingReloadPermissions = plugin.lang.equalsIgnoreCase("messages_es")
                        ? "&c Necesitas el permiso &a" + reload + "&c para acceder al comando"
                        : "&c You need permission &a" + reload + "&c to access the command";
                sender.sendMessage(color(plugin.nombre + " " + missingReloadPermissions));
                return true;
            }
            LocationManager.getManager().reloadConfig();
            plugin.reloadConfig();
            String sucessmsg = plugin.lang.equalsIgnoreCase("messages_es")
                    ? "&2 El plugin ha sido recargado correctamente"
                    : "&2 The plugin has been correctly reloaded";
            sender.sendMessage(color(plugin.nombre + " " + sucessmsg));
            return true;
        } else {
            sender.sendMessage(color(plugin.nombre + "&c Opcion incorrecta!"));
            return true;
        }
    }

    private static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
