package com.comonier.ah.managers;
import com.comonier.ah.AH;
import com.comonier.ah.models.AuctionItem;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.List;
public class TaskManager {
    private final AH plugin;
    private final AuctionManager auctionManager;
    public TaskManager(AH plugin) {
        this.plugin = plugin;
        this.auctionManager = plugin.getAuctionManager();
        startExpirationTask();
    }
    private void startExpirationTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (plugin.isEnabled() == false) {
                    this.cancel();
                    return;
                }
                List<AuctionItem> active = auctionManager.getActiveAuctions();
                List<AuctionItem> toRemove = new ArrayList<>();
                for (AuctionItem item : active) {
                    if (item.isExpired()) {
                        toRemove.add(item);
                    }
                }
                if (toRemove.isEmpty()) return;
                for (AuctionItem item : toRemove) {
                    plugin.getExpiredManager().addExpiredItem(item.getSellerUUID(), item);
                    auctionManager.removeItem(item);
                }
                auctionManager.saveToDatabase();
            }
        }.runTaskTimerAsynchronously(plugin, 1200, 1200);
    }
}
