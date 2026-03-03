package com.comonier.ah.commands;

import com.comonier.ah.AH;
import com.comonier.ah.managers.*;
import com.comonier.ah.menus.*;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        if (sub.equals("sell")) { handleSell(p, args); return true; }
        handleMenus(p, sub);
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
            
            String name = item.getType().name();
            plugin.getWebhookManager().announce("&f" + p.getName() + " &eanunciou &f" + name + " &epor &a$" + price, 
                ":shopping_cart: " + p.getName() + " anunciou " + name + " por $" + price);
        } catch (NumberFormatException e) { p.sendMessage(mm.getPrefix() + mm.getMessage("invalid-price")); }
    }

    private void handleMenus(Player p, String sub) {
        HistoryMenu h = new HistoryMenu(plugin);
        if (sub.equals("sales")) h.open(p, mm.getMessage("menu-sales"), 1, plugin.getSoldManager().getSoldItems(p.getUniqueId()));
        if (sub.equals("purchase")) h.open(p, mm.getMessage("menu-purchases"), 1, plugin.getPurchaseManager().getPurchasedItems(p.getUniqueId()));
        if (sub.equals("expired")) h.open(p, mm.getMessage("menu-expired"), 1, plugin.getExpiredManager().getExpiredItems(p.getUniqueId()));
        if (sub.equals("active")) p.performCommand("ah");
    }
}
