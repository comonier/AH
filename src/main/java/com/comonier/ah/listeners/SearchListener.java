package com.comonier.ah.listeners;

import com.comonier.ah.managers.SearchManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SearchListener implements Listener {

    private final SearchManager searchManager;

    public SearchListener(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    /**
     * Intercepta a mensagem do chat se o jogador estiver pesquisando
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        // Se o jogador não estiver na lista de busca, ignora
        if (!searchManager.isSearching(player)) {
            return;
        }

        // Cancela o evento para a mensagem não aparecer no chat global
        event.setCancelled(true);

        String query = event.getMessage();
        
        // Verifica se a mensagem está vazia ou nula
        if (query.equals("")) {
            searchManager.stopSearch(player);
            return;
        }

        // Processa a busca (lógica de filtro)
        searchManager.processSearch(player, query);
    }
}
