package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.utils.NBTUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class CustomMoneyPickupListener extends CustomListener {

    @EventHandler
    public void onPickup(PlayerAttemptPickupItemEvent event) {
        Item item = event.getItem();
        ItemStack itemStack = item.getItemStack();
        if (itemStack.hasDisplayName() && !itemStack.getDisplayName().equalsIgnoreCase("money form death")) return;
        NBTUtil.NBTTags nbt = NBTUtil.getTagsFromItem(itemStack);
        if (nbt == null) return;
        if (nbt.getString("money") == null || nbt.getString("money").isEmpty()) return;
        double amount = Double.parseDouble(nbt.getString("money"));
        String owner = nbt.getString("owner");
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        profile.depositMoney(amount, "Death-money from " + owner);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        event.setCancelled(true);
        item.remove();
    }

}
