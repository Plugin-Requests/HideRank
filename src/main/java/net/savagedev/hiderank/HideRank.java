package net.savagedev.hiderank;

import net.milkbowl.vault.chat.Chat;
import net.savagedev.hiderank.commands.HideRankCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class HideRank extends JavaPlugin {
    private Chat chat;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.hookVault();
        this.initCommands();
    }

    private void initCommands() {
        final PluginCommand command = this.getCommand("hiderank");
        if (command != null) {
            command.setExecutor(new HideRankCommand(this));
        }
    }

    private void hookVault() {
        final RegisteredServiceProvider<Chat> chatProvider = this.getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider == null) {
            this.getLogger().log(Level.SEVERE, "No chat provider found! Disabling plugin.");
            this.getServer().getPluginManager().disablePlugin(this);
        } else {
            this.chat = chatProvider.getProvider();
        }
    }

    public Chat getChat() {
        return this.chat;
    }
}
