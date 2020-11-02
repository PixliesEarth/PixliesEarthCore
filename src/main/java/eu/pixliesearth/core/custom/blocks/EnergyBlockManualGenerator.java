package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.interfaces.Energyable;
import eu.pixliesearth.core.objects.Energy;
import eu.pixliesearth.core.objects.Profile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EnergyBlockManualGenerator extends CustomEnergyBlock {

    public double getCapacity() {
        return 20D;
    }

    @Override
    public Material getMaterial() {
        return Material.ORANGE_STAINED_GLASS;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Manual Generator";
    }

    @Override
    public String getUUID() {
        return "Machine:Manual_Generator"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean onBlockIsInteractedWith(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return event.isCancelled();
        Location location = event.getClickedBlock().getLocation();
        CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
        Player player = event.getPlayer();
        for  (Block b : getSurroundingCustomBlocks(location)) {
            CustomBlock c = h.getCustomBlockFromLocation(b.getLocation());
            if (c instanceof Energyable) {
                Main instance = Main.getInstance();
                Profile profile = instance.getProfile(player.getUniqueId());
                if (Energy.take(profile, 0.05)) {
                    h.addPowerToLocation(b.getLocation(), 0.05);
                    location.getWorld().playSound(location, Sound.BLOCK_ANVIL_HIT, 1, 1);
                } else {
                    player.sendActionBar("§cNot enough mana");
                }
                return event.isCancelled();
            }
        }
        return event.isCancelled();
    }

}
