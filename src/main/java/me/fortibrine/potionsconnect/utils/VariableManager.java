package me.fortibrine.potionsconnect.utils;

import lombok.Getter;
import me.fortibrine.potionsconnect.PotionsConnect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

            ShapedRecipe potionRecipe = new ShapedRecipe(new NamespacedKey(plugin, UUID.randomUUID().toString()), new ItemStack(Material.POTION));
            ShapedRecipe splashPotionRecipe = new ShapedRecipe(new NamespacedKey(plugin, UUID.randomUUID().toString()), new ItemStack(Material.SPLASH_POTION));

            potionRecipe.shape("AB ", "   ", "   ");
            splashPotionRecipe.shape("AB ", "   ", "   ");

            potionRecipe.setIngredient('A', Material.POTION);
            splashPotionRecipe.setIngredient('A', Material.SPLASH_POTION);
            potionRecipe.setIngredient('B', material);
            splashPotionRecipe.setIngredient('B', material);

            Bukkit.addRecipe(potionRecipe);
            Bukkit.addRecipe(splashPotionRecipe);

            UpgradeItem upgradeItem = new UpgradeItem(duration, level, material, potionRecipe, splashPotionRecipe);
            this.items.put(material, upgradeItem);
        }
    }

    public ItemStack upgradeItem(ItemStack item, Material material) {

        if (item == null) return null;

        Material itemType = item.getType();

        if (itemType != Material.POTION && itemType != Material.SPLASH_POTION && itemType != Material.LINGERING_POTION) return null;

        if (!this.items.containsKey(material)) return null;
        UpgradeItem upgradeItem = this.items.get(material);

        ItemStack result = new ItemStack(item.getType());
        PotionMeta resultMeta = (PotionMeta) result.getItemMeta();

        potionManager.getEffects(item).forEach(effect -> resultMeta.addCustomEffect(new PotionEffect(effect.getType(), effect.getDuration() + upgradeItem.getDuration() * 20, effect.getAmplifier() + upgradeItem.getLevel()), true));

        result.setItemMeta(resultMeta);

        return result;
    }

}
