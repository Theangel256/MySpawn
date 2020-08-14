// 
// Decompiled by Procyon v0.5.36
// 

package theangel256.myspawn.commands;

import org.bukkit.configuration.file.FileConfiguration;
import theangel256.myspawn.util.LocationManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import theangel256.myspawn.MySpawn;
import org.bukkit.command.CommandExecutor;

public class CommandPrincipal implements CommandExecutor
{
    private MySpawn plugin;
    
    public CommandPrincipal(final MySpawn plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command comando, final String label, final String[] args) {
        final FileConfiguration config = this.plugin.getConfig();
        final String reload = config.getString("Permissions.Reload");
        final String rlES = String.valueOf(this.plugin.nombre) + ChatColor.RED + " Necesitas el permiso " + ChatColor.GREEN + reload + ChatColor.RED + " para acceder al comando";
        final String rlEN = String.valueOf(this.plugin.nombre) + ChatColor.RED + " You need permission " + ChatColor.GREEN + reload + ChatColor.RED + " to access the command";
        final String sucessES = String.valueOf(this.plugin.nombre) + ChatColor.DARK_GREEN + " El plugin ha sido recargado correctamente";
        final String sucessEN = String.valueOf(this.plugin.nombre) + ChatColor.DARK_GREEN + " The plugin has been correctly reloaded";
        if (args.length <= 0) {
            if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                sender.sendMessage(new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append("        MySpawn").toString());
                sender.sendMessage(ChatColor.RED + "<----------------------->");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.YELLOW + "/MySpawn help" + ChatColor.GRAY + " informaci\u00f3n sobre todos los comandos.");
                sender.sendMessage(ChatColor.YELLOW + "/MySpawn reload" + ChatColor.GRAY + " recarga el complemento.");
                sender.sendMessage(ChatColor.YELLOW + "/setspawn" + ChatColor.GRAY + " Establece el spawn de los usuarios.");
                sender.sendMessage(ChatColor.YELLOW + "/spawn" + ChatColor.GRAY + " Te teletransporta al spawn");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.RED + "<----------------------->");
            }
            else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
                sender.sendMessage(new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append("        MySpawn").toString());
                sender.sendMessage(ChatColor.RED + "<----------------------->");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.YELLOW + "/MySpawn help" + ChatColor.GRAY + " information about all the commands.");
                sender.sendMessage(ChatColor.YELLOW + "/MySpawn reload" + ChatColor.GRAY + " reload the plugin.");
                sender.sendMessage(ChatColor.YELLOW + "/setspawn" + ChatColor.GRAY + " Define the spawn of users.");
                sender.sendMessage(ChatColor.YELLOW + "/spawn" + ChatColor.GRAY + " teleport to spawn.");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.RED + "<----------------------->");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("help")) {
            if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                sender.sendMessage(new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append("        MySpawn").toString());
                sender.sendMessage(ChatColor.RED + "<----------------------->");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.YELLOW + "/MySpawn help" + ChatColor.GRAY + " informaci\u00f3n sobre todos los comandos.");
                sender.sendMessage(ChatColor.YELLOW + "/MySpawn reload" + ChatColor.GRAY + " recarga el complemento.");
                sender.sendMessage(ChatColor.YELLOW + "/setspawn" + ChatColor.GRAY + " Establece el spawn de los usuarios.");
                sender.sendMessage(ChatColor.YELLOW + "/spawn" + ChatColor.GRAY + " Te teletransporta al spawn");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.RED + "<----------------------->");
            }
            else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
                sender.sendMessage(new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append("        MySpawn").toString());
                sender.sendMessage(ChatColor.RED + "<----------------------->");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.YELLOW + "/MySpawn help" + ChatColor.GRAY + " information about all the commands.");
                sender.sendMessage(ChatColor.YELLOW + "/MySpawn reload" + ChatColor.GRAY + " reload the plugin.");
                sender.sendMessage(ChatColor.YELLOW + "/setspawn" + ChatColor.GRAY + " Define the spawn of users.");
                sender.sendMessage(ChatColor.YELLOW + "/spawn" + ChatColor.GRAY + " teleport to spawn.");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.RED + "<----------------------->");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission(reload)) {
                if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                    sender.sendMessage(rlES);
                }
                else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
                    sender.sendMessage(rlEN);
                }
                return true;
            }
            MySpawn.ReloadMessages();
            LocationManager.getManager().reloadConfig();
            this.plugin.reloadConfig();
            if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                sender.sendMessage(sucessES);
            }
            else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
                sender.sendMessage(sucessEN);
            }
            return true;
        }
        else {
            if (!args[0].equalsIgnoreCase("rl")) {
                if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                    sender.sendMessage(String.valueOf(this.plugin.nombre) + ChatColor.RED + " Opcion incorrecta!");
                }
                else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
                    sender.sendMessage(String.valueOf(this.plugin.nombre) + ChatColor.RED + " Wrong choice!");
                }
                return false;
            }
            if (!sender.hasPermission(reload)) {
                if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                    sender.sendMessage(rlES);
                }
                else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
                    sender.sendMessage(rlEN);
                }
                return true;
            }
            MySpawn.ReloadMessages();
            LocationManager.getManager().reloadConfig();
            this.plugin.reloadConfig();
            if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                sender.sendMessage(sucessES);
            }
            else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
                sender.sendMessage(sucessEN);
            }
            return true;
        }
    }
}
