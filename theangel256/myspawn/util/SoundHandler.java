package theangel256.myspawn.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class SoundHandler {

    public static void playSoundToPlayer(FileConfiguration config, String path, Player player, String pluginName, String lang) {
        if (!config.getBoolean(path)) return;

        String fullPath = config.getString(path + "-Sound");
        if (fullPath == null || !fullPath.contains(";")) {
            Bukkit.getConsoleSender().sendMessage(color(pluginName + " &cERROR: Config inválida para " + path));
            return;
        }

        String[] parts = fullPath.split(";");
        if (parts.length < 3) {
            Bukkit.getConsoleSender().sendMessage(color(pluginName + " &cERROR: Formato inválido (esperado: NOMBRE;VOLUMEN;PITCH)"));
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
            String msg = lang.equalsIgnoreCase("messages_es")
                    ? "&cERROR: El sonido &e" + parts[0] + " &ces inválido o mal configurado."
                    : "&cERROR: The sound &e" + parts[0] + " &cis invalid or misconfigured.";
            Bukkit.getConsoleSender().sendMessage(color(pluginName + " " + msg));
        }
    }


    private static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
