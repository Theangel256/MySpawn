// 
// Decompiled by Procyon v0.5.36
// 

package theangel256.myspawn.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.InvalidConfigurationException;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import theangel256.myspawn.MySpawn;
import org.bukkit.event.Listener;

public class LocationManager implements Listener
{
    private MySpawn plugin;
    public File location;
    public YamlConfiguration spawnCoords;
    private static LocationManager manager;
    
    static {
        LocationManager.manager = new LocationManager();
    }
    
    private LocationManager() {
        this.plugin = (MySpawn)MySpawn.getPlugin((Class)MySpawn.class);
    }
    
    public LocationManager(final MySpawn mainclass) {
        this.plugin = (MySpawn)MySpawn.getPlugin((Class)MySpawn.class);
    }
    
    public static LocationManager getManager() {
        return LocationManager.manager;
    }
    
    public void setupFiles() {
        this.location = new File(this.plugin.getDataFolder(), "Spawn.yml");
        if (!this.location.exists()) {
            this.location.getParentFile().mkdirs();
            this.plugin.saveResource("Spawn.yml", false);
        }
        this.spawnCoords = new YamlConfiguration();
        try {
            this.spawnCoords.load(this.location);
        }
        catch (IOException | InvalidConfigurationException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    public FileConfiguration getConfig() {
        return (FileConfiguration)this.spawnCoords;
    }
    
    public void saveConfig() {
        try {
            this.spawnCoords.save(this.location);
        }
        catch (IOException ex) {}
    }
    
    public void reloadConfig() {
        this.spawnCoords = YamlConfiguration.loadConfiguration(this.location);
    }
}
