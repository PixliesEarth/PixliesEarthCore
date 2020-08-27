package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;
import org.bukkit.Material;

public enum Religion {

    SHIA_ISLAM('a', Material.LIME_STAINED_GLASS_PANE),
    SUNNI_ISLAM('2', Material.GREEN_STAINED_GLASS_PANE),
    BUDDHISM('6', Material.BROWN_STAINED_GLASS_PANE),
    HINDUISM('e', Material.YELLOW_STAINED_GLASS_PANE),
    CATHOLICISM('9', Material.BLUE_STAINED_GLASS_PANE),
    EVANGELISM('b', Material.CYAN_STAINED_GLASS_PANE),
    PROTESTANT('b', Material.LIGHT_BLUE_STAINED_GLASS_PANE),
    ORTHODOX('8', Material.GRAY_STAINED_GLASS_PANE),
    JUDAISM('3', Material.LIGHT_BLUE_STAINED_GLASS_PANE),
    ATHEISM('f', Material.WHITE_STAINED_GLASS_PANE),
    ;

    private @Getter final char colour;
    private @Getter final Material material;

    Religion(char colour, Material material) {
        this.colour = colour;
        this.material = material;
    }

}
