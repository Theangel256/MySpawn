package theangel256.myspawn.utils;

import org.bukkit.Bukkit;
import theangel256.myspawn.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {
    private final int project;
    private String newVersion;
    private URL checkURL;

    public UpdateChecker(Main plugin, final int projectID) {
        project = projectID;
        newVersion = plugin.getDescription().getVersion();
        try {
            checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
        } catch (MalformedURLException e) {
            Bukkit.getLogger().warning("§4Could not connect to Spigot, plugin disabled!");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    public String getResourceUrl() {
        return "https://spigotmc.org/resources/" + project;
    }

    public boolean checkForUpdates(Main plugin) throws Exception {
        final URLConnection con = checkURL.openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            newVersion = reader.readLine();
        }
        return !plugin.getDescription().getVersion().equals(newVersion);
    }
}
