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

    /**
     * Adiciona um item à lista de expirados do jogador
     */
    public void addExpiredItem(UUID owner, AuctionItem item) {
        String uuidStr = owner.toString();
        long timestamp = System.currentTimeMillis();
        String path = uuidStr + "." + timestamp;
        
        config.set(path + ".item", item.getItemStack());
        config.set(path + ".price", item.getPrice());

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Erro ao salvar expired.yml!");
        }
    }

    /**
     * Retorna a lista de itens expirados de um jogador
     */
    public List<AuctionItem> getExpiredItems(UUID owner) {
        List<AuctionItem> expired = new ArrayList<>();
        String uuidStr = owner.toString();

        if (config.contains(uuidStr) == false) {
            return expired;
        }

        for (String timestamp : config.getConfigurationSection(uuidStr).getKeys(false)) {
            String path = uuidStr + "." + timestamp;
            expired.add(new AuctionItem(
                owner,
                config.getItemStack(path + ".item"),
                config.getDouble(path + ".price"),
                0
            ));
        }
        return expired;
    }

    /**
     * Remove um item específico da lista de expirados (após ser coletado)
     */
    public void clearExpired(UUID owner) {
        config.set(owner.toString(), null);
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Erro ao limpar itens expirados!");
        }
    }
}
