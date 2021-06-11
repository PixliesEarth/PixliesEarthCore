package eu.pixliesearth.nations.entities.chunk;

import eu.pixliesearth.utils.SkullCreator;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public enum NationChunkType {

    NORMAL("§7§lNormal", SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/8449b9318e33158e64a46ab0de121c3d40000e3332c1574932b3c849d8fa0dc2"), true),
    CAPITAL("§b§lCapital", SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/2dcbba6c751dab5f2b309bc5941e8ea79877cce4256936a189811ed7ec36d25c"), false),
    CITY("§d§lCity", SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/75465f77e7fd4217384998c33859ad9636e1893bee405dd2519423b51767"), false),
    HOSPITAL("§c§lHospital", SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/2528cf6fab8ae7effb51cd3ac42d2ee27504e44002fa4f629a45090fa167a478"), true),
    BANK("§a§lBank", SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/b527f1b6f021b39a39bf92df540d7ea00765e4f3c6cb0b7e378c97347840906"), true),
    ;

    private @Getter final String displayName;
    private @Getter final ItemStack icon;
    private @Getter final boolean changeAble;

    NationChunkType(String displayName, ItemStack icon, boolean chooseAble) {
        this.displayName = displayName;
        this.icon = icon;
        this.changeAble = chooseAble;
    }

}
