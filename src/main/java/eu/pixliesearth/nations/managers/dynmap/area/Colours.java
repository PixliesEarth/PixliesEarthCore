package eu.pixliesearth.nations.managers.dynmap.area;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Random;

@Getter
@AllArgsConstructor
public enum Colours {

    PINK("#f06292", "#e91e63"),
    PURPLE("#d500f9", "#8e24aa"),
    DARK_PURPLE("#6200ea", "#512da8"),
    INDIGO("#3949ab", "#283593"),
    BLUE("#2196f3", "#0d47a1"),
    LIGHT_BLUE("#29b6f6", "#01579b"),
    CYAN("#00bcd4", "#00838f"),
    TEAL("#009688", "#00695c"),
    LIME("#d4e157", "#a0af22"),
    YELLOW("#ffd600", "#c17900"),
    AMBER("#ffc107", "#c79100"),
    ORANGE("#ff9800", "#c66900"),
    DEEP_ORANGE("#ff5722", "#c41c00"),
    ;

    private final String fill;
    private final String stroke;
    private static final Random random = new Random();

    public static Colours getRandom() {
        return Arrays.asList(values()).get(random.nextInt(values().length));
    }

}
