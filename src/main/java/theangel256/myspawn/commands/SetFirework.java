package theangel256.myspawn.commands;

import org.bukkit.FireworkEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import theangel256.myspawn.Main;

public class SetFirework implements CommandExecutor {
    private final Main plugin;

    public SetFirework(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        if (args.length < 1) {
            p.sendMessage("Usage: /setfirework <power|color|type|trail|flicker>");
            return false;
        }

        FileConfiguration config = plugin.getConfig();
        String path = "Fireworks.Join"; // Default to the 'Join' firework section

        // Handle different arguments
        switch (args[0].toLowerCase()) {
            case "power":
                int power = Integer.parseInt(args[1]);
                config.set(path + ".Power", power);
                p.sendMessage("Firework power set to " + power);
                break;
            case "color":
                String color = args[1];
                config.getStringList(path + ".Colors").add(color);
                p.sendMessage("Firework color added: " + color);
                break;
            case "type":
                FireworkEffect.Type type = FireworkEffect.Type.valueOf(args[1].toUpperCase());
                config.set(path + ".Type", type.name());
                p.sendMessage("Firework type set to: " + type.name());
                break;
            case "trail":
                boolean trail = Boolean.parseBoolean(args[1]);
                config.set(path + ".Trail", trail);
                p.sendMessage("Firework trail set to: " + trail);
                break;
            case "flicker":
                boolean flicker = Boolean.parseBoolean(args[1]);
                config.set(path + ".Flicker", flicker);
                p.sendMessage("Firework flicker set to: " + flicker);
                break;
            default:
                p.sendMessage("Invalid argument. Use power, color, type, trail, or flicker.");
                break;
        }
        plugin.saveConfig();
        return true;
    }
}
