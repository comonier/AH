package com.comonier.ah.managers;

import com.comonier.ah.AH;
import com.comonier.ah.models.AuctionItem;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuctionManager {

    private final AH plugin;
    private final DatabaseManager db;
    private final List<AuctionItem> activeAuctions;
    private final List<AuctionItem> expiredAuctions;

    public AuctionManager(AH plugin) {
        this.plugin = plugin;
        this.db = new DatabaseManager(plugin);
        this.activeAuctions = db.loadAuctions();
        this.expiredAuctions = new ArrayList<>();
    }

    /**
     * Salva o estado atual da lista ativa no arquivo auctions.yml
     */
    public void saveToDatabase() {
        db.saveAuctions(activeAuctions);
    }

    /**
     * Adiciona um novo item e sincroniza com o banco
     */
    public void listItem(UUID seller, ItemStack item, double price) {
        long hours = plugin.getConfig().getLong("auction.expire-time-hours", 48);
        long duration = hours * 3600 * 1000;

        AuctionItem auctionItem = new AuctionItem(seller, item, price, duration);
        activeAuctions.add(auctionItem);
        
        saveToDatabase();
    }

    /**
     * Remove um item específico da lista ativa (ex: após compra)
     */
    public void removeItem(AuctionItem item) {
        activeAuctions.remove(item);
        saveToDatabase();
    }

    public List<AuctionItem> getActiveAuctions() {
        return activeAuctions;
    }

    /**
     * Filtra itens por categoria
     */
    public List<AuctionItem> getByCategory(String categoryPath) {
        List<AuctionItem> filtered = new ArrayList<>();
        for (AuctionItem item : activeAuctions) {
            if (item.getCategory().equals(categoryPath)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    /**
     * Move leilões expirados para a lista de expiração
     */
    public void checkExpirations() {
        List<AuctionItem> toRemove = new ArrayList<>();
        for (AuctionItem item : activeAuctions) {
            if (item.isExpired()) {
                toRemove.add(item);
            }
        }
        
        if (toRemove.isEmpty()) return;

        for (AuctionItem item : toRemove) {
            activeAuctions.remove(item);
            expiredAuctions.add(item);
        }
        
        saveToDatabase();
    }
}
