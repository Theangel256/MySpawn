package theangel256.myspawn.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import theangel256.myspawn.Main;

public class Quit implements Listener {
    private final Main plugin;

    public Quit(final Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void alSalir(final PlayerQuitEvent event) {
        final FileConfiguration config = plugin.getConfig();
        final String quitText = Main.getMessages().getString("Messages.Player-quit");
        event.setQuitMessage(null);
        if (config.getBoolean("Options.Player-quit")) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', quitText).replace("{player}",
                    event.getPlayer().getName()));
        }
    }
}
