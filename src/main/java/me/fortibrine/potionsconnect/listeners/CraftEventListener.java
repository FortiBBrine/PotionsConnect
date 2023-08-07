package me.fortibrine.potionsconnect.listeners;

import me.fortibrine.potionsconnect.PotionsConnect;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class CraftEventListener implements Listener {

    private PotionsConnect plugin;
    public CraftEventListener(PotionsConnect plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {

        System.out.println("hi");

        CraftingInventory inventory = event.getInventory();

        ItemStack potion = new ItemStack(Material.POTION);

        PotionMeta meta = (PotionMeta) potion.getItemMeta();

        List<PotionEffect> potionEffects = new ArrayList<>();

        for (ItemStack item : inventory.getMatrix()) {

            if (item == null) continue;
            if (item.getType() != Material.AIR && item.getType() != Material.POTION) return;
            if (item.getType() == Material.AIR) continue;

            PotionMeta craftPotionMeta = (PotionMeta) item.getItemMeta();
            Potion potionItem = Potion.fromItemStack(item);

            potionItem.getEffects().forEach(effect -> {
                boolean hasSimilar = false;

                for (int i = 0; i < potionEffects.size(); i++) {
                    PotionEffect effectInNewItem = potionEffects.get(i);
                    if (effectInNewItem.getType() == effect.getType()) {
                        if (effectInNewItem.getAmplifier() == effect.getAmplifier()) {
                            effectInNewItem = new PotionEffect(effectInNewItem.getType(), effectInNewItem.getDuration(), effectInNewItem.getAmplifier() + 1);
                        } else {
                            effectInNewItem = new PotionEffect(effectInNewItem.getType(), effectInNewItem.getDuration(), Math.max(effect.getAmplifier(), effectInNewItem.getAmplifier()));
                        }
                        potionEffects.set(i, effectInNewItem);
                        System.out.println("set");
                        hasSimilar = true;
                        break;
                    }
                }

                System.out.println(hasSimilar);

                if (!hasSimilar) {
                    potionEffects.add(effect);
                    meta.addCustomEffect(effect, true);

                    System.out.println("add");
                }

            });
        }

        if (meta.getCustomEffects().isEmpty()) return;
        System.out.println("not empty");

        potion.setItemMeta(meta);
        inventory.setResult(potion);


    }

}
