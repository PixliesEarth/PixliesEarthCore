package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;

public enum Ideology {

    ANARCHISM('7', Material.BARRIER),
    COMMUNISM('4', Material.IRON_PICKAXE, "§a§oGains the political power someone else spends from justifying on them"),
    SOCIAL_DEMOCRACY('c', Material.POPPY, "§a§oTakes 5x as long to justify war on"),
    CONSERVATIVE_DEMOCRACY('9', Material.CORNFLOWER, "§a§o+10% Political Power from XP"),
    LIBERAL_DEMOCRACY('e', Material.TORCH, "§a§o+0.05 Political power every day per allied nation"),
    ABSOLUTE_MONARCHY('6', Material.GOLDEN_HELMET),
    FASCISM('3', Material.WITHER_ROSE, "§a§o2x Faster Justification"),
    THEOCRACY('2', Material.BOOK, "§a§o15% Cheaper Justification on people who are a different religion"),
    CONSTITUTIONAL_MONARCHY('3', Material.PAPER),
    DICTATORSHIP('5', Material.NETHERITE_SWORD, "§a§o25% more political power"),
    TRIBE('f', Material.CAMPFIRE),
    ;

    private final @Getter char colour;
    private final @Getter Material material;
    private final @Getter String[] traits;

    Ideology(char colour, Material material, String... traits) {
        this.colour = colour;
        this.material = material;
        this.traits = traits;
    }

    public String getDisplayName() {
        return "§" + colour + WordUtils.capitalize(name().toLowerCase().replace("_", " "));
    }

}
