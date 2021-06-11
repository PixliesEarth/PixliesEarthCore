package eu.pixliesearth.nations.managers.dynmap.area;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Random;

@Getter
@AllArgsConstructor
public enum Colours {

    PINK("#f06292", "#e91e63", Material.PINK_STAINED_GLASS_PANE),
    PURPLE("#d500f9", "#8e24aa", Material.PURPLE_STAINED_GLASS_PANE),
    DARK_PURPLE("#6200ea", "#512da8", Material.PURPLE_STAINED_GLASS_PANE),
    INDIGO("#3949ab", "#283593", Material.PURPLE_STAINED_GLASS_PANE),
    BLUE("#2196f3", "#0d47a1", Material.BLUE_STAINED_GLASS_PANE),
    LIGHT_BLUE("#29b6f6", "#01579b", Material.BLUE_STAINED_GLASS_PANE),
    CYAN("#00bcd4", "#00838f", Material.CYAN_STAINED_GLASS_PANE),
    TEAL("#009688", "#00695c", Material.CYAN_STAINED_GLASS_PANE),
    LIME("#d4e157", "#a0af22", Material.LIME_STAINED_GLASS_PANE),
    YELLOW("#ffd600", "#c17900", Material.YELLOW_STAINED_GLASS_PANE),
    AMBER("#ffc107", "#c79100", Material.YELLOW_STAINED_GLASS_PANE),
    ORANGE("#ff9800", "#c66900", Material.ORANGE_STAINED_GLASS_PANE),
    DEEP_ORANGE("#ff5722", "#c41c00", Material.ORANGE_STAINED_GLASS_PANE),
    GREEN("#32B454", "#32914B", Material.GREEN_STAINED_GLASS_PANE),
    RED("#ED3131", "#D14343", Material.RED_STAINED_GLASS_PANE),
    GRAY("#7F7F7F", "#4F4F4F", Material.LIGHT_GRAY_STAINED_GLASS_PANE),
    DARK_GREEN("#197130", "#124B21", Material.GREEN_STAINED_GLASS_PANE),
    DARK_RED("#A90C0C", "#840B0B", Material.RED_STAINED_GLASS_PANE),
    WHITE("#EFEFEF", "#CFCFCF", Material.WHITE_STAINED_GLASS_PANE)
    ;

    private final String fill;
    private final String stroke;
    private final Material material;
    private static final Random random = new Random();

    public static Colours getRandom() {
        return Arrays.asList(values()).get(random.nextInt(values().length));
    }

    public static Colours getByFillAndStroke(String fill, String stroke) {
        for (Colours colours : values())
            if (colours.getFill().equals(fill) && colours.getStroke().equals(stroke)) return colours;
        return null;
    }

}
