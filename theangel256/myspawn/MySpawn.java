package theangel256.myspawn;

import java.io.File;
import org.bukkit.plugin.PluginManager;
import theangel256.myspawn.events.Playervoid;
import theangel256.myspawn.events.Quit;
import org.bukkit.plugin.Plugin;
import theangel256.myspawn.events.Join;
import theangel256.myspawn.commands.CommandSetSpawn;
import theangel256.myspawn.commands.CommandSpawn;
import org.bukkit.command.CommandExecutor;
import theangel256.myspawn.commands.CommandPrincipal;
import org.bukkit.Bukkit;
import theangel256.myspawn.util.UpdateChecker;
import theangel256.myspawn.util.LocationManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import theangel256.myspawn.util.PluginConfig;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MySpawn extends JavaPlugin implements Listener
{
    private static PluginConfig Messages;
    PluginDescriptionFile pdffile;
    FileConfiguration config;
    public String rutaConfig;
    public String version;
    public String nombre;
    public String lang;
    public String latestversion;
    
    public MySpawn() {
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
        MySpawn.Messages = new PluginConfig(this, "Messages_" + this.config.getString("Options.Language"));
        if (this.config.getBoolean("Update-check")) {
            try {
                final UpdateChecker updater = new UpdateChecker(this, 64762);
                if (updater.checkForUpdates()) {
                    if (this.lang.equalsIgnoreCase("messages_es")) {
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(this.nombre) + ChatColor.GREEN + " Nueva version disponible.");
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(this.nombre) + ChatColor.YELLOW + " Puedes descargarlo en: " + ChatColor.WHITE + "https://www.spigotmc.org/resources/64762");
                    }
                    else if (this.lang.equalsIgnoreCase("messages_en")) {
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(this.nombre) + ChatColor.GREEN + " New version available.");
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(this.nombre) + ChatColor.YELLOW + " You can download it in: " + ChatColor.WHITE + "https://www.spigotmc.org/resources/64762");
                    }
                }
            }
            catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(String.valueOf(this.nombre) + ChatColor.RED + " The plugin is not found in the spigot page");
            }
        }
    }
    
    public void RegistrarComandos() {
        this.getCommand("MySpawn").setExecutor((CommandExecutor)new CommandPrincipal(this));
        this.getCommand("Spawn").setExecutor((CommandExecutor)new CommandSpawn(this));
        this.getCommand("SetSpawn").setExecutor((CommandExecutor)new CommandSetSpawn(this));
    }
    
    public void RegistrarEventos() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents((Listener)new Join(this), (Plugin)this);
        pm.registerEvents((Listener)new Quit(this), (Plugin)this);
        pm.registerEvents((Listener)new Playervoid(this), (Plugin)this);
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
        return MySpawn.Messages;
    }
    
    public static PluginConfig ReloadMessages() {
        return MySpawn.Messages;
    }
}
