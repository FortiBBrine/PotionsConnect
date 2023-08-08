package me.fortibrine.potionsconnect;

import lombok.Getter;
import me.fortibrine.potionsconnect.listeners.CraftEventListener;
import me.fortibrine.potionsconnect.utils.PotionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PotionsConnect extends JavaPlugin {

    @Getter
    private PotionManager potionManager;

    @Override
    public void onEnable() {

        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        }

        this.potionManager = new PotionManager();

        Bukkit.getPluginManager().registerEvents(new CraftEventListener(this), this);
    }

}
