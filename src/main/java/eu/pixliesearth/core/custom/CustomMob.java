package eu.pixliesearth.core.custom;

import eu.pixliesearth.core.objects.PixliesCalendar;
import lombok.Getter;
import org.bukkit.block.Biome;

import java.util.Arrays;
import java.util.List;

public enum CustomMob {

    RABID_WOLF(RabidWolf.class, 0.1, 0.6, 64,  Arrays.asList(PixliesCalendar.PixliesSeasons.values()), Biome.WOODED_MOUNTAINS, Biome.WOODED_BADLANDS_PLATEAU, Biome.WOODED_HILLS, Biome.TAIGA, Biome.TAIGA_HILLS, Biome.TAIGA_MOUNTAINS, Biome.SNOWY_MOUNTAINS),

    ;

    private @Getter final Class<?> clazz;
    private @Getter final double probabilityFrom;
    private @Getter final double probabilityTo;
    private @Getter final int spawnAbove;
    private @Getter final List<PixliesCalendar.PixliesSeasons> seasons;
    private @Getter final List<Biome> biomes;

    CustomMob(Class<?> clazz, double probabilityFrom, double probabilityTo, int spawnAbove, List<PixliesCalendar.PixliesSeasons> seasons, Biome... biomes) {
        this.clazz = clazz;
        this.probabilityFrom = probabilityFrom;
        this.probabilityTo = probabilityTo;
        this.spawnAbove = spawnAbove;
        this.seasons = seasons;
        this.biomes = Arrays.asList(biomes);
    }

}
