// 
// Decompiled by Procyon v0.5.36
// 

package theangel256.myspawn.events;

import org.bukkit.event.EventHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerQuitEvent;
import theangel256.myspawn.MySpawn;
import org.bukkit.event.Listener;

public class Quit implements Listener
{
    private MySpawn plugin;
    
    public Quit(final MySpawn plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void alSalir(final PlayerQuitEvent event) {
        final FileConfiguration config = this.plugin.getConfig();
        final String quitText = MySpawn.getMessages().getString("Messages.Player-quit");
        event.setQuitMessage((String)null);
        if (config.getBoolean("Options.Player-quit")) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', quitText).replace("{player}", event.getPlayer().getName()));
        }
    }
}
