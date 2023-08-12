package me.fortibrine.potionsconnect.listeners;

import me.fortibrine.potionsconnect.PotionsConnect;
import me.fortibrine.potionsconnect.utils.PotionManager;
import me.fortibrine.potionsconnect.utils.VariableManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CraftEventListener implements Listener {

    private PotionManager potionManager;
    private FileConfiguration config;
    private VariableManager variableManager;
    public CraftEventListener(PotionsConnect plugin) {
        this.potionManager = plugin.getPotionManager();
        this.config = plugin.getConfig();
        this.variableManager = plugin.getVariableManager();
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();

        List<ItemStack> items = Arrays.stream(inventory.getMatrix()).filter(item -> {
           if (item == null) return false;
           Material type = item.getType();

           return type != Material.POTION && type != Material.SPLASH_POTION;
        }).collect(Collectors.toList());

        if (items.size() != 0) return;

        Material type = Material.POTION;

        ItemStack item = potionManager.potionFromEffects(potionManager.combinePotions(potionManager.getEffects(inventory.getMatrix())), type);

        if (item == null) return;
        inventory.setResult(item);
    }

    @EventHandler
    public void levelUpCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack[] matrix = inventory.getMatrix();

        final Set<Material> materials = variableManager.getItems().keySet();

        List<ItemStack> items = Arrays.stream(matrix).filter(item -> {
            if (item == null) return false;
            Material type = item.getType();

            return materials.contains(type) || type == Material.POTION || type == Material.SPLASH_POTION;
        }).collect(Collectors.toList());

        if (items.size() != 2) return;

        List<ItemStack> containsUpgradeItem = items.stream().filter(item -> materials.contains(item.getType())).collect(Collectors.toList());
        List<ItemStack> containsPotion = items.stream().filter(item -> {
            Material type = item.getType();
            return type == Material.POTION || type == Material.SPLASH_POTION || type == Material.LINGERING_POTION;
        }).collect(Collectors.toList());

        if (containsUpgradeItem.size() == 0 || containsPotion.size() == 0) return;

        ItemStack potion = containsPotion.get(0);
        ItemStack upgradeItem = containsUpgradeItem.get(0);

        ItemStack result = variableManager.upgradeItem(potion, upgradeItem.getType());

        inventory.setResult(result);

    }

}
