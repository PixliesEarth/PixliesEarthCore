package eu.pixliesearth.pixliefun.machine;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.stream.Collectors;

public class Distillery extends MultiBlockMachine {

    public static final SlimefunItemStack icon = new SlimefunItemStack("DISTILLERY", new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/48437fee749cbd68f3443bb06e45058a687c0fca96436cadb538cad59b5216c6")).setDisplayName("§eDistillery").build());

    @ParametersAreNonnullByDefault
    public Distillery() {
        super(PixlieFun.pixlieFunCategory, icon, new ItemStack[]{ null, new ItemStack(Material.BARREL), null, null, new ItemStack(Material.NETHER_BRICK_FENCE), null, null, new CustomItem(Material.DISPENSER, "Dispenser (Facing up)"), null }, BlockFace.SELF);
    }

    @Override
    public List<ItemStack> getDisplayRecipes() {
        return recipes.stream().map(items -> items[0]).collect(Collectors.toList());
    }

    @Override
    public void onInteract(Player p, Block b) {
        Block dispBlock = b.getRelative(BlockFace.DOWN);
        BlockState state = PaperLib.getBlockState(dispBlock, false).getState();

        if (state instanceof Dispenser) {
            Dispenser disp = (Dispenser) state;
            Inventory inv = disp.getInventory();

            for (ItemStack current : inv.getContents()) {
                for (ItemStack convert : RecipeType.getRecipeInputs(this)) {
                    if (convert != null && SlimefunUtils.isItemSimilar(current, convert, true)) {
                        ItemStack adding = RecipeType.getRecipeOutput(this, convert);
                        Inventory outputInv = findOutputInventory(adding, dispBlock, inv);

                        if (outputInv != null) {
                            ItemStack removing = current.clone();
                            removing.setAmount(1);
                            inv.removeItem(removing);
                            outputInv.addItem(adding);
                            p.getWorld().playSound(b.getLocation(), Sound.ENTITY_PLAYER_SPLASH, 1F, 1F);
                            p.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.HAY_BLOCK);
                        } else {
                            SlimefunPlugin.getLocalization().sendMessage(p, "machines.full-inventory", true);
                        }

                        return;
                    }
                }
            }

            SlimefunPlugin.getLocalization().sendMessage(p, "machines.unknown-material", true);
        }
    }

}
