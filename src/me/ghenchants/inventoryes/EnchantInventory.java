package me.ghenchants.inventoryes;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import me.ghenchants.Main;
import me.ghenchants.model.InventoryButton;
import me.ghenchants.utils.ColorUtils;
import me.ghenchants.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

public class EnchantInventory {
    public static final Map<String, InventoryButton> inventoryButtons = new LinkedHashMap<>();

    public static final Inventory inventory;

    static {
        String invname = Main.getInstance().getConfig().getString("Inventory.inventory-name");
        int invsize = Main.getInstance().getConfig().getInt("Inventory.inventory-size");
        inventory = Bukkit.createInventory(null, invsize, ColorUtils.colored(invname));

        ConfigurationSection itemSection = Main.getInstance().getConfig().getConfigurationSection("items");

        for (Iterator<String> iterator = itemSection.getKeys(false).iterator(); iterator.hasNext(); ) {
            String key = iterator.next();

            Material material = Material.valueOf(itemSection.getString(key + ".material"));
            String name = itemSection.getString(key + ".name");
            List<String> lore = itemSection.getStringList(key + ".lore");
            String enchant = itemSection.getString(key + ".enchant");
            int xp = itemSection.getInt(key + ".xp");
            int price = itemSection.getInt(key + ".price");
            int slot = itemSection.getInt(key + ".slot");
            int enchantlevel = itemSection.getInt(key + ".enchantlevel");

            ItemBuilder item = (new ItemBuilder(material)).changeItemMeta(itemMeta -> {
                itemMeta.setDisplayName(ColorUtils.colored(name));
                itemMeta.setLore(ColorUtils.colored(lore));
                itemMeta.addEnchant(Enchantment.getByName(enchant), enchantlevel, true);
                itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
            }).setNBTTag("enchant", key);
            inventoryButtons.put(key, new InventoryButton(key, item
                    .wrap(),
                    Enchantment.getByName(enchant), enchantlevel, slot, xp, price));
        }
        inventoryButtons.forEach((k, button) -> inventory.setItem(button.getSlot(), button.getItemStack()));
    }

    public static Inventory getInventory() {
        return inventory;
    }
}
