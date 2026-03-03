package com.comonier.ah.listeners;

import com.comonier.ah.AH;
import com.comonier.ah.managers.*;
import com.comonier.ah.menus.*;
import com.comonier.ah.models.AuctionItem;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        // TRAVA MÁXIMA: Se o dono do inventário for o nosso MenuManager, CANCELA TUDO.
        if (!(event.getInventory().getHolder() instanceof MenuManager)) return;

        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player p = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();
        String title = event.getView().getTitle();

        if (slot < 0 || slot >= event.getInventory().getSize()) return;

        if (slot == 49) { categoryMenu.open(p); return; }
        if (slot == 13) { new ActiveAuctionsMenu(plugin).open(p, 1, plugin.getAuctionManager().getActiveAuctions(), "Todos"); return; }
        
        handleCategoryClick(p, slot);
        handleNav(p, slot);
        
        if (title.contains("[") && slot < 45) handlePurchase(p, slot);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof MenuManager) {
            e.setCancelled(true);
        }
    }

    private void handlePurchase(Player p, int slot) {
        List<AuctionItem> items = plugin.getAuctionManager().getActiveAuctions();
        if (slot >= items.size()) return;
        AuctionItem a = items.get(slot);
        if (!plugin.getEconomyManager().hasMoney(p.getUniqueId(), a.getPrice())) return;

        plugin.getEconomyManager().withdraw(p.getUniqueId(), a.getPrice());
        plugin.getEconomyManager().deposit(a.getSellerUUID(), a.getPrice());
        plugin.getSoldManager().recordSale(a.getSellerUUID(), a);
        plugin.getPurchaseManager().recordPurchase(p.getUniqueId(), a);
        p.getInventory().addItem(a.getItemStack());
        plugin.getAuctionManager().removeItem(a);
        p.closeInventory();
        
        plugin.getWebhookManager().announce("&f" + p.getName() + " &ecomprou &f" + a.getItemStack().getType().name(), 
            ":moneybag: " + p.getName() + " comprou de " + a.getSellerName());
    }

    private void handleCategoryClick(Player p, int slot) {
        String c = null;
        if (slot == 19) c = "cat-cash"; if (slot == 20) c = "cat-keys";
        if (slot == 21) c = "cat-protection"; if (slot == 22) c = "cat-blocks";
        if (slot == 23) c = "cat-items"; if (slot == 24) c = "cat-tools";
        if (slot == 25) c = "cat-armor"; if (slot == 28) c = "cat-food";
        if (slot == 29) c = "cat-books"; if (slot == 30) c = "cat-spawners";
        if (slot == 31) c = "cat-drops"; if (slot == 32) c = "cat-shulker";
        if (slot == 33) c = "cat-farm"; if (slot == 34) c = "cat-ores";
        if (c != null) new ActiveAuctionsMenu(plugin).open(p, 1, plugin.getAuctionManager().getByCategory(c), mm.getMessage(c));
    }

    private void handleNav(Player p, int slot) {
        HistoryMenu h = new HistoryMenu(plugin);
        if (slot == 45) h.open(p, mm.getMessage("menu-sales"), 1, plugin.getSoldManager().getSoldItems(p.getUniqueId()));
        if (slot == 47) h.open(p, mm.getMessage("menu-purchases"), 1, plugin.getPurchaseManager().getPurchasedItems(p.getUniqueId()));
        if (slot == 51) { p.closeInventory(); plugin.getSearchManager().startSearch(p); }
        if (slot == 53) h.open(p, mm.getMessage("menu-expired"), 1, plugin.getExpiredManager().getExpiredItems(p.getUniqueId()));
    }
}
