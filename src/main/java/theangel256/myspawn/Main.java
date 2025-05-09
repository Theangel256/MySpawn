package theangel256.myspawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import theangel256.myspawn.commands.CommandPrincipal;
import theangel256.myspawn.commands.CommandSetFirstSpawn;
import theangel256.myspawn.commands.CommandSetSpawn;
import theangel256.myspawn.commands.CommandSpawn;
import theangel256.myspawn.events.Join;
import theangel256.myspawn.events.Playervoid;
import theangel256.myspawn.events.Quit;
import theangel256.myspawn.util.LocationManager;
import theangel256.myspawn.util.PluginConfig;
import theangel256.myspawn.util.UpdateChecker;

import java.io.File;
import java.util.Objects;

public class Main extends JavaPlugin implements Listener {
    private static PluginConfig Messages;
    PluginDescriptionFile pdffile;
    FileConfiguration config;
    public String rutaConfig;
    public String version;
    public String nombre;
    public String lang;

    public Main() {
        this.pdffile = this.getDescription();
        this.config = this.getConfig();
        this.version = this.pdffile.getVersion();
        this.nombre = ChatColor.GRAY + "[" + ChatColor.YELLOW + this.getName() + ChatColor.GRAY + "]";
        this.lang = String.format("Messages_%s", this.config.getString("Options.Language"));
    }

    public void onEnable() {
        this.RegistrarComandos();
        this.RegistrarEventos();
        this.RegistrarConfig();
        LocationManager.getManager().setupFiles();
        LocationManager.getManager().reloadConfig();
        Main.Messages = new PluginConfig(this, "Messages_" + this.config.getString("Options.Language"));
        if (this.config.getBoolean("Update-check")) {
            final UpdateChecker updater = new UpdateChecker(this, 64762);
            try {
                if (updater.checkForUpdates()) {
                    if (this.lang.equalsIgnoreCase("messages_es")) {
                        Bukkit.getConsoleSender().sendMessage(this.nombre + ChatColor.GREEN + " Nueva version disponible.");
                        Bukkit.getConsoleSender().sendMessage(this.nombre + ChatColor.YELLOW + " Puedes descargarlo en: &fhttps://www.spigotmc.org/resources/64762");
                    } else if (this.lang.equalsIgnoreCase("messages_en")) {
                        Bukkit.getConsoleSender().sendMessage(this.nombre + ChatColor.GREEN + " New version available.");
                        Bukkit.getConsoleSender().sendMessage(this.nombre + ChatColor.YELLOW + " You can download it in: &fhttps://www.spigotmc.org/resources/64762");
                    }
                }
            } catch (Exception e) {
                String missingPageSpigot = this.lang.equalsIgnoreCase("messages_es")
                        ? ChatColor.RED + " El plugin no se encuentra en la pagina de spigot"
                        : ChatColor.RED + " The plugin is not found in the spigot page";
                Bukkit.getConsoleSender().sendMessage(this.nombre + missingPageSpigot);
            }
        }
    }

    public void RegistrarComandos() {
        Objects.requireNonNull(this.getCommand("MySpawn")).setExecutor(new CommandPrincipal(this));
        Objects.requireNonNull(this.getCommand("Spawn")).setExecutor(new CommandSpawn(this));
        Objects.requireNonNull(this.getCommand("SetSpawn")).setExecutor(new CommandSetSpawn(this));
        Objects.requireNonNull(this.getCommand("SetFirstSpawn")).setExecutor(new CommandSetFirstSpawn(this));
    }

    public void RegistrarEventos() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Join(this), this);
        pm.registerEvents(new Quit(this), this);
        pm.registerEvents(new Playervoid(this), this);
    }

    public void RegistrarConfig() {
        final File config = new File(this.getDataFolder(), "config.yml");
        this.rutaConfig = config.getPath();
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        }
    }

    public static PluginConfig getMessages() {
        return Main.Messages;
    }
}
