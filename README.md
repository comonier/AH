# AH - Advanced Auction House (Modular)

> [!CAUTION]
> **DEVELOPMENT ALERT:** This is the first build (v1.0). It is natural to encounter bugs during this early stage. As time permits, the plugin will be improved and new integrations will be added.

A high-performance, modular Auction House plugin for Minecraft **1.21.1+ (Paper/Spigot)**. This plugin features automated category detection, advanced search, full transaction history, and unique protection block trading.

## 🚀 Key Features
- **Auto-Category Detection:** Items are automatically filtered into categories (Armor, Tools, Spawners, Shulker Boxes, etc.).
- **Claim Blocks Trading:** Integration with GriefPrevention to sell/buy bonus claim blocks directly in the AH.
- **Permanent Protection Listing:** Claim block listings never expire, ensuring steady market availability.
- **Search System:** Real-time item filtering via chat by Name or ID without closing the main flow.
- **Transaction History:** Dedicated GUIs for Sold, Purchased, and Expired items.
- **Discord Integration:** Real-time Webhook announcements for listings, sales, and administrative reloads.
- **Economy Integration:** Full support for Vault

## 🛠️ Commands & Permissions


| Command | Description | Permission |
|:--- |:--- |:--- |
| `/ah` | Opens the main Category GUI | `ah.use` |
| `/ah sell <price>` | Lists the item in your hand for sale | `ah.use` |
| `/ah sellblocks <qty> <price>` | Sells bonus claim blocks (Price: $10-$100 per block) | `ah.use` |
| `/ah active` | Shows your currently active auctions (Click to cancel) | `ah.use` |
| `/ah expired` | Opens the GUI to reclaim expired/cancelled items | `ah.use` |
| `/ah sales` | View your history of sold items | `ah.use` |
| `/ah purchase` | View your history of bought items | `ah.use` |
| `/ah reload` | Reloads config and language files | `ah.admin` |
| `/ah remove <player>` | Safely moves a player's active auctions to expired | `ah.admin` |

## 📦 Installation
1. Ensure you have Vault and GriefPrevention installed.
2. Drop `AH-1.0.jar` into your `/plugins` folder.
3. Configure your Discord Webhook in `config.yml`.
4. Restart your server.

## ⚙️ Requirements
- **Java:** 21 or higher.
- **Platform:** Paper, Spigot, or Purpur 1.21.1+.
- **Dependencies:** Vault API, GriefPrevention.

## 👨‍💻 Developer
Created by **Comonier**.
