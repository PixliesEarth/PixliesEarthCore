package eu.pixliesearth.core.customcrafting;

import org.bukkit.inventory.ItemStack;

public class CustomRecipeBuilder {

    private final CustomRecipe recipe;

    public CustomRecipeBuilder() {
        this.recipe = new CustomRecipe();
    }

    public CustomRecipeBuilder setFirstSlot(ItemStack s1) {
        recipe.setS1(s1);
        return this;
    }

    public CustomRecipeBuilder setSecondSlot(ItemStack s2) {
        recipe.setS2(s2);
        return this;
    }

    public CustomRecipeBuilder setThirdSlot(ItemStack s3) {
        recipe.setS2(s3);
        return this;
    }

    public CustomRecipeBuilder setFourthSlot(ItemStack s4) {
        recipe.setS2(s4);
        return this;
    }

    public CustomRecipeBuilder setFifthSlot(ItemStack s5) {
        recipe.setS2(s5);
        return this;
    }

    public CustomRecipeBuilder setSixthSlot(ItemStack s6) {
        recipe.setS2(s6);
        return this;
    }

    public CustomRecipeBuilder setSeventhSlot(ItemStack s7) {
        recipe.setS2(s7);
        return this;
    }

    public CustomRecipeBuilder setEighthSlot(ItemStack s8) {
        recipe.setS2(s8);
        return this;
    }

    public CustomRecipeBuilder setNinthSlot(ItemStack s9) {
        recipe.setS2(s9);
        return this;
    }

    public CustomRecipeBuilder setResult(ItemStack result) {
        recipe.setResult(result);
        return this;
    }

    public CustomRecipe build() {
        return recipe;
    }

}
