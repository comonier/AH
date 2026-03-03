package com.comonier.ah.managers;
import com.comonier.ah.AH;
import com.comonier.ah.models.AuctionItem;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class ExpiredManager {
    private final AH plugin;
    private final File file;
    private final YamlConfiguration config;
    public ExpiredManager(AH plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "expired.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }
    public void addExpiredItem(UUID owner, AuctionItem item) {
        String path = owner.toString() + "." + System.currentTimeMillis();
        config.set(path + ".item", item.getItemStack());
        config.set(path + ".price", item.getPrice());
        config.set(path + ".category", item.getCategory());
        save();
    }
    public List<AuctionItem> getExpiredItems(UUID owner) {
        List<AuctionItem> expired = new ArrayList<>();
        String uuidStr = owner.toString();
        if (config.contains(uuidStr) == false) return expired;
        for (String timestamp : config.getConfigurationSection(uuidStr).getKeys(false)) {
            String path = uuidStr + "." + timestamp;
            expired.add(new AuctionItem(
                owner,
                config.getItemStack(path + ".item"),
                config.getDouble(path + ".price"),
                0L,
                config.getString(path + ".category")
            ));
        }
        return expired;
    }
    public void removeSpecificExpired(UUID owner, int index) {
        String uuidStr = owner.toString();
        if (config.contains(uuidStr) == false) return;
        List<String> keys = new ArrayList<>(config.getConfigurationSection(uuidStr).getKeys(false));
        if (index >= keys.size()) return;
        config.set(uuidStr + "." + keys.get(index), null);
        if (config.getConfigurationSection(uuidStr).getKeys(false).isEmpty()) config.set(uuidStr, null);
        save();
    }
    private void save() {
        try { config.save(file); } catch (IOException e) { plugin.getLogger().severe("Erro ao salvar expired.yml!"); }
    }
}
