package theangel256.myspawn.commands;

import org.bukkit.FireworkEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import theangel256.myspawn.Main;

import java.util.List;

import static theangel256.myspawn.Main.color;

public class SetFirework implements CommandExecutor {
    private final Main plugin;

    public SetFirework(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        if (args.length < 3) {
            p.sendMessage(color("&cUsage: /setfirework <join|spawn|first-join> <power|color|type|trail|flicker> <value>"));
            return true;
        }

        String section = args[0].toLowerCase();
        String setting = args[1].toLowerCase();
        String value = args[2];

        String path = switch (section) {
            case "join" -> "Fireworks.Join";
            case "spawn" -> "Fireworks.Spawn";
            case "first-join" -> "Fireworks.First-join";
            default -> null;
        };
        if (path == null) {
            p.sendMessage(color("&cInvalid section. Use: join, spawn, first-join."));
            return true;
        }
        FileConfiguration config = plugin.getConfig();

        try {
            switch (setting) {
                case "power" -> {
                    int power = Integer.parseInt(value);
                    config.set(path + ".Power", power);
                    p.sendMessage(color("&aSet firework power to &e" + power));
                }
                case "color" -> {
                    List<String> colors = config.getStringList(path + ".Colors");
                    colors.add(value.toUpperCase());
                    config.set(path + ".Colors", colors);
                    p.sendMessage(color("&aAdded color: &e" + value));
                }
                case "type" -> {
                    FireworkEffect.Type type = FireworkEffect.Type.valueOf(value.toUpperCase());
                    config.set(path + ".Type", type.name());
                    p.sendMessage(color("&aSet firework type to &e" + type.name()));
                }
                case "trail" -> {
                    boolean trail = Boolean.parseBoolean(value);
                    config.set(path + ".Trail", trail);
                    p.sendMessage(color("&aSet trail to &e" + trail));
                }
                case "flicker" -> {
                    boolean flicker = Boolean.parseBoolean(value);
                    config.set(path + ".Flicker", flicker);
                    p.sendMessage(color("&aSet flicker to &e" + flicker));
                }
                default -> p.sendMessage(color("&cInvalid setting. Use: power, color, type, trail, flicker."));
            }
            plugin.saveConfig();
        } catch (Exception e) {
            p.sendMessage(color("&cError applying setting: ") + e.getMessage());
        }

        return true;
    }
}
