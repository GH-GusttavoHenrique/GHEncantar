package me.ghenchants.commands;

import me.ghenchants.Main;
import me.ghenchants.utils.ColorUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReloadPlugin implements CommandExecutor {

    String incompletecommand = Main.getInstance().getConfig().getString("Message.incomplete-command-reloadplugin");
    String nopermission = Main.getInstance().getConfig().getString("Message.no-permission");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

        if(!sender.hasPermission("ghenchants.usecommand.reloadplugin")) {
            sender.sendMessage(ColorUtils.colored(this.nopermission));
            return false;
        }

        Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("ghencantar")) {

            if(args.length == 0) {
                sender.sendMessage(ColorUtils.colored(this.incompletecommand));
                return false;
            }

            if(args.length > 0) {
                if(args[0].equalsIgnoreCase("reload")) {

                    Main.getInstance().reloadConfig();

                    sender.sendMessage(ColorUtils.colored("&aReload do plugin GHEncantar executado com sucesso!"));
                    p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.0F);

                    return true;
                }else {
                    sender.sendMessage(ColorUtils.colored(this.incompletecommand));
                }
            }
        }
        return true;
    }

}
