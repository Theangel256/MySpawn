package theangel256.myspawn.util;

import org.bukkit.Bukkit;

public class VersionUtils {
    private static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    public static String getServerVersion() {
        return VERSION;
    }

    public static boolean isLegacy() {
        return VERSION.startsWith("v1_8") || VERSION.startsWith("v1_9") || VERSION.startsWith("v1_10") || VERSION.startsWith("v1_11") || VERSION.startsWith("v1_12");
    }
}
