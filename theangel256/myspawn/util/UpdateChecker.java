// 
// Decompiled by Procyon v0.5.36
// 

package theangel256.myspawn.util;

import java.net.URLConnection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.net.URL;

public class UpdateChecker
{
    private int project;
    private URL checkURL;
    private String newVersion;
    private JavaPlugin plugin;
    
    public UpdateChecker(final JavaPlugin plugin, final int projectID) {
        this.plugin = plugin;
        this.project = projectID;
        this.newVersion = plugin.getDescription().getVersion();
        try {
            this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
        }
        catch (MalformedURLException e) {
            Bukkit.getLogger().warning("§4Could not connect to Spigot, plugin disabled!");
            Bukkit.getPluginManager().disablePlugin((Plugin)plugin);
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
