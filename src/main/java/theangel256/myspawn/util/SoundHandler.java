package theangel256.myspawn.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static theangel256.myspawn.Main.color;

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
            String soundName = VersionUtils.isLegacy()
                    ? VersionUtils.suggestLegacySound(parts[0]) != null ? VersionUtils.suggestLegacySound(parts[0]) : parts[0]
                    : parts[0];
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            float volume = Math.max(0.0f, Math.min(Float.parseFloat(parts[1]), 10.0f)); // tope práctico
            float pitch = Math.max(0.5f, Math.min(Float.parseFloat(parts[2]), 2.0f));     // tono seguro

            if (volume <= 0) volume = 1.0f;
            if (pitch <= 0) pitch = 1.0f;
            player.playSound(player.getLocation(), sound, volume, pitch);
        } catch (Exception e) {
            String suggested = VersionUtils.suggestLegacySound(parts[0]);
            String suggestionMsg = (suggested != null)
                    ? " Sugerido: " + suggested
                    : "";
            String errorMsg = lang.equalsIgnoreCase("messages_es")
                    ? "&cERROR: El sonido &e" + parts[0] + " &ces invalido o mal configurado." + suggestionMsg
                    : "&cERROR: The sound &e" + parts[0] + " &cis invalid or misconfigured." + suggestionMsg;
            Bukkit.getConsoleSender().sendMessage(color(pluginName + " " + errorMsg));
        }
    }
}
