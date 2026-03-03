package com.comonier.ah.managers;

import com.comonier.ah.AH;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskManager {

    private final AH plugin;
    private final AuctionManager auctionManager;

    public TaskManager(AH plugin) {
        this.plugin = plugin;
        this.auctionManager = plugin.getAuctionManager();
        startExpirationTask();
    }

    /**
     * Inicia uma tarefa repetitiva para verificar itens expirados
     */
    private void startExpirationTask() {
        // Roda a cada 1200 ticks (60 segundos)
        new BukkitRunnable() {
            @Override
            public void run() {
                // Inversão: se o plugin não estiver habilitado, para a tarefa
                if (plugin.isEnabled() == false) {
                    this.cancel();
                    return;
                }

                // Executa a lógica de verificação de expiração no AuctionManager
                auctionManager.checkExpirations();
            }
        }.runTaskTimerAsynchronously(plugin, 1200, 1200);
    }
}
