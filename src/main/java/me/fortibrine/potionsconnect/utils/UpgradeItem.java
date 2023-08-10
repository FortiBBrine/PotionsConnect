package me.fortibrine.potionsconnect.utils;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public class UpgradeItem {

    private int duration;
    private int level;
    private Material material;

    public UpgradeItem(int duration, int level, Material material) {
        this.duration = duration;
        this.level = level;
        this.material = material;
    }

}
