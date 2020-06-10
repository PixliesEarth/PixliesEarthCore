package eu.pixliesearth.core.listener;

import eu.pixliesearth.core.customitems.ci.ItemExplosivePick;
import eu.pixliesearth.core.customitems.ci.ItemExplosiveShovel;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        //Explosive Pick
        if (!event.isCancelled()) {
            if (event.getPlayer().getInventory().getItemInMainHand() != null) {
                if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE || event.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_SHOVEL) {
                    System.out.println("Right material");
                    if (event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
                        System.out.println("Has meta");
                        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore() == new ItemExplosivePick().getLore() || event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore() == new ItemExplosiveShovel().getLore()) {
                            System.out.println("Has correct meta");
                            Block b = event.getBlock();
                            if (b.getType() != Material.TORCH
                                    && b.getType() != Material.REDSTONE_TORCH
                                    && b.getType() != Material.REDSTONE_WALL_TORCH
                                    && b.getType() != Material.WALL_TORCH
                                    && b.getType() != Material.END_ROD
                                    && b.getType() != Material.TRIPWIRE_HOOK) {
                                ArrayList<Block> blocks = new ArrayList<>();


                                Block up = b.getRelative(BlockFace.UP, 1);
                                Block down = b.getRelative(BlockFace.DOWN, 1);
                                Block right = b.getRelative(BlockFace.EAST, 1);
                                Block left = b.getRelative(BlockFace.WEST, 1);
                                Block south = b.getRelative(BlockFace.SOUTH, 1);
                                Block north = b.getRelative(BlockFace.NORTH, 1);
                                Block downeast = down.getRelative(BlockFace.EAST, 1);
                                Block downwest = down.getRelative(BlockFace.WEST, 1);
                                Block downsouth = down.getRelative(BlockFace.SOUTH, 1);
                                Block downnorth = down.getRelative(BlockFace.NORTH, 1);
                                Block upeast = up.getRelative(BlockFace.EAST, 1);
                                Block upwest = up.getRelative(BlockFace.WEST, 1);
                                Block upsouth = up.getRelative(BlockFace.SOUTH, 1);
                                Block upnorth = up.getRelative(BlockFace.NORTH, 1);
                                Block downsoutheast = downsouth.getRelative(BlockFace.EAST, 1);
                                Block downsouthwest = downsouth.getRelative(BlockFace.WEST, 1);
                                Block downnorthwest = downnorth.getRelative(BlockFace.WEST, 1);
                                Block downnortheast = downnorth.getRelative(BlockFace.EAST, 1);
                                Block southeast = south.getRelative(BlockFace.EAST, 1);
                                Block southwest = south.getRelative(BlockFace.WEST, 1);
                                Block northeast = north.getRelative(BlockFace.EAST, 1);
                                Block northwest = north.getRelative(BlockFace.WEST, 1);
                                Block upsoutheast = upsouth.getRelative(BlockFace.EAST, 1);
                                Block upsouthwest = upsouth.getRelative(BlockFace.WEST, 1);
                                Block upnorthwest = upnorth.getRelative(BlockFace.WEST, 1);
                                Block upnortheast = upnorth.getRelative(BlockFace.EAST, 1);

                                blocks.add(b);
                                blocks.add(up);
                                blocks.add(down);
                                blocks.add(right);
                                blocks.add(left);
                                blocks.add(south);
                                blocks.add(north);
                                blocks.add(downeast);
                                blocks.add(downwest);
                                blocks.add(downsouth);
                                blocks.add(downnorth);
                                blocks.add(upeast);
                                blocks.add(upwest);
                                blocks.add(upnorth);
                                blocks.add(upsouth);
                                blocks.add(downsoutheast);
                                blocks.add(downsouthwest);
                                blocks.add(downnorthwest);
                                blocks.add(downnortheast);
                                blocks.add(southeast);
                                blocks.add(southwest);
                                blocks.add(northeast);
                                blocks.add(northwest);
                                blocks.add(upsoutheast);
                                blocks.add(upsouthwest);
                                blocks.add(upnorthwest);
                                blocks.add(upnortheast);

                                event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.2F, 1F);
                                event.getPlayer().getWorld().createExplosion(event.getBlock().getLocation(), 0.0F);
                                int counter = 0;
                                for (Block block : blocks) {
                                    if (block.getType() != Material.BEDROCK
                                            && block.getType() != Material.COMMAND_BLOCK
                                            && block.getType() != Material.CHAIN_COMMAND_BLOCK
                                            && block.getType() != Material.REPEATING_COMMAND_BLOCK
                                            && block.getType() != Material.WATER
                                            && block.getType() != Material.LAVA
                                            && block.getType() != Material.BARRIER
                                            && block.getType() != Material.ARMOR_STAND
                                            && block.getType() != Material.AIR)  {
                                        block.breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
                                        block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
                                        counter++;
                                    }
                                }
                                if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                                    Damageable meta = (Damageable) event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                                    meta.setDamage(meta.getDamage() + counter);
                                }
                            } else {
                                event.setCancelled(true);
                                event.getBlock().breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
                                event.getPlayer().sendMessage(Lang.VERY_SMART.get(event.getPlayer()));
                            }

                        }
                    }
                }
            }
        }

    }

}