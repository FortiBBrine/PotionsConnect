package me.fortibrine.potionsconnect.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PotionManager {

    public Set<PotionEffect> getEffects(ItemStack item) {
        if (item == null) return new HashSet<>();
        if (item.getType() != Material.POTION) return new HashSet<>();
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        if (potionMeta == null) return new HashSet<>();

        Set<PotionEffect> potionEffects = new HashSet<>();
        if (potionMeta.getBasePotionData().getType().getEffectType() != null) {
            potionEffects.add(this.getEffects(potionMeta.getBasePotionData()));
        }

        potionEffects.addAll(potionMeta.getCustomEffects());

        return potionEffects;

    }

    public List<PotionEffect> getEffects(ItemStack... items) {
        List<PotionEffect> potionEffects = new ArrayList<>();

        for (ItemStack item : items) {
            potionEffects.addAll(this.getEffects(item));
        }

        return potionEffects;

    }

    public ItemStack potionFromEffects(Collection<? extends PotionEffect> potionEffects) {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();

        potionEffects.forEach(potionEffect -> meta.addCustomEffect(potionEffect, true));

        item.setItemMeta(meta);

        if (potionEffects.size() == 0) {
            return null;
        }

        return item;
    }

    public Set<PotionEffect> combinePotions(PotionEffect... potions) {
        Set<PotionEffect> potionsInSet = Arrays.stream(potions).collect(Collectors.toSet());;
        List<PotionEffect> potionsInList = Arrays.stream(potions).collect(Collectors.toList());;

        Set<PotionEffect> potionEffects = new HashSet<>();

        potionsInSet.forEach(potion -> {
            List<PotionEffect> potionsWthSameType = potionsInList.stream().filter(potionWithTheSameType -> potion.getType() == potionWithTheSameType.getType()).collect(Collectors.toList());

            int duration = 0;
            int amplifier = 0;
            for (PotionEffect potionWithSameType : potionsWthSameType) {
                duration += potionWithSameType.getDuration();
                amplifier += potionWithSameType.getAmplifier();
            }

            amplifier += potionsWthSameType.size() - 1;

            potionEffects.add(new PotionEffect(potion.getType(), duration, amplifier));

        });

        return potionEffects;
    }

    public Set<PotionEffect> combinePotions(Collection<PotionEffect> potionEffects) {
        return this.combinePotions(potionEffects.toArray(new PotionEffect[0]));
    }

    public PotionEffect getEffects(PotionData data) {
        PotionType type = data.getType();

        if (type == PotionType.AWKWARD) {
            return null;
        }
        PotionEffectType effectType = type.getEffectType();

        int level = 1;

        int duration = 180;
        if (effectType == PotionEffectType.SPEED) {
            duration = 180;
            if (data.isUpgraded()) {
                duration = 90;
                level = 2;
            }
            if (data.isExtended()) {
                duration = 480;
            }
        } else if (effectType == PotionEffectType.SLOW) {
            duration = 90;
            if (data.isUpgraded()) {
                duration = 20;
                level = 4;
            }
            if (data.isExtended()) {
                duration = 240;
            }
        } else if (effectType == PotionEffectType.JUMP) {
            duration = 180;
            if (data.isUpgraded()) {
                duration = 90;
                level = 2;
            }
            if (data.isExtended()) {
                duration = 480;
            }
        } else if (effectType == PotionEffectType.INCREASE_DAMAGE) {
            duration = 180;
            if (data.isUpgraded()) {
                duration = 90;
                level = 2;
            }
            if (data.isExtended()) {
                duration = 480;
            }
        } else if (effectType == PotionEffectType.HEAL) {
            if (data.isUpgraded()) {
                level = 2;
            }
        } else if (effectType == PotionEffectType.HARM) {
            if (data.isUpgraded()) {
                level = 2;
            }
        } else if (effectType == PotionEffectType.POISON) {
            duration = 45;
            if (data.isUpgraded()) {
                duration = 21;
                level = 2;
            }
            if (data.isExtended()) {
                duration = 90;
            }
        } else if (effectType == PotionEffectType.REGENERATION) {
            duration = 45;
            if (data.isUpgraded()) {
                level = 2;
                duration = 22;
            }
            if (data.isExtended()) {
                duration = 90;
            }
        } else if (effectType == PotionEffectType.FIRE_RESISTANCE) {
            duration = 180;
            if (data.isExtended()) {
                duration = 480;
            }
        } else if (effectType == PotionEffectType.WATER_BREATHING) {
            duration = 180;
            if (data.isExtended()) {
                duration = 480;
            }
        } else if (effectType == PotionEffectType.NIGHT_VISION) {
            duration = 180;
            if (data.isExtended()) {
                duration = 480;
            }
        } else if (effectType == PotionEffectType.INVISIBILITY) {
            duration = 180;
            if (data.isExtended()) {
                duration = 480;
            }
        } else if (effectType == PotionEffectType.SLOW_FALLING) {
            duration = 80;
            if (data.isExtended()) {
                duration = 240;
            }
        } else if (effectType == PotionEffectType.WEAKNESS) {
            duration = 90;
            if (data.isExtended()) {
                duration = 240;
            }
        }
        return new PotionEffect(effectType, duration * 20, level - 1);
    }

}
