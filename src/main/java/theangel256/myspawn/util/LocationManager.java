// 
// Decompiled by Procyon v0.5.36
// 

package theangel256.myspawn.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import theangel256.myspawn.Main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class LocationManager {
    private final Main plugin;
    public File location;
    public YamlConfiguration spawnCoords;
    private static final LocationManager manager = new LocationManager();

    private LocationManager() {
        this.plugin = Main.getPlugin(Main.class);
    }

    public static LocationManager getManager() {
        return LocationManager.manager;
    }

    public void setupFiles() {
        this.location = new File(this.plugin.getDataFolder(), "Spawn.yml");
        if (!this.location.exists()) {
            File parentDir = this.location.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                plugin.getLogger().warning("No se pudo crear el directorio para Spawn.yml en: " + parentDir.getAbsolutePath());
                return; // evita continuar si no se puede crear la carpeta
            }

            this.plugin.saveResource("Spawn.yml", false);
        }
        this.spawnCoords = new YamlConfiguration();
        try {
            this.spawnCoords.load(this.location);
        } catch (IOException | InvalidConfigurationException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error al cargar Spawn.yml", ex);
        }
    }

    public FileConfiguration getConfig() {
        return this.spawnCoords;
    }

    public void saveConfig() {
        if (this.spawnCoords == null || this.location == null) {
            plugin.getLogger().warning("No se puede guardar Spawn.yml: archivo o configuración no inicializados.");
            return;
        }
        try {
            this.spawnCoords.save(this.location);
        } catch (IOException ex) {
            plugin.getLogger().severe("Error al guardar Spawn.yml en: " + this.location.getAbsolutePath());
            plugin.getLogger().log(Level.SEVERE, "Detalles del error:", ex);
        }
    }

    public void reloadConfig() {
        if (this.location == null || !this.location.exists()) {
            plugin.getLogger().warning("No se puede recargar Spawn.yml: archivo no encontrado.");
            return;
        }
        this.spawnCoords = YamlConfiguration.loadConfiguration(this.location);
    }
}
