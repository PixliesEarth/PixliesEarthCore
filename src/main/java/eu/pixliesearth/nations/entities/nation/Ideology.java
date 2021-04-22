package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;

public enum Ideology {

    PEOPLES_REPUBLIC('4', Material.RED_STAINED_GLASS_PANE),
    THEOCRACY('2', Material.GREEN_STAINED_GLASS_PANE),
    REPUBLIC('b', Material.CYAN_STAINED_GLASS_PANE),
    MONARCHY('6', Material.YELLOW_STAINED_GLASS_PANE),
    CONSTITUTIONAL_MONARCHY('3', Material.BLUE_STAINED_GLASS_PANE),
    DICTATORSHIP('5', Material.PURPLE_STAINED_GLASS_PANE),
    TRIBE('f', Material.WHITE_STAINED_GLASS_PANE),
    ;

    private final @Getter char colour;
    private final @Getter Material material;

    Ideology(char colour, Material material) {
        this.colour = colour;
        this.material = material;
    }

    public String getDisplayName() {
        return "§" + colour + WordUtils.capitalize(name().toLowerCase().replace("_", " "));
    }

}
