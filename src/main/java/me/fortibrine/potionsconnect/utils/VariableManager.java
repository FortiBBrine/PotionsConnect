package me.fortibrine.potionsconnect.utils;

import me.fortibrine.potionsconnect.PotionsConnect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VariableManager {

    private FileConfiguration config;
    private PotionManager potionManager;
    private Map<ShapedRecipe, UpgradeItem> items = new HashMap<>();

    public VariableManager(PotionsConnect plugin) {
        this.config = plugin.getConfig();
        this.potionManager = plugin.getPotionManager();

        for (String key : config.getConfigurationSection("items").getKeys(false)) {
            ConfigurationSection configurationSection = config.getConfigurationSection("items." + key);

            Material material = Material.matchMaterial(configurationSection.getString("material"));
            int duration = configurationSection.getInt("duration");
            int level = configurationSection.getInt("level");

            UpgradeItem upgradeItem = new UpgradeItem(duration, level, material);

            NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
            ShapedRecipe recipe = new ShapedRecipe(namespacedKey, new ItemStack(Material.POTION));

            recipe.shape("AB ", "   ", "   ");

            recipe.setIngredient('A', Material.POTION);
            recipe.setIngredient('B', material);

            Bukkit.addRecipe(recipe);

            this.items.put(recipe, upgradeItem);
        }
    }

    public Set<PotionEffect> upgradeItem(Recipe recipe) {
        ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
        ItemStack item = shapedRecipe.getIngredientMap().get('A');

        if (this.items.containsKey(shapedRecipe)) {

        } else {
            return null;
        }

        int duration = this.items.get(shapedRecipe).getDuration();
        int level = this.items.get(shapedRecipe).getLevel();

        Set<PotionEffect> potionEffects = this.potionManager.getEffects(item);
        Set<PotionEffect> result = new HashSet<>();

        potionEffects.forEach(effect -> result.add(new PotionEffect(effect.getType(), effect.getDuration() + duration, effect.getAmplifier() + level)));

        return result;
     }

}
