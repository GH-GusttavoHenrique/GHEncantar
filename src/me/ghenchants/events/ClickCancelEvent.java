package me.ghenchants.events;

import me.ghenchants.Main;
import me.ghenchants.inventoryes.EnchantInventory;
import me.ghenchants.model.InventoryButton;
import me.ghenchants.utils.ColorUtils;
import me.ghenchants.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClickCancelEvent implements Listener {

    String noexp = Main.getInstance().getConfig().getString("Messages.no-exp");
    String nocoins = Main.getInstance().getConfig().getString("Messages.no-coins");
    String enchantapplied = Main.getInstance().getConfig().getString("Messages.success");
    String invaliditem = Main.getInstance().getConfig().getString("Messages.invalid-item");
    String alreadyenchanted = Main.getInstance().getConfig().getString("Messages.alreadyenchanted");

    @EventHandler
    public void onCLick(InventoryClickEvent e) {
        if (e.getInventory().equals(EnchantInventory.getInventory())) {
            e.setCancelled(true);

            ItemStack item = e.getCurrentItem();
            Player player = (Player)e.getWhoClicked();
            ItemStack hand = player.getItemInHand();

            if (item == null)
                item = e.getCursor();
            if (item == null || item.getType() == Material.AIR) return;
            if (EnchantInventory.inventoryButtons.get(ItemBuilder.getNBTTag(item, "enchant")) == null) return;

            InventoryButton button = (InventoryButton)EnchantInventory.inventoryButtons.get(ItemBuilder.getNBTTag(item, "enchant"));
            boolean hasAmount = (Main.getEconomy().getBalance((OfflinePlayer)player) >= button.getPrice());

            if (!Main.getInstance().getAllowedItems().contains(hand.getType())) {
                player.sendMessage(ColorUtils.colored(this.invaliditem));
                player.closeInventory();
                return;
            }

            if (player.getItemInHand().getItemMeta().hasEnchant(button.getEnchantment())) {
                player.sendMessage(ColorUtils.colored(String.format(this.alreadyenchanted, button.getEnchantment().getName())));
                player.closeInventory();
            }else
                if (hasAmount && player.getLevel() >= button.getXp()) {

                ItemStack stack = player.getItemInHand().clone();
                ItemMeta meta = stack.getItemMeta();
                meta.addEnchant(button.getEnchantment(), button.getEnchantLevel(), true);
                stack.setItemMeta(meta);
                player.setItemInHand(stack);

                Main.getEconomy().withdrawPlayer((OfflinePlayer)player, button.getPrice());
                player.setLevel(player.getLevel() - button.getXp());

                player.closeInventory();

                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                player.sendMessage(ColorUtils.colored(this.enchantapplied));
            } else {
                double balance = Main.getEconomy().getBalance((OfflinePlayer)player);
                if (balance < button.getPrice())
                    player.sendMessage(String.format(ColorUtils.colored(this.nocoins), button.getPrice()));
                if (player.getLevel() < button.getXp())
                    player.sendMessage(String.format(ColorUtils.colored(this.noexp), button.getXp()));

                player.closeInventory();
            }
        }
    }
}
