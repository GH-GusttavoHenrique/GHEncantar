package me.ghenchants;

import me.ghenchants.commands.CommandAddExp;
import me.ghenchants.commands.CommandEnchant;
import me.ghenchants.commands.CommandReloadPlugin;
import me.ghenchants.commands.CommandSetExp;
import me.ghenchants.events.ClickCancelEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private List<Material> allowedItems;

    FileConfiguration config;
    File cfile;

    public void onEnable() {
        getCommand("encantar").setExecutor((CommandExecutor)new CommandEnchant());
        getCommand("setexp").setExecutor((CommandExecutor)new CommandSetExp());
        getCommand("addexp").setExecutor((CommandExecutor)new CommandAddExp());
        getCommand("ghencantar").setExecutor((CommandExecutor)new CommandReloadPlugin());

        Bukkit.getPluginManager().registerEvents((Listener)new ClickCancelEvent(), (Plugin)this);

        loadAllowedItems();
        setupEconomy();
    }

    public void loadAllowedItems() {
        List<String> itemNames = getConfig().getStringList("Allowed-items");
        allowedItems = new ArrayList<>();

        for (String itemName : itemNames) {
            try {
                Material material = Material.valueOf(itemName.toUpperCase());
                allowedItems.add(material);
            } catch (IllegalArgumentException e) {
                getLogger().warning("Material inválido no config.yml: " + itemName);
            }
        }
    }

    public List<Material> getAllowedItems() {
        return allowedItems;
    }

    public void onLoad() {
        saveDefaultConfig();
    }

    public static Economy econ = null;

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        econ = (Economy)rsp.getProvider();
        return (econ != null);
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Main getInstance() {
        return (Main)getPlugin(Main.class);
    }
}
