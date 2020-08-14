// 
// Decompiled by Procyon v0.5.36
// 

package theangel256.myspawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import theangel256.myspawn.util.LocationManager;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import theangel256.myspawn.MySpawn;
import org.bukkit.command.CommandExecutor;

public class CommandSetSpawn implements CommandExecutor
{
    private MySpawn plugin;
    
    public CommandSetSpawn(final MySpawn plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command comando, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            final LocationManager spawnCoords1 = LocationManager.getManager();
            spawnCoords1.getConfig().set("Spawn.world", (Object)p.getLocation().getWorld().getName());
            spawnCoords1.getConfig().set("Spawn.x", (Object)p.getLocation().getX());
            spawnCoords1.getConfig().set("Spawn.y", (Object)p.getLocation().getY());
            spawnCoords1.getConfig().set("Spawn.z", (Object)p.getLocation().getZ());
            spawnCoords1.getConfig().set("Spawn.yaw", (Object)p.getLocation().getYaw());
            spawnCoords1.getConfig().set("Spawn.pitch", (Object)p.getLocation().getPitch());
            spawnCoords1.saveConfig();
            final String setSpawnText = MySpawn.getMessages().getString("Messages.SpawnDefined");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', setSpawnText));
        }
        else if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(this.plugin.nombre) + ChatColor.RED + " No puedes usar comandos desde la consola");
        }
        else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(this.plugin.nombre) + ChatColor.RED + " You can not use commands from the console");
        }
        return false;
    }
}
