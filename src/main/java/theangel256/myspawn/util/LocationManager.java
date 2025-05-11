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
        plugin = Main.getPlugin(Main.class);
    }

    public static LocationManager getManager() {
        return LocationManager.manager;
    }

    public void setupFiles() {
        location = new File(plugin.getDataFolder(), "Spawn.yml");
        if (!location.exists()) {
            File parentDir = location.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                plugin.getLogger().warning("No se pudo crear el directorio para Spawn.yml en: " + parentDir.getAbsolutePath());
                return; // evita continuar si no se puede crear la carpeta
            }
            plugin.saveResource("Spawn.yml", false);
        }
        spawnCoords = new YamlConfiguration();
        try {
            spawnCoords.load(location);
        } catch (IOException | InvalidConfigurationException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error al cargar Spawn.yml", ex);
        }
    }

    public FileConfiguration getConfig() {
        return spawnCoords;
    }

    public void saveConfig() {
        if (spawnCoords == null || location == null) {
            plugin.getLogger().warning("No se puede guardar Spawn.yml: archivo o configuración no inicializados.");
            return;
        }
        try {
            spawnCoords.save(location);
        } catch (IOException ex) {
            plugin.getLogger().severe("Error al guardar Spawn.yml en: " + location.getAbsolutePath());
            plugin.getLogger().log(Level.SEVERE, "Detalles del error:", ex);
        }
    }

    public void reloadConfig() {
        if (location == null || !location.exists()) {
            plugin.getLogger().warning("No se puede recargar Spawn.yml: archivo no encontrado.");
            return;
        }
        spawnCoords = YamlConfiguration.loadConfiguration(location);
    }
}
