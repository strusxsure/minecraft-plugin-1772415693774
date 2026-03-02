# SoulBoundSMP - Minecraft Plugin

A modern SMP-oriented Soul-based RPG plugin built for **PaperMC 1.21+** using Java 21.

## Features:
- Collect "Souls" from PVP kills (drops Nether Star as usable item)
- Store/global number tracked via JSON flatfile per-player basis.
- Tradeable soul mechanics (via pickup/drop)
- Unlock bonuses via `/souls` menu:
  - Murderer’s Might (+STR)
  - Snatch Sprint (+SPD)
  - Heart Cache (Temporary HP+)
- GUI-based menu on `/souls`
- Configurable messages and timers via `config.yml`

## Permissions:
- None required for play — `/souls` works freely unless admin lock enforced

## Installation:
Ensure running:
- Paper 1.21+
- Plugin compiles using Maven with defined `<repository>` and dependency listed earlier

Run JAR in `plugins` folder.

## Supported Commands:
- `/souls`: View abilities menu

## Planned modules/extensions:
- Soul rituals to resurrect fallen allies
- Passive chain boosts while under certain thresholds

## Diagram:

𝐊𝐢𝐥𝐥𝐳 ➝ 𝐒𝐨𝐮𝐥𝐳 ➝ 𝐆𝐔𝐈 𝐌𝐞𝐧𝐮 ➝ 𝑷𝒆𝒓𝒊𝒐𝒅𝒊𝒄 𝑮𝒂𝒎𝒆 𝑾𝒂𝒈𝒆𝒔!

📌 Requires Java 21 runtime environment!

---

Developed with ❤ by [StormAI](https://github.com/StormAI)