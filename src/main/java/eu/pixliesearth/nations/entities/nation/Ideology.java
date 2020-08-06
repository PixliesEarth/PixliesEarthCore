package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;
import org.bukkit.Material;

public enum Ideology {

    COMMUNISM('4', Material.RED_STAINED_GLASS_PANE),
    SOCIALISM('c', Material.PINK_STAINED_GLASS_PANE),
    THEOCRACY('2', Material.GREEN_STAINED_GLASS_PANE),
    DEMOCRACY('b', Material.CYAN_STAINED_GLASS_PANE),
    MONARCHY('6', Material.YELLOW_STAINED_GLASS_PANE),
    DICTATORSHIP('8', Material.GRAY_STAINED_GLASS_PANE),
    ;

    private final @Getter char colour;
    private final @Getter Material material;

    Ideology(char colour, Material material) {
        this.colour = colour;
        this.material = material;
    }

}
