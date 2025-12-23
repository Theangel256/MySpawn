package theangel256.myspawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import theangel256.myspawn.commands.*;
import theangel256.myspawn.listeners.FireworkDamage;
import theangel256.myspawn.listeners.Join;
import theangel256.myspawn.listeners.Playervoid;
import theangel256.myspawn.listeners.Quit;
import theangel256.myspawn.listeners.Respawn;
import theangel256.myspawn.utils.LocationManager;
import theangel256.myspawn.utils.PluginConfig;
import theangel256.myspawn.utils.UpdateChecker;

import java.util.Objects;

public class Main extends JavaPlugin implements Listener {
    PluginDescriptionFile pdffile;
    FileConfiguration config;
    public String rutaConfig;
    public String version;
    public String nombre;
    public String lang;

    public Main() {
        pdffile = getDescription();
        config = getConfig();
        version = pdffile.getVersion();
        nombre = ChatColor.GRAY + "[" + ChatColor.YELLOW + getName() + ChatColor.GRAY + "]";
        lang = String.format("messages_%s", config.getString("Options.Language").toLowerCase());
    }

    public void onEnable() {
        RegistrarComandos();
        RegistrarEventos();
        LocationManager.getManager().setupFiles();
        LocationManager.getManager().reloadConfig();
        if (config.getBoolean("Update-check")) {
            final UpdateChecker updater = new UpdateChecker(this, 64762);
            try {
                if (updater.checkForUpdates(this)) {
                    if (lang.equalsIgnoreCase("messages_es")) {
                        Bukkit.getConsoleSender()
                                .sendMessage(color(nombre + " &aNueva version disponible."));
                        Bukkit.getConsoleSender()
                                .sendMessage(color(nombre + " &ePuedes descargarlo en: &f" + updater.getResourceUrl()));
                    } else if (lang.equalsIgnoreCase("messages_en")) {
                        Bukkit.getConsoleSender()
                                .sendMessage(color(nombre + " &aNew version available."));
                        Bukkit.getConsoleSender()
                                .sendMessage(
                                        color(nombre + " &eYou can download it in: &f" + updater.getResourceUrl()));
                    }
                }
            } catch (Exception e) {
                String missingPageSpigot = lang.equalsIgnoreCase("messages_es")
                        ? ChatColor.RED + " El plugin no se encuentra en la pagina de spigot"
                        : ChatColor.RED + " The plugin is not found in the spigot page";
                Bukkit.getConsoleSender().sendMessage(nombre + missingPageSpigot);
            }
        }
    }

    public void RegistrarComandos() {
        Objects.requireNonNull(getCommand("MySpawn")).setExecutor(new Principal(this));
        Objects.requireNonNull(getCommand("Spawn")).setExecutor(new Spawn(this));
        Objects.requireNonNull(getCommand("SetSpawn")).setExecutor(new SetSpawn(this));
        Objects.requireNonNull(getCommand("SetFirstSpawn")).setExecutor(new SetFirstSpawn(this));
        Objects.requireNonNull(getCommand("SetFirework")).setExecutor(new SetFirework(this));
    }

    public void RegistrarEventos() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Join(this), this);
        pm.registerEvents(new Quit(this), this);
        pm.registerEvents(new Playervoid(this), this);
        pm.registerEvents(new FireworkDamage(), this);
        pm.registerEvents(new Respawn(this), this);
    }

    public static PluginConfig getMessages() {
        return LocationManager.getManager().getMessagesConfig();
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
