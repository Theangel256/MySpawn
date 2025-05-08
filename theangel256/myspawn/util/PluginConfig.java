// 
// Decompiled by Procyon v0.5.36
// 

package theangel256.myspawn.util;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;
import org.bukkit.ChatColor;
import java.io.OutputStream;
import java.io.InputStream;
import org.bukkit.configuration.file.YamlConfiguration;
import com.google.common.io.ByteStreams;
import java.io.FileOutputStream;
import theangel256.myspawn.MySpawn;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

public class PluginConfig
{
    private FileConfiguration config;
    private File file;
    private MySpawn main;
    
    public PluginConfig(final MySpawn main, final String resourceName) {
        this.main = main;
        this.file = new File(this.main.getDataFolder(), String.valueOf(String.valueOf(resourceName)) + ".yml");
        final File folder = main.getDataFolder();
        if (!folder.exists()) {
            folder.mkdir();
        }
        final File resourceFile = new File(folder, String.valueOf(String.valueOf(resourceName)) + ".yml");
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                Throwable t = null;
                try {
                    final InputStream in = main.getResource(String.valueOf(String.valueOf(resourceName)) + ".yml");
                    try {
                        final OutputStream out = new FileOutputStream(resourceFile);
                        try {
                            ByteStreams.copy(in, out);
                            this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
                        }
                        finally {
                            if (out != null) {
                                out.close();
                            }
                        }
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                        return;
                    }
                    finally {
                        if (t == null) {
                            final Throwable t2 = t = null;
                        }
                        else {
                            final Throwable t2 = null;
                            if (t != t2) {
                                t.addSuppressed(t2);
                            }
                        }
                        if (in != null) {
                            in.close();
                        }
                    }
                }
                finally {
                    if (t == null) {
                        final Throwable t3 = t = null;
                    }
                    else {
                        final Throwable t3 = null;
                        if (t != t3) {
                            t.addSuppressed(t3);
                        }
                    }
                }
            }
            this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setDefault(final String path, final String value) {
        if (!this.config.contains(path)) {
            this.config.set(path, (Object)value);
            this.save();
        }
    }
    
    public void save() {
        try {
            this.config.save(this.file);
        }
        catch (Exception ex) {}
    }
    
    public FileConfiguration getConfig() {
        return this.config;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public String getString(final String path) {
        return ChatColor.translateAlternateColorCodes('&', this.config.getString(path));
    }
    
    public List<String> getStringList(final String path) {
        final List<String> lore = Lists.newArrayList();
        for (final String args : this.config.getStringList(path)) {
            lore.add(ChatColor.translateAlternateColorCodes('&', args));
        }
        return lore;
    }
    
    public int getInt(final String path) {
        return this.config.getInt(path);
    }
}
