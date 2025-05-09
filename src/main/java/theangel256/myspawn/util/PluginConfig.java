package theangel256.myspawn.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import theangel256.myspawn.Main;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class PluginConfig {
    private final File file;
    private final FileConfiguration config;

    public PluginConfig(final Main plugin, final String resourceName) {
        File folder = plugin.getDataFolder();
        if (!folder.exists()) {
            folder.mkdirs();
        }

        this.file = new File(folder, resourceName + ".yml");

        if (!file.exists()) {
            try (InputStream in = plugin.getResource(resourceName + ".yml");
                 OutputStream out = Files.newOutputStream(file.toPath())) {

                if (in != null) {
                    Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

            } catch (Exception e) {
                plugin.getLogger().severe("Error creating configuration file: " + file.getName());
            }
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void setDefault(String path, String value) {
        if (!config.contains(path)) {
            config.set(path, value);
            save();
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            System.err.println("Error saving configuration file: " + file.getName());

        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }

    public String getString(String path) {
        String value = config.getString(path);
        return value != null ? ChatColor.translateAlternateColorCodes('&', value) : "";
    }

    public List<String> getStringList(String path) {
        List<String> rawList = config.getStringList(path);
        List<String> coloredList = new ArrayList<>();
        for (String line : rawList) {
            coloredList.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return coloredList;
    }

    public int getInt(String path) {
        return config.getInt(path);
    }
}
