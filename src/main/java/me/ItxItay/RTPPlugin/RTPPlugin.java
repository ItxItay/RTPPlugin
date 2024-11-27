package me.ItxItay.RTPPlugin;

import me.ItxItay.RTPPlugin.Commands.RTPCommand;
import me.ItxItay.RTPPlugin.Commands.giveRTPCommand;
import me.ItxItay.RTPPlugin.Listeners.ClickEventInventory;
import me.ItxItay.RTPPlugin.Listeners.ClickEventItem;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

import java.util.*;
import java.util.logging.Logger;


public final class RTPPlugin extends JavaPlugin {

    FileConfiguration config = this.getConfig();
    Logger logger = getLogger();
    private static RTPPlugin plugin;

    @Override
    public void onEnable() {
        RTPPlugin.plugin = this;


        logger.info("Programmed by ItxItay!");
        this.getCommand("rtp").setExecutor(new RTPCommand());
        this.getCommand("givertp").setExecutor(new giveRTPCommand());
        getServer().getPluginManager().registerEvents(new ClickEventInventory(), this);
        getServer().getPluginManager().registerEvents(new ClickEventItem(), this);

        registerPermissions();

        saveDefaultConfig();
        reloadConfig();
    }
    @Override
    public void onDisable(){
        logger.info("Programmed by ItxItay!");
        unRegisterPermissions();
    }
    /**
     * Inventory util
     * */
    private static final Map<UUID, Inventory> playerInventory = new HashMap<>();
    public static void setPlayerInventory(UUID playerId, Inventory inventory) {playerInventory.put(playerId, inventory);}
    public static Inventory getPlayerInventory(UUID playerId) {return playerInventory.get(playerId);}
    /***/

    public void registerPermissions(){
        ConfigurationSection settings =  config.getConfigurationSection("RTP.RTPSettings");
        Set<String> LEVELSList = settings.getKeys(false);

        for (String key : LEVELSList){
            Permission perm = new Permission("rtp.permission." + key);
            this.getServer().getPluginManager().addPermission(perm);
        }
    }

    public void unRegisterPermissions(){
        ConfigurationSection settings =  config.getConfigurationSection("RTP.RTPSettings");
        Set<String> LEVELSList = settings.getKeys(false);

        for (String key : LEVELSList){
            this.getServer().getPluginManager().removePermission("rtp.permission." + key);
        }
    }

    public static RTPPlugin getPlugin() {return plugin;}
}
