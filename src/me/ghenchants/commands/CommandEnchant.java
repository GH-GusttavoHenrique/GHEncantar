package me.ghenchants.commands;

import me.ghenchants.Main;
import me.ghenchants.inventoryes.EnchantInventory;
import me.ghenchants.inventoryes.PlayerInfoItem;
import me.ghenchants.utils.ColorUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEnchant implements CommandExecutor {
    String noperm = Main.getInstance().getConfig().getString("Messages.no-permission");

    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

        if (!sender.hasPermission("ghenchants.usecommand")) {
            sender.sendMessage(ColorUtils.colored(this.noperm));
            return true;
        }

        Player p = (Player)sender;
        if (args.length >= 0 && cmd.getName().equalsIgnoreCase("encantar")) {

            PlayerInfoItem.playerInfo(p);
            p.openInventory(EnchantInventory.getInventory());

            p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.0F);
            return true;
        }
        return false;
    }
}
