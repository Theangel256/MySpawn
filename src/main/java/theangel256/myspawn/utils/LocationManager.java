package theangel256.myspawn.utils;

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
    private PluginConfig messagesConfig;
    private static final LocationManager manager = new LocationManager();

    private LocationManager() {
        plugin = Main.getPlugin(Main.class);
    }

    public static LocationManager getManager() {
        return LocationManager.manager;
    }

    public void setupFiles() {
        // locations.yml setup
        location = new File(plugin.getDataFolder(), "locations.yml");
        if (!location.exists()) {
            File parentDir = location.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                plugin.getLogger()
                        .warning(
                                "No se pudo crear el directorio para locations.yml en: " + parentDir.getAbsolutePath());
                return;
            }
            plugin.saveResource("locations.yml", false);
        }
        spawnCoords = new YamlConfiguration();
        try {
            spawnCoords.load(location);
        } catch (IOException | InvalidConfigurationException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error al cargar locations.yml", ex);
        }

        // Messages setup
        String lang = plugin.getConfig().getString("Options.Language", "EN").toLowerCase();
        messagesConfig = new PluginConfig(plugin, "messages_" + lang);
    }

    public FileConfiguration getSpawnConfig() {
        return spawnCoords;
    }

    public FileConfiguration getMainConfig() {
        return plugin.getConfig();
    }

    public PluginConfig getMessagesConfig() {
        return messagesConfig;
    }

    public void saveConfig() {
        if (spawnCoords == null || location == null) {
            plugin.getLogger().warning("No se puede guardar locations.yml: archivo o configuración no inicializados.");
            return;
        }
        try {
            spawnCoords.save(location);
        } catch (IOException ex) {
            plugin.getLogger().severe("Error al guardar locations.yml en: " + location.getAbsolutePath());
            plugin.getLogger().log(Level.SEVERE, "Detalles del error:", ex);
        }
    }

    public void reloadConfig() {
        // Reload locations.yml
        if (location == null || !location.exists()) {
            // If missing, try to setup again (restore)
            if (location != null && !location.exists()) {
                plugin.saveResource("locations.yml", false);
            }
        }

        // Reload object if it was null or just load
        if (location == null) {
            location = new File(plugin.getDataFolder(), "locations.yml");
        }

        if (!location.exists()) {
            plugin.saveResource("locations.yml", false);
        }

        spawnCoords = YamlConfiguration.loadConfiguration(location);

        // Reload config.yml
        plugin.reloadConfig();

        // Reload Messages
        String newLang = plugin.getConfig().getString("Options.Language", "EN").toLowerCase();
        plugin.lang = "messages_" + newLang; // Update Main.lang field

        // Check if language changed or file missing
        File msgFile = new File(plugin.getDataFolder(), "messages_" + newLang + ".yml");
        if (!msgFile.exists() || !messagesConfig.getFile().getName().equals("messages_" + newLang + ".yml")) {
            messagesConfig = new PluginConfig(plugin, "messages_" + newLang);
        } else {
            messagesConfig.reload();
        }
    }
}
