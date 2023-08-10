package me.fortibrine.potionsconnect.utils;

import lombok.Getter;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VariableManager {

    private FileConfiguration config;
    private PotionManager potionManager;

    @Getter
    private Map<Material, UpgradeItem> items = new HashMap<>();

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

            this.items.put(material, upgradeItem);
        }
    }

    public void upgradeItem(ItemStack item, Material material) {

        System.out.println("I WORK");

        if (item == null) return;

        Material itemType = item.getType();

        if (itemType != Material.POTION && itemType != Material.SPLASH_POTION && itemType != Material.LINGERING_POTION) return;
        PotionMeta meta = (PotionMeta) item.getItemMeta();

        if (!this.items.containsKey(material))  return;
        UpgradeItem upgradeItem = this.items.get(material);

        Set<PotionEffect> effects = potionManager.getEffects(item);
        List<PotionEffect> customEffects = meta.getCustomEffects();
        customEffects.clear();

        effects.forEach(effect -> customEffects.add(new PotionEffect(effect.getType(), effect.getDuration() + upgradeItem.getDuration(), effect.getAmplifier() + upgradeItem.getLevel())));

        item.setItemMeta(meta);

    }

}
