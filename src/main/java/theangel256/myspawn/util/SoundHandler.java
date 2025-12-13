package theangel256.myspawn.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import theangel256.myspawn.Main;

import static theangel256.myspawn.Main.color;

public class SoundHandler {

    public static void playSoundToPlayer(Main plugin, FileConfiguration config, String path, Player player) {
        if (!config.getBoolean(path + ".Enabled"))
            return;

        String fullPath = config.getString(path + ".Sound");
        if (fullPath == null || !fullPath.contains(";")) {
            String InvalidConfig = plugin.lang.equalsIgnoreCase("messages_es")
                    ? "&cERROR: Config invalida para " + path
                    : "&cERROR: Invalid config " + path;
            Bukkit.getConsoleSender().sendMessage(color(plugin.nombre + " " + InvalidConfig));
            return;
        }
        String[] parts = fullPath.split(";");
        if (parts.length < 3) {
            String InvalidFormat = plugin.lang.equalsIgnoreCase("messages_es")
                    ? "&cERROR: Formato invalido (esperado: NOMBRE;VOLUMEN;PITCH)"
                    : "&cERROR: Invalid Format (: NAME;VOLUME;PITCH)";
            Bukkit.getConsoleSender().sendMessage(color(plugin.nombre + " " + InvalidFormat));
            return;
        }

        String soundName = parts[0].toUpperCase();
        Sound sound = null;

        // Try to get the sound directly
        try {
            sound = Sound.valueOf(soundName);
        } catch (IllegalArgumentException e) {
            // If failed, try to find a legacy alternative
            String legacyName = VersionUtils.suggestLegacySound(soundName);
            if (legacyName != null) {
                try {
                    sound = Sound.valueOf(legacyName);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }

        if (sound != null) {
            try {
                float volume = Math.max(0.0f, Math.min((Float.parseFloat(parts[1]) - 1) / 9.0f, 1.0f));
                float pitch = Math.max(0.5f, Math.min(0.5f + (Float.parseFloat(parts[2]) - 1) * 0.15f, 2.0f));
                player.playSound(player.getLocation(), sound, volume, pitch);
            } catch (NumberFormatException e) {
                Bukkit.getConsoleSender()
                        .sendMessage(color(plugin.nombre + " &cERROR: Invalid number format for sound volume/pitch"));
            }
        } else {
            String suggested = VersionUtils.suggestLegacySound(soundName);
            String suggestionMsg = (suggested != null) ? " Sugerido: " + suggested : "";
            String errorMsg = plugin.lang.equalsIgnoreCase("messages_es")
                    ? "&cERROR: El sonido &e" + soundName + " &ces invalido o mal configurado." + suggestionMsg
                    : "&cERROR: The sound &e" + soundName + " &cis invalid or misconfigured." + suggestionMsg;
            Bukkit.getConsoleSender().sendMessage(color(plugin.nombre + " " + errorMsg));
        }
    }
}
