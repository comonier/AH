package com.comonier.ah.managers;
import com.comonier.ah.AH;
import com.comonier.ah.menus.ActiveAuctionsMenu;
import com.comonier.ah.models.AuctionItem;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
public class SearchManager {
    private final AH plugin;
    private final Map<UUID, String> activeSearches = new HashMap<>();
    public SearchManager(AH plugin) { this.plugin = plugin; }
    public void startSearch(Player p) {
        activeSearches.put(p.getUniqueId(), "");
        p.sendMessage(plugin.getMessageManager().getPrefix() + plugin.getMessageManager().getMessage("lore-search"));
    }
    public void stopSearch(Player p) { activeSearches.remove(p.getUniqueId()); }
    public boolean isSearching(Player p) { return activeSearches.containsKey(p.getUniqueId()); }
    public void processSearch(Player p, String query) {
        stopSearch(p);
        List<AuctionItem> all = plugin.getAuctionManager().getActiveAuctions();
        List<AuctionItem> filtered = new ArrayList<>();
        String q = query.toLowerCase();
        for (AuctionItem item : all) {
            String matName = item.getItemStack().getType().name().toLowerCase();
            String dispName = "";
            if (item.getItemStack().hasItemMeta() && item.getItemStack().getItemMeta().hasDisplayName()) {
                dispName = item.getItemStack().getItemMeta().getDisplayName().toLowerCase();
            }
            if (matName.contains(q) || dispName.contains(q)) {
                filtered.add(item);
            }
        }
        p.sendMessage(plugin.getMessageManager().getPrefix() + "§7Resultados para: §f" + query);
        new ActiveAuctionsMenu(plugin).open(p, 1, filtered, "Busca: " + query);
    }
}
