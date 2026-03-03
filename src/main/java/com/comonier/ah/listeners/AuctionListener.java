package com.comonier.ah.listeners;

import com.comonier.ah.AH;
import com.comonier.ah.managers.*;
import com.comonier.ah.menus.*;
import com.comonier.ah.models.AuctionItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.List;

public class AuctionListener implements Listener {
    private final AH plugin;
    private final CategoryMenu categoryMenu;
    private final MessageManager mm;

    public AuctionListener(AH plugin, CategoryMenu categoryMenu, SearchManager sm) {
        this.plugin = plugin;
        this.categoryMenu = categoryMenu;
        this.mm = plugin.getMessageManager();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.contains("AH") && !title.contains("Auction") && !title.contains("Leilão")) return;
        event.setCancelled(true);
        Player p = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if (slot == 49) { categoryMenu.open(p); return; }
        if (slot == 13) { openActive(p, "Todos", plugin.getAuctionManager().getActiveAuctions()); return; }
        handleCategoryClick(p, slot);
        handleNav(p, slot);
    }

    private void handleCategoryClick(Player p, int slot) {
        String cat = null;
        if (slot == 19) cat = "cat-cash"; if (slot == 20) cat = "cat-keys";
        if (slot == 21) cat = "cat-protection"; if (slot == 22) cat = "cat-blocks";
        if (slot == 23) cat = "cat-items"; if (slot == 24) cat = "cat-tools";
        if (slot == 25) cat = "cat-armor"; if (slot == 28) cat = "cat-food";
        if (slot == 29) cat = "cat-books"; if (slot == 30) cat = "cat-spawners";
        if (slot == 31) cat = "cat-drops"; if (slot == 32) cat = "cat-shulker";
        if (slot == 33) cat = "cat-farm"; if (slot == 34) cat = "cat-ores";
        if (cat != null) openActive(p, mm.getMessage(cat), plugin.getAuctionManager().getByCategory(cat));
    }

    private void handleNav(Player p, int slot) {
        HistoryMenu history = new HistoryMenu(plugin);
        if (slot == 45) history.open(p, mm.getMessage("menu-sales"), 1, plugin.getSoldManager().getSoldItems(p.getUniqueId()));
        if (slot == 47) history.open(p, mm.getMessage("menu-purchases"), 1, plugin.getPurchaseManager().getPurchasedItems(p.getUniqueId()));
        if (slot == 51) { p.closeInventory(); plugin.getSearchManager().startSearch(p); }
        if (slot == 53) history.open(p, mm.getMessage("menu-expired"), 1, plugin.getExpiredManager().getExpiredItems(p.getUniqueId()));
    }

    private void openActive(Player p, String name, List<AuctionItem> list) {
        new ActiveAuctionsMenu(plugin).open(p, 1, list, name);
    }
}
