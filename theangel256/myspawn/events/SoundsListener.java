// 
// Decompiled by Procyon v0.5.36
// 

package theangel256.myspawn.events;

import org.bukkit.event.EventHandler;
import java.util.Iterator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerJoinEvent;
import theangel256.myspawn.MySpawn;
import org.bukkit.event.Listener;

public class SoundsListener implements Listener
{
    private MySpawn plugin;
    
    public SoundsListener(final MySpawn plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void ingresarAdmin(final PlayerJoinEvent event) {
        final FileConfiguration config = this.plugin.getConfig();
        final Player p = event.getPlayer();
        final String Admin_perm = config.getString("Permissions.Admin-join");
        if (config.getBoolean("Sounds.Admin-join") && (p.isOp() || p.hasPermission(Admin_perm))) {
            final String path = config.getString("Sounds.Admin-join-Sound");
            final String[] separados = path.split(";");
            try {
                final int volumen = Integer.valueOf(separados[1]);
                final float pitch = Float.valueOf(separados[2]);
                final Sound sound = Sound.valueOf(separados[0]);
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), sound, (float)volumen, pitch);
                }
            }
            catch (IllegalArgumentException e) {
                if (this.plugin.lang.equalsIgnoreCase("messages_es")) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.plugin.nombre) + " &cERROR: El Sonido &e" + separados[0] + " &cEs Invalido"));
                }
                else if (this.plugin.lang.equalsIgnoreCase("messages_en")) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.plugin.nombre) + " &cERROR: The Sound &e" + separados[0] + " &cIs Invalid"));
                }
            }
        }
    }
}
