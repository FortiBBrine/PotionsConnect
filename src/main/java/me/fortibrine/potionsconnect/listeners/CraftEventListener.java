package me.fortibrine.potionsconnect.listeners;

import me.fortibrine.potionsconnect.PotionsConnect;
import me.fortibrine.potionsconnect.utils.PotionManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class CraftEventListener implements Listener {

    private PotionManager potionManager;
    private FileConfiguration config;
    public CraftEventListener(PotionsConnect plugin) {
        this.potionManager = plugin.getPotionManager();
        this.config = plugin.getConfig();
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack item = potionManager.potionFromEffects(potionManager.combinePotions(potionManager.getEffects(inventory.getMatrix())));
        inventory.setResult(item);
    }

}
