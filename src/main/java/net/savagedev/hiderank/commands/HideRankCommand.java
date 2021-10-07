package net.savagedev.hiderank.commands;

import net.savagedev.hiderank.HideRank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HideRankCommand implements CommandExecutor {
    private final HideRank hideRank;

    public HideRankCommand(HideRank hideRank) {
        this.hideRank = hideRank;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        final Player player = (Player) sender;

        final String defaultGroup = this.hideRank.getConfig().getString("default-rank");
        final String primaryGroup = this.hideRank.getChat().getPrimaryGroup(player);

        if (primaryGroup.equalsIgnoreCase(defaultGroup)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.hideRank.getConfig().getString("messages.already-default")));
            return true;
        }

        final String groupPrefix = this.hideRank.getChat().getGroupPrefix(player.getLocation().getWorld(), primaryGroup);
        final String currentPrefix = this.hideRank.getChat().getPlayerPrefix(player);

        if (!currentPrefix.equals(groupPrefix)) {
            this.hideRank.getChat().setPlayerPrefix(player, null);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.hideRank.getConfig().getString("messages.rank-unhidden")));
        } else {
            this.hideRank.getChat().setPlayerPrefix(player, this.hideRank.getChat().getGroupPrefix(player.getLocation().getWorld(), defaultGroup));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.hideRank.getConfig().getString("messages.rank-hidden")));
        }
        return true;
    }
}
