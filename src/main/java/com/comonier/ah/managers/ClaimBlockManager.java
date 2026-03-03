package com.comonier.ah.managers;
import com.comonier.ah.AH;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import java.util.UUID;
public class ClaimBlockManager {
    private final AH plugin;
    public ClaimBlockManager(AH plugin) {
        this.plugin = plugin;
    }
    public int getBonusBlocks(UUID uuid) {
        PlayerData data = GriefPrevention.instance.dataStore.getPlayerData(uuid);
        return data.getBonusClaimBlocks();
    }
    public void removeBonusBlocks(UUID uuid, int amount) {
        PlayerData data = GriefPrevention.instance.dataStore.getPlayerData(uuid);
        data.setBonusClaimBlocks(data.getBonusClaimBlocks() - amount);
    }
    public void addBonusBlocks(UUID uuid, int amount) {
        PlayerData data = GriefPrevention.instance.dataStore.getPlayerData(uuid);
        data.setBonusClaimBlocks(data.getBonusClaimBlocks() + amount);
    }
}
