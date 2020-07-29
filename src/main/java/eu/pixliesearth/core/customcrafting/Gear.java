package eu.pixliesearth.core.customcrafting;

import net.minecraft.server.v1_16_R1.Enchantment;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Gear {

    protected String e = "-";
    private ItemStack item;
    private List<String> recipes;
    private Enchantment.Rarity rarity;
    private String name;
    private int defense, speed, intelligence, health;

    public Gear(String name, List<String> lore, Material material, Enchantment.Rarity rarity, int amount) {
        this.rarity = rarity;
        this.name = name;
        this.defense = 0;
        this.speed = 0;
        this.intelligence = 0;
        this.health = 0;
        this.item = new ItemStack(material, amount);
    }

    public Gear(String name, List<String> lore, Material material, Enchantment.Rarity rarity, int defense, int speed, int intelligence, int health) {
        this(name, lore, material, rarity, 1); //custom items don't have more than 1 amount on hypixel
        this.defense = defense;
        this.speed = speed;
        this.intelligence = intelligence;
        this.health = health;
    }

    public Enchantment.Rarity getRarity() {
        return rarity;
    }

    public ItemStack getItem() {
        return item;
    }

    public List<String> getRecipes() {
        return recipes;
    }

    public double getPrice() {
        return 100;
    }

    public double getSellPrice() {
        return 100;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public String getName() {
        return name;
    }

    public void onUpdate() { }
    public void onEquip() { }
    public void onUnEquip() { }
    public void onRightClick() { }
}