package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;

public enum Religion {

    SHIA_ISLAM('a', Material.LIME_STAINED_GLASS_PANE, "§a☪"),
    SUNNI_ISLAM('2', Material.GREEN_STAINED_GLASS_PANE, "§2☪"),
    ISLAM_OTHER('3', Material.BLUE_STAINED_GLASS_PANE, "§3☪"),
    BUDDHISM('6', Material.BROWN_STAINED_GLASS_PANE, "§6☸"),
    HINDUISM('e', Material.YELLOW_STAINED_GLASS_PANE, "§e🕉"),
    CATHOLICISM('9', Material.BLUE_STAINED_GLASS_PANE, "§6✝"),
    PROTESTANT('b', Material.LIGHT_BLUE_STAINED_GLASS_PANE, "§b✝"),
    ORTHODOX('8', Material.PURPLE_STAINED_GLASS_PANE, "§9☦"),
    CHRISTIANITY_OTHER('b', Material.CYAN_STAINED_GLASS_PANE, "§a✝"),
    JUDAISM('3', Material.LIGHT_BLUE_STAINED_GLASS_PANE, "§b✡"),
    ATHEISM('f', Material.WHITE_STAINED_GLASS_PANE, "§c⚛"),
    ZOROASTRIANISM('6', Material.ORANGE_STAINED_GLASS_PANE, "§c🔥"),
    SECULAR('3', Material.BLUE_STAINED_GLASS_PANE, "§r📕"),
    ;

    private @Getter final char colour;
    private @Getter final Material material;
    private @Getter final String icon;

    Religion(char colour, Material material, String icon) {
        this.colour = colour;
        this.material = material;
        this.icon = icon;
    }

    public String getDisplayName() {
        return "§" + colour + WordUtils.capitalize(name().toLowerCase().replace("_", " "));
    }

}
