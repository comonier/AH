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
    public AuctionManager(AH plugin) {
        this.plugin = plugin;
        this.db = new DatabaseManager(plugin);
        this.activeAuctions = db.loadAuctions();
    }
    public void saveToDatabase() { db.saveAuctions(activeAuctions); }
    public void listItem(UUID seller, ItemStack item, double price, String forcedCategory) {
        long hours = plugin.getConfig().getLong("auction.expire-time-hours", 48);
        long duration = hours * 3600 * 1000;
        AuctionItem auctionItem = new AuctionItem(seller, item, price, duration, forcedCategory);
        activeAuctions.add(auctionItem);
        saveToDatabase();
    }
    public void removeItem(AuctionItem item) {
        activeAuctions.remove(item);
        saveToDatabase();
    }
    public List<AuctionItem> getActiveAuctions() {
        List<AuctionItem> filtered = new ArrayList<>();
        for (AuctionItem item : activeAuctions) {
            if (item.getCategory().equals("cat-protection") == false) {
                filtered.add(item);
            }
        }
        return filtered;
    }
    public List<AuctionItem> getByCategory(String categoryPath) {
        List<AuctionItem> filtered = new ArrayList<>();
        for (AuctionItem item : activeAuctions) {
            if (item.getCategory().equals(categoryPath)) {
                filtered.add(item);
            }
        }
        return filtered;
    }
    public void checkExpirations() {
        List<AuctionItem> toRemove = new ArrayList<>();
        for (AuctionItem item : activeAuctions) {
            if (item.isExpired()) toRemove.add(item);
        }
        if (toRemove.isEmpty()) return;
        for (AuctionItem item : toRemove) {
            plugin.getExpiredManager().addExpiredItem(item.getSellerUUID(), item);
            activeAuctions.remove(item);
        }
        saveToDatabase();
    }
}
