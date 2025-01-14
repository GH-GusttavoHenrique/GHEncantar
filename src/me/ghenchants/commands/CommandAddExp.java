package me.ghenchants.commands;

import me.ghenchants.Main;
import me.ghenchants.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAddExp implements CommandExecutor {

    String incompletecommand = Main.getInstance().getConfig().getString("Messages.incomplete-command-addxp");
    String nopermission = Main.getInstance().getConfig().getString("Messages.no-permission");

    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        Player p = (Player)sender;
        if (!sender.hasPermission("ghenchants.usecommand.addexp")) {
            sender.sendMessage(ColorUtils.colored(this.nopermission));
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("addexp")) {
            if (args.length == 0) {
                sender.sendMessage(ColorUtils.colored(this.incompletecommand));
                return true;
            }
            if (args.length == 1) {
                sender.sendMessage(ColorUtils.colored(this.incompletecommand));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            int quantity = 1;
            if (args.length == 2) {
                try {
                    quantity = Integer.parseInt(args[1]);
                } catch (NumberFormatException exception) {
                    sender.sendMessage(ColorUtils.colored(this.incompletecommand));
                }
                p.setLevel(p.getLevel() + quantity);
                sender.sendMessage(ColorUtils.colored("&aFoi acrescentado &f" + quantity + " &axp na conta de &f" + target.getName() + "&a."));
            }
        }
        return false;
    }
}
