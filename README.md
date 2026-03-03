# AH - Advanced Auction House (Modular)

A high-performance, modular Auction House plugin for Minecraft **1.21.1+ (Paper/Spigot)**. This plugin features an automated category detection system, advanced search, and full transaction history.

## 🚀 Key Features
- **Auto-Category Detection:** Items are automatically filtered into categories (Armor, Tools, Spawners, Shulker Boxes, etc.) upon listing.
- **Search System:** Real-time item filtering via chat by Name or ID.
- **Transaction History:** Dedicated GUIs for Sold, Purchased, and Expired items.
- **Modular Design:** Optimized code structure with small, maintainable files (under 150 lines each).
- **Economy Integration:** Full support for [Vault](https://www.spigotmc.org) and compatible economy providers.

## 🛠️ Commands & Permissions

| Command | Description | Permission |
|:--- |:--- |:--- |
| `/ah` | Opens the main Category GUI | `ah.use` |
| `/ah sell <price>` | Lists the item in your hand for sale | `ah.use` |
| `/ah active` | Shows your currently active auctions | `ah.use` |
| `/ah expired` | Opens the GUI to reclaim expired/cancelled items | `ah.use` |
| `/ah sales` | View your history of sold items | `ah.use` |
| `/ah purchase` | View your history of bought items | `ah.use` |

## 📦 Installation
1. Ensure you have [Vault](https://www.spigotmc.org) and an economy plugin (like EssentialsX) installed.
2. Drop `AH-1.0.jar` into your `/plugins` folder.
3. Restart or load using [PlugManX](https://www.spigotmc.org).

## ⚙️ Requirements
- **Java:** 21 or higher.
- **Platform:** Paper, Spigot, or Purpur 1.21.1+.
- **Dependencies:** Vault API.

## 👨‍💻 Developer
Created by **Comonier**.
