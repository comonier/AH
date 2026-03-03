package com.comonier.ah.commands;

import com.comonier.ah.AH;
import com.comonier.ah.managers.*;
import com.comonier.ah.menus.*;
import com.comonier.ah.models.AuctionItem;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class AuctionCommand implements CommandExecutor {
    private final AH plugin;
    private final CategoryMenu categoryMenu;
    private final MessageManager mm;

    public AuctionCommand(AH plugin, CategoryMenu categoryMenu) {
        this.plugin = plugin;
        this.categoryMenu = categoryMenu;
        this.mm = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { sender.sendMessage(mm.getMessage("only-players")); return true; }
        Player p = (Player) sender;
        if (args.length == 0) { categoryMenu.open(p); return true; }

        String sub = args[0].toLowerCase();
        switch (sub) {
            case "sell": handleSell(p, args); break;
            case "sales": 
            case "purchase": 
            case "expired": 
            case "active": handleMenus(p, sub); break;
            default: sendHelp(p); break;
        }
        return true;
    }

    private void handleSell(Player p, String[] args) {
        if (args.length < 2) { p.sendMessage(mm.getPrefix() + "§cUse: /ah sell <preço>"); return; }
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR) { p.sendMessage(mm.getPrefix() + mm.getMessage("item-error")); return; }
        try {
            double price = Double.parseDouble(args[1]);
            if (price <= 0) { p.sendMessage(mm.getPrefix() + mm.getMessage("invalid-price")); return; }
            plugin.getAuctionManager().listItem(p.getUniqueId(), item.clone(), price);
            p.getInventory().setItemInMainHand(null);
            p.sendMessage(mm.getPrefix() + mm.getMessage("item-added"));
        } catch (NumberFormatException e) { p.sendMessage(mm.getPrefix() + mm.getMessage("invalid-price")); }
    }

    private void handleMenus(Player p, String sub) {
        HistoryMenu history = new HistoryMenu(plugin);
        if (sub.equals("sales")) history.open(p, mm.getMessage("menu-sales"), 1, plugin.getSoldManager().getSoldItems(p.getUniqueId()));
        if (sub.equals("purchase")) history.open(p, mm.getMessage("menu-purchases"), 1, plugin.getPurchaseManager().getPurchasedItems(p.getUniqueId()));
        if (sub.equals("expired")) history.open(p, mm.getMessage("menu-expired"), 1, plugin.getExpiredManager().getExpiredItems(p.getUniqueId()));
        if (sub.equals("active")) {
            List<AuctionItem> myItems = new ArrayList<>();
            for (AuctionItem i : plugin.getAuctionManager().getActiveAuctions()) if (i.getSellerUUID().equals(p.getUniqueId())) myItems.add(i);
            new ActiveAuctionsMenu(plugin).open(p, 1, myItems, "Meus Leilões");
        }
    }

    private void sendHelp(Player p) {
        p.sendMessage("§e---- [ AH - Ajuda ] ----");
        p.sendMessage("§f/ah §7- Abre o menu principal");
        p.sendMessage("§f/ah sell <preço> §7- Vende o item da mão");
        p.sendMessage("§f/ah active §7- Seus leilões ativos");
        p.sendMessage("§f/ah expired §7- Itens expirados/cancelados");
        p.sendMessage("§f/ah sales/purchase §7- Históricos de transações");
    }
}
