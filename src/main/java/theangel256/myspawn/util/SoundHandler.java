package theangel256.myspawn.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SoundHandler {

    public static void playSoundToPlayer(FileConfiguration config, String path, Player player, String pluginName, String lang) {
        if (!config.getBoolean(path)) return;

        String fullPath = config.getString(path + "-Sound");
        if (fullPath == null || !fullPath.contains(";")) {
            String InvalidConfig = lang.equalsIgnoreCase("messages_es")
                    ? "&cERROR: Config invalida para " + path
                    : "&cERROR: Invalid config " + path;
            Bukkit.getConsoleSender().sendMessage(color(pluginName + " " + InvalidConfig));
            return;
        }

        String[] parts = fullPath.split(";");
        if (parts.length < 3) {
            String InvalidFormat = lang.equalsIgnoreCase("messages_es")
                    ? "&cERROR: Formato invalido (esperado: NOMBRE;VOLUMEN;PITCH)"
                    : "&cERROR: Invalid Format (: NAME;VOLUME;PITCH)";
            Bukkit.getConsoleSender().sendMessage(color(pluginName + " " + InvalidFormat));
            return;
        }

        try {
            Sound sound = Sound.valueOf(parts[0].toUpperCase());
            float volume = Float.parseFloat(parts[1]);
            float pitch = Float.parseFloat(parts[2]);

            if (volume <= 0) volume = 1;
            if (pitch <= 0) pitch = 1;
            player.playSound(player.getLocation(), sound, volume, pitch);
        } catch (Exception e) {
            String InvalidSound = lang.equalsIgnoreCase("messages_es")
                    ? "&cERROR: El sonido &e" + parts[0] + " &ces invalido o mal configurado."
                    : "&cERROR: The sound &e" + parts[0] + " &cis invalid or misconfigured.";
            Bukkit.getConsoleSender().sendMessage(color(pluginName + " " + InvalidSound));
        }
    }


    private static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
