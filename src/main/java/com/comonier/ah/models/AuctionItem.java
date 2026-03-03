package com.comonier.ah.models;

import com.comonier.ah.utils.CategoryDetector;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import java.util.UUID;

public class AuctionItem {
    private final UUID sellerUUID;
    private final String sellerName;
    private final ItemStack itemStack;
    private final double price;
    private final long expiryTime;
    private final String category;

    public AuctionItem(UUID sellerUUID, ItemStack item, double price, long duration) {
        this.sellerUUID = sellerUUID;
        this.sellerName = Bukkit.getOfflinePlayer(sellerUUID).getName();
        this.itemStack = item;
        this.price = price;
        this.expiryTime = System.currentTimeMillis() + duration;
        this.category = CategoryDetector.getCategoryPath(item);
    }

    public UUID getSellerUUID() { return sellerUUID; }
    public String getSellerName() { return sellerName != null ? sellerName : "Desconhecido"; }
    public ItemStack getItemStack() { return itemStack; }
    public double getPrice() { return price; }
    public long getExpiryTime() { return expiryTime; }
    public String getCategory() { return category; }
    public boolean isExpired() { return (expiryTime - System.currentTimeMillis()) <= 0; }
}
