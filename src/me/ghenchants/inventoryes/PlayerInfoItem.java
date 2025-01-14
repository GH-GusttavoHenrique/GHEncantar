package me.ghenchants.inventoryes;

import me.ghenchants.Main;
import me.ghenchants.utils.ColorUtils;
import me.ghenchants.utils.ItemBuilderGB;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoItem {

    public static ItemStack playerInfo(Player player) {

        String title = Main.getInstance().getConfig().getString("PlayerInfoItem.item-title");
        List<String> playerinfolore = Main.getInstance().getConfig().getStringList("PlayerInfoItem.lore");
        int slot = Main.getInstance().getConfig().getInt("PlayerInfoItem.slot");

        double balance = Main.getEconomy().getBalance(player);
        int exp = player.getLevel();

        List<String> lore = new ArrayList<>();
        for (String line : playerinfolore) {
            String updatedLine = ColorUtils.colored(line)
                    .replace("{exp}", String.valueOf(exp))
                    .replace("{money}", String.valueOf(balance));
            lore.add(updatedLine);
        }

        ItemStack PlayerInfo = new ItemBuilderGB(Material.SKULL_ITEM)
                .name(ColorUtils.colored(title).replace("{player}", player.getName()))
                .lore(ColorUtils.colored(lore))
                .durability((short)3)
                .skullOwner(player.getName())
                .build();

        EnchantInventory.getInventory().setItem(slot, PlayerInfo);
            return PlayerInfo;
         }
    }
