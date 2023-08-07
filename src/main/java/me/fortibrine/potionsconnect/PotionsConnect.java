package me.fortibrine.potionsconnect;

import me.fortibrine.potionsconnect.listeners.CraftEventListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class PotionsConnect extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new CraftEventListener(this), this);

        NamespacedKey key = new NamespacedKey(this, "potions");
        ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.POTION));

        recipe.shape("AB ", "   ", "   ");
        recipe.setIngredient('A', Material.POTION);
        recipe.setIngredient('B', Material.POTION);

        Bukkit.addRecipe(recipe);
    }

}
