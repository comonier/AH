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
    public AuctionItem(UUID sellerUUID, ItemStack item, double price, long duration, String forcedCategory) {
        this.sellerUUID = sellerUUID;
        this.sellerName = Bukkit.getOfflinePlayer(sellerUUID).getName();
        this.itemStack = item;
        this.price = price;
        if (forcedCategory != null) {
            this.category = forcedCategory;
            this.expiryTime = -1;
        } else {
            this.category = CategoryDetector.getCategoryPath(item);
            this.expiryTime = System.currentTimeMillis() + duration;
        }
    }
    public UUID getSellerUUID() { return sellerUUID; }
    public String getSellerName() { return sellerName != null ? sellerName : "Desconhecido"; }
    public ItemStack getItemStack() { return itemStack; }
    public double getPrice() { return price; }
    public long getExpiryTime() { return expiryTime; }
    public String getCategory() { return category; }
    public boolean isExpired() {
        if (expiryTime == -1) return false;
        return (expiryTime - System.currentTimeMillis()) > 0 == false;
    }
}
