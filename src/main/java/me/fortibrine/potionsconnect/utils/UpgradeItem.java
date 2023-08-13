package me.fortibrine.potionsconnect.utils;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

@Getter
public class UpgradeItem {

    private int duration;
    private int level;
    private Material material;
    private ShapedRecipe potionRecipe;
    private ShapedRecipe splashPotionRecipe;

    public UpgradeItem(int duration, int level, Material material, ShapedRecipe potionRecipe, ShapedRecipe splashPotionRecipe) {
        this.duration = duration;
        this.level = level;
        this.material = material;
        this.potionRecipe = potionRecipe;
        this.splashPotionRecipe = splashPotionRecipe;
    }

}
