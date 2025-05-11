package theangel256.myspawn.util;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;

import static theangel256.myspawn.Main.color;

public class FireworkHandler {
    public static void launchFirework(FileConfiguration config, String fireworkConfigPath, Player p, String pluginName, String lang) {
        // If fireworks are not enabled for the specified event, return
        if (!config.getBoolean(fireworkConfigPath + ".Enabled")) return;
        // Power
        int power = config.getInt(fireworkConfigPath + ".Power");
        if (power < 0 || power > 3) {
            String invalidPower = lang.equalsIgnoreCase("messages_es")
                    ? "&cERROR: Potencia de fuegos artificiales inválida &e" + power + "&c Usando valor por defecto (1)."
                    : "&cERROR: Invalid firework power &e" + power + "&c Using default value (1).";
            Bukkit.getConsoleSender().sendMessage(color(pluginName + " " + invalidPower));
            power = 1;
        }
        // Colors
        List<Color> colors = new ArrayList<>();
        List<String> colorStrings = config.getStringList(fireworkConfigPath + ".Colors");

        for (String colorStr : colorStrings) {
            try {
                if (colorStr.startsWith("#")) {
                    colors.add(hexToColorRGB(colorStr));
                } else {
                    colors.add(getNamedColor(colorStr, pluginName));
                }
            } catch (Exception e) {
                String InvalidFireworkColor = lang.equalsIgnoreCase("messages_es")
                        ? "&cERROR: Invalid firework color: &e" + colorStr + " &2Using default color."
                        : "&cERROR: El color para los fuegos artificales &e" + colorStr + "&c no es valido. Usando color por defecto.";
                Bukkit.getConsoleSender().sendMessage(color(pluginName + " " + InvalidFireworkColor));
            }
        }
        // Fallback color
        if (colors.isEmpty()) {
            colors.add(Color.WHITE);
        }

        // Firework Type
        FireworkEffect.Type effectType;
        String typeStr = config.getString(fireworkConfigPath + ".Type");
        try {
            effectType = FireworkEffect.Type.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            String InvalidFireworkType = lang.equalsIgnoreCase("messages_es")
                    ? "&cERROR: Invalid firework type: &e" + typeStr + " &2Using default type."
                    : "&cERROR: El tipo de fuegos artificales &e" + typeStr + "&c no es valido. Usando el tipo por defecto.";
            Bukkit.getConsoleSender().sendMessage(color(pluginName + " " + InvalidFireworkType));
            effectType = FireworkEffect.Type.BALL; // Default to BALL if the type is invalid
        }
        // Effects: Trail and Flicker
        boolean trail = config.getBoolean(fireworkConfigPath + ".Trail", false);
        boolean flicker = config.getBoolean(fireworkConfigPath + ".Flicker", false);

        // Create the firework
        EntityType fireworkType = VersionUtils.getFireworkEntityType();
        Firework firework = (Firework) p.getWorld().spawnEntity(p.getLocation(), fireworkType);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.setPower(power); // safe range 0-3

        // Add the effects
        FireworkEffect.Builder effectBuilder = FireworkEffect.builder()
                .withColor(colors)
                .with(effectType)
                .flicker(flicker)
                .trail(trail);

        meta.addEffect(effectBuilder.build());
        firework.setFireworkMeta(meta);
    }

    private static Color getNamedColor(String colorName, String pluginName) {
        return switch (colorName.toUpperCase()) {
            case "BLACK" -> Color.BLACK;
            case "RED" -> Color.RED;
            case "GREEN" -> Color.GREEN;
            case "YELLOW" -> Color.YELLOW;
            case "BLUE" -> Color.BLUE;
            case "MAGENTA" -> Color.fromRGB(255, 0, 255); // Custom RGB for MAGENTA
            case "CYAN" -> Color.fromRGB(0, 255, 255);
            case "WHITE" -> Color.WHITE;
            case "ORANGE" -> Color.fromRGB(255, 165, 0); // Custom RGB for ORANGE
            case "PINK" -> Color.fromRGB(255, 192, 203); // Custom RGB for PINK
            case "LIME" -> Color.fromRGB(0, 255, 0); // Custom RGB for LIME
            case "LIGHT_BLUE" -> Color.fromRGB(173, 216, 230); // Custom RGB for LIGHT_BLUE
            case "PURPLE" -> Color.fromRGB(128, 0, 128); // Custom RGB for PURPLE
            case "BROWN" -> Color.fromRGB(139, 69, 19); // Custom RGB for BROWN
            case "LIGHT_GRAY" -> Color.fromRGB(211, 211, 211); // Custom RGB for LIGHT_GRAY
            case "GRAY" -> Color.GRAY;
            default ->
                    throw new IllegalArgumentException("Unknown color name: " + pluginName); // Si no se encuentra el color, retorna null
        };
    }

    private static Color hexToColorRGB(String hex) {
        return Color.fromRGB(
                Integer.valueOf(hex.substring(1, 3), 16), // Rojo
                Integer.valueOf(hex.substring(3, 5), 16), // Verde
                Integer.valueOf(hex.substring(5, 7), 16)  // Azul
        );
    }
}
