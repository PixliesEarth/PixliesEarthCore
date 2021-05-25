package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;

public enum Ideology {

    ANARCHISM('7', Material.BARRIER),
    COMMUNISM('4', Material.IRON_PICKAXE),
    SOCIAL_DEMOCRACY('c', Material.POPPY),
    CONSERVATIVE_DEMOCRACY('9', Material.CORNFLOWER),
    LIBERAL_DEMOCRACY('e', Material.TORCH),
    ABSOLUTE_MONARCHY('6', Material.GOLDEN_HELMET),
    FASCISM('3', Material.WITHER_ROSE),
    THEOCRACY('2', Material.BOOK),
    CONSTITUTIONAL_MONARCHY('3', Material.PAPER),
    DICTATORSHIP('5', Material.NETHERITE_SWORD),
    TRIBE('f', Material.CAMPFIRE),
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
