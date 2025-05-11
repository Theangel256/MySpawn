package theangel256.myspawn.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

public class VersionUtils {
    private static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    public static String getServerVersion() {
        return VERSION;
    }

    public static boolean isLegacy() {
        return VERSION.startsWith("v1_8");
    }

    public static String suggestLegacySound(String name) {
        return switch (name.toUpperCase()) {
            case "ENTITY_PLAYER_LEVELUP" -> "LEVEL_UP";
            case "ENTITY_EXPERIENCE_ORB_PICKUP" -> "ORB_PICKUP";
            case "ENTITY_ITEM_PICKUP" -> "ITEM_PICKUP";
            case "BLOCK_ANVIL_LAND" -> "ANVIL_LAND";
            case "BLOCK_ANVIL_BREAK" -> "ANVIL_BREAK";
            case "BLOCK_ANVIL_USE" -> "ANVIL_USE";
            case "BLOCK_NOTE_BLOCK_PLING" -> "NOTE_PLING";
            case "BLOCK_NOTE_BLOCK_BASS" -> "NOTE_BASS";
            case "BLOCK_NOTE_BLOCK_SNARE" -> "NOTE_SNARE_DRUM";
            case "BLOCK_NOTE_BLOCK_HAT" -> "NOTE_STICKS";
            case "BLOCK_CHEST_OPEN" -> "CHEST_OPEN";
            case "BLOCK_CHEST_CLOSE" -> "CHEST_CLOSE";
            case "ENTITY_ENDERMAN_TELEPORT" -> "ENDERMAN_TELEPORT";
            case "ENTITY_BLAZE_SHOOT" -> "FIREBALL";
            case "BLOCK_FIRE_AMBIENT" -> "FIRE";
            case "ENTITY_GENERIC_EXPLODE" -> "EXPLODE";
            case "ENTITY_ARROW_HIT_PLAYER" -> "SUCCESSFUL_HIT";
            case "BLOCK_LAVA_AMBIENT" -> "LAVA";
            case "ENTITY_WOLF_HOWL" -> "WOLF_HOWL";
            case "ENTITY_WOLF_GROWL" -> "WOLF_GROWL";
            case "ENTITY_ENDER_DRAGON_GROWL" -> "ENDERDRAGON_GROWL";
            case "ENTITY_ZOMBIE_AMBIENT" -> "ZOMBIE_IDLE";
            case "ENTITY_ZOMBIE_ATTACK_IRON_DOOR" -> "ZOMBIE_METAL";
            case "ENTITY_CREEPER_PRIMED" -> "CREEPER_HISS";
            case "BLOCK_WOODEN_DOOR_OPEN" -> "DOOR_OPEN";
            case "BLOCK_WOODEN_DOOR_CLOSE" -> "DOOR_CLOSE";
            case "ENTITY_VILLAGER_TRADE" -> "VILLAGER_HAGGLE";
            default -> null;
        };
    }

    public static EntityType getFireworkEntityType() {
        try {
            return isLegacy() ? EntityType.FIREWORK : EntityType.valueOf("FIREWORK_ROCKET");
        } catch (IllegalArgumentException e) {
            return EntityType.FIREWORK; // Fallback en caso de que FIREWORK_ROCKET no exista
        }
    }
}
