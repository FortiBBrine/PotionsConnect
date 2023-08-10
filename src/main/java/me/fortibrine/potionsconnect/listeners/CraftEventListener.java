package me.fortibrine.potionsconnect.listeners;

import me.fortibrine.potionsconnect.PotionsConnect;
import me.fortibrine.potionsconnect.utils.PotionManager;
import me.fortibrine.potionsconnect.utils.VariableManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Set;

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
        ItemStack item = potionManager.potionFromEffects(potionManager.combinePotions(potionManager.getEffects(inventory.getMatrix())));

        if (item == null) return;
        inventory.setResult(item);
    }

    @EventHandler
    public void levelUpCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();

        if (inventory.getRecipe() == null) return;

        Set<PotionEffect> effects = variableManager.upgradeItem(inventory.getRecipe());


    }

}
