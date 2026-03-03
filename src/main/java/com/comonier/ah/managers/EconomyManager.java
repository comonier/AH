package com.comonier.ah.managers;

import com.comonier.ah.AH;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import java.util.UUID;

public class EconomyManager {

    private final AH plugin;
    private Economy econ;

    public EconomyManager(AH plugin) {
        this.plugin = plugin;
        setupEconomy();
    }

    /**
     * Tenta configurar a conexão com o Vault
     */
    private boolean setupEconomy() {
        // Verifica se o Vault está instalado
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = plugin.getServer()
                .getServicesManager().getRegistration(Economy.class);
        
        if (rsp == null) {
            return false;
        }

        this.econ = rsp.getProvider();
        return true;
    }

    /**
     * Verifica se o jogador tem saldo suficiente
     */
    public boolean hasMoney(UUID playerUUID, double amount) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
        double balance = econ.getBalance(player);
        
        // Inversão: se o saldo menos o valor for negativo, ele não tem dinheiro
        if (balance - amount < 0) {
            return false;
        }
        return true;
    }

    /**
     * Remove dinheiro de um jogador
     */
    public void withdraw(UUID playerUUID, double amount) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
        econ.withdrawPlayer(player, amount);
    }

    /**
     * Adiciona dinheiro a um jogador
     */
    public void deposit(UUID playerUUID, double amount) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
        econ.depositPlayer(player, amount);
    }

    public Economy getEconomy() {
        return econ;
    }
}
