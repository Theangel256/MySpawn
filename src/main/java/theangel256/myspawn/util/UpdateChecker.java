package theangel256.myspawn.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {
    private final int project;
    private String newVersion;
    private final JavaPlugin plugin;
    private URL checkURL;

    public UpdateChecker(final JavaPlugin plugin, final int projectID) {
        this.plugin = plugin;
        this.project = projectID;
        this.newVersion = plugin.getDescription().getVersion();
        try {
            this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
        } catch (MalformedURLException e) {
            Bukkit.getLogger().warning("§4Could not connect to Spigot, plugin disabled!");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    public String getResourceUrl() {
        return "https://spigotmc.org/resources/" + this.project;
    }

    public boolean checkForUpdates() throws Exception {
        final URLConnection con = this.checkURL.openConnection();
        this.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        return !this.plugin.getDescription().getVersion().equals(this.newVersion);
    }
}
