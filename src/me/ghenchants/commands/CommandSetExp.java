package me.ghenchants.commands;

import me.ghenchants.Main;
import me.ghenchants.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetExp implements CommandExecutor {

    String incompletecommand = Main.getInstance().getConfig().getString("Messages.incomplete-command-setxp");
    String nopermission = Main.getInstance().getConfig().getString("Messages.no-permission");

    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        Player p = (Player)sender;
        if (!sender.hasPermission("ghenchants.usecommand.setexp")) {
            sender.sendMessage(ColorUtils.colored(this.nopermission));
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("setexp")) {
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
                p.setLevel(quantity);
                sender.sendMessage(ColorUtils.colored("&aO xp do player &f" + target.getName() + " &afoi setado para &f" + quantity + "&a."));
            }
        }
        return false;
    }
}
