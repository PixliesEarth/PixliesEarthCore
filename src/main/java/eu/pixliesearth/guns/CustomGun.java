package eu.pixliesearth.guns;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;
import eu.pixliesearth.guns.events.PixliesGunShootEvent;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;

public abstract class CustomGun {

    public Material getMaterial() {
        return Material.CARROT_ON_A_STICK;
    }

    public ItemStack buildItem() {
        return new ItemBuilder(getMaterial()) {{
            setDisplayName(getDefaultDisplayName());
            if (getDefaultLore() != null)
                addLoreAll(getDefaultLore());
            if (getCustomModelData() != null)
                setCustomModelData(getCustomModelData());
            for (ItemFlag flag : getItemFlags())
                addItemFlag(flag);
            addNBTTag("ammo", Integer.toString(0), NBTTagType.STRING);
        }}.build();
    }

    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
        if (!event.getHand().equals(EquipmentSlot.HAND)) return true;
        final ItemStack itemStack = event.getItem();
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return false;
        // Check for action timer
        String timeString = NBTUtil.getTagsFromItem(itemStack).getString("cooldown");
        if (timeString != null && !timeString.equals("")) {
            if (timeString != null && timeString.equals("reloading")) { // This line causes the reload bug lmao (bc the tag isnt removed)
                NBTUtil.NBTTags nbt = NBTUtil.getTagsFromItem(itemStack);
                String cancelStr = nbt.getString("cancel");
                if (cancelStr == null || cancelStr.equals("") || cancelStr.equals(" ")) cancelStr = Integer.toString(4);
                int cancel = Integer.parseInt(cancelStr) - 1;
                nbt.addTag("cancel", Integer.toString(cancel));
                if (cancel <= 0) {
                    nbt.addTag("cancel", "");
                    nbt.addTag("cooldown", "");
                    event.getPlayer().sendActionBar("§c§lCanceled reload");
                } else {
                    event.getPlayer().sendActionBar("§c§lClick " + cancel + " more times to cancel reload");
                }
                event.getPlayer().getInventory().setItemInMainHand(NBTUtil.addTagsToItem(itemStack, nbt));
                return true;
            } else {
                long time = Long.parseLong(timeString);
                if (!(time <= System.currentTimeMillis())) {
                    return true;
                }
            }
        }
        // Rest of method
        int ammo = Integer.parseInt(NBTUtil.getTagsFromItem(itemStack).getString("ammo"));
        if (ammo <= 0) { // Reload
            CustomGun customGun = (CustomGun) customItem;
            if (Methods.removeRequiredAmount(customGun.getAmmoType().getAmmo().getItem(), event.getPlayer().getInventory())) {
                event.getPlayer().sendActionBar("§b§lReloading...");
                event.getPlayer().getInventory().setItemInMainHand(null);
                final int slotToReloadIn = event.getPlayer().getInventory().getHeldItemSlot();
                String gunID = CustomItemUtil.getUUIDFromItemStack(event.getPlayer().getInventory().getItemInMainHand());
                Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), () -> {
                    if (slotToReloadIn != event.getPlayer().getInventory().getHeldItemSlot() && !CustomItemUtil.getUUIDFromItemStack(event.getPlayer().getInventory().getItemInMainHand()).equals(gunID) && NBTUtil.getTagsFromItem(event.getPlayer().getInventory().getItemInMainHand()).getString("cooldown").equals("reloading")) {
                        event.getPlayer().sendActionBar("§c§lFailed to reload!");
                        for (Entry<Integer, ItemStack> entry : event.getPlayer().getInventory().addItem(getAmmoType().getAmmo().getItem()).entrySet()) {
                            if (entry == null || entry.getValue() == null) continue;
                            event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), entry.getValue());
                        }
                        return;
                    }
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
                    ItemStack itemStack2 = new ItemBuilder(itemStack).setDisplayName(getName(getMaxAmmo())).addNBTTag("ammo", Integer.toString(getMaxAmmo()), NBTTagType.STRING).build();
                    event.getPlayer().sendActionBar("§a§lReloaded!");
                    event.getPlayer().getInventory().setItemInMainHand(itemStack2);
                }, 20L * getDelayToReload());
            } else {
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
                event.getPlayer().sendActionBar("§c§lNO AMMO!");
            }
        } else { // Shoot
            CustomGun customGun = (CustomGun) customItem;
            PixliesAmmo pammo = customGun.getAmmoType().getAmmo().createNewOne(event.getPlayer().getLocation(), this);
            PixliesGunShootEvent shootEvent = new PixliesGunShootEvent(event.getPlayer(), pammo);
            shootEvent.callEvent();
            if (!shootEvent.isCancelled()) {
                event.getPlayer().getInventory().setItemInMainHand(new ItemBuilder(itemStack).setDisplayName(getName(ammo - 1)).addNBTTag("cooldown", Long.toString((System.currentTimeMillis() + getDelayPerShot())), NBTTagType.STRING).addNBTTag("ammo", Integer.toString(ammo - 1), NBTTagType.STRING).build());
                event.getPlayer().playSound(event.getPlayer().getLocation(), "gunshoot", SoundCategory.PLAYERS, 15, 1);
                event.getPlayer().getWorld().playEffect(event.getPlayer().getEyeLocation().add(1, -1, 1), Effect.SMOKE, 2);

                LivingEntity result = null;
                boolean headshot = false;
                int maxDistance = getRange();
                Player player = event.getPlayer();
                Location location = player.getEyeLocation();
                Block block = event.getPlayer().getTargetBlock(null, maxDistance);
                if (block.getType().isSolid()) {
                    maxDistance = (int) Math.min(maxDistance, block.getLocation().distance(location));
                }
                Collection<LivingEntity> entityList = event.getPlayer().getWorld().getNearbyLivingEntities(location, maxDistance);
                if (!entityList.isEmpty()) {
                    Vector vector = location.toVector();
                    x:
                    for (double distance = 0.0; distance <= maxDistance; distance += getAccuracy()) {
                        Vector vector2 = vector.clone().add(location.getDirection().clone().multiply(distance));
                        Location location2 = vector2.toLocation(event.getPlayer().getWorld());
                        BoundingBox axisAlignedBB = new BoundingBox(location2.getX(), location2.getY(), location2.getZ(), location2.getX(), location2.getY(), location2.getZ());
                        for (LivingEntity entity : entityList) {
                            if (entity == null || entity.isDead() || entity.getEntityId() == event.getPlayer().getEntityId()) {
                                continue;
                            } else {
                                BoundingBox axisAlignedBB2 = entity.getBoundingBox();
                                if (axisAlignedBB2.overlaps(axisAlignedBB)) {
                                    result = entity;
                                    headshot = location2.distance(entity.getEyeLocation()) <= 0.5;
                                    break x;
                                }
                            }
                        }
                    }
                }
                if (result == null) {
                    return false;
                } else {
                    //double damage = pammo.getDamage()-(getChesplateToughness(result)/(pammo.getDamage()*1.83333333333));
                    double damage = (getChesplateToughness(result) == 0) ? pammo.getDamage() : ((((7.5 - (getChesplateToughness(result))) - pammo.getDamage()) / pammo.getDamage()) / 2);
                    if (headshot) {
                        damage *= 2;
                    }
                    damage *= 2.5;
                    if (damage < 2.5) damage = 2.0;
                    result.setLastDamageCause(new EntityDamageByEntityEvent(event.getPlayer(), player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage));
                    EntityDamageByEntityEvent nevent = new EntityDamageByEntityEvent(player, result, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage);
                    Bukkit.getPluginManager().callEvent(nevent);
                    if (!nevent.isCancelled()) {
                        if (!(result instanceof Player) || ((Player) result).getGameMode() == GameMode.SURVIVAL) {
                            if (result.getHealth() - damage <= 0) {
                                result.setHealth(0);
                            } else {
                                result.setHealth(result.getHealth() - (damage));
                            }
                            result.damage(0.1, player);
                            result.damage(-0.1, player);
                        }
                    }
                }
            }
        }
        return false;
    }

    protected double getChesplateToughness(LivingEntity e) {
        double value = 0;
        if (e.getEquipment().getChestplate() == null) return 0;
        try {
            for (AttributeModifier a : e.getEquipment().getChestplate().getAttributeModifiers(Attribute.GENERIC_ARMOR_TOUGHNESS)) {
                value += a.getAmount();
            }
            return value;
        } catch (Exception ignore) {
        }
        if (value <= 0) {
            value = switch (e.getEquipment().getChestplate().getType()) {
                case DIAMOND_CHESTPLATE -> 2;
                case NETHERITE_CHESTPLATE -> 3;
                default -> 0;
            };
        }
        return value;
    }

    public List<String> getDefaultLore() {
        List<String> list = new ArrayList<>();
        list.add("§7Ammo: §3" + getAmmoType().name());
        list.add("§7Origin: §b" + getOrigin());
        list.add("§7Range: §3" + getRange() + " blocks");
        list.add("§7Accuracy: §3" + getAccuracy());
        return list;
    }

    public boolean isUnstackable() {
        return true;
    }

    // NEW METHODS

    public List<ItemFlag> getItemFlags() {
        return new ArrayList<>();
    }

    public abstract Integer getCustomModelData();

    public abstract String getDefaultDisplayName();

    public abstract int getMaxAmmo();

    public abstract int getRange();

    public abstract double getAccuracy();

    public abstract long getDelayPerShot();

    public abstract int getDelayToReload();

    public abstract AmmoType getAmmoType();

    public abstract String getOrigin();

    protected String getName(int ammo) {
        return getDefaultDisplayName() + " §8| §8[§c" + ammo + "§7/§c" + getMaxAmmo() + "§8]";
    }

}