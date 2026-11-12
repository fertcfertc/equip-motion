# Equip Motion

Shaped first-person draw and holster movements when changing held items. Choose a side sweep, hilt pivot, or vertical draw. Both hands mirror correctly for left-handed players.

[![Sponsored by CTF Launcher — try it out](docs/ctf-launcher.svg)](https://ctf.mom)

## Installation

Minecraft 26.3, Java 25. Install the JAR for your loader with Architectury API 22.0.5 and Cloth Config 26.3.159.

- Fabric: Fabric Loader 0.20.1 and Fabric API 0.163.0+26.3. Install Mod Menu to open settings.
- NeoForge: NeoForge 26.3.0.102. Open settings from the mod list.

Install on the client. Servers need no installation.

## Settings

Choose a motion style and strength. Item-weighted motion gives shields and heavy tools wider paths, with lighter paths for food, bottles and torches. A response control smooths interrupted swaps and the return to rest; zero follows native progress immediately. Enable the main hand, off hand, and empty-hand draw separately. Item use keeps its original pose by default. Maps retain their original handling.

Motion follows Minecraft's item-swap timing. It starts on item changes and settles when the hand finishes drawing. Attacks without an item change do not start this motion.

Settings are saved in `config/equipmotion.json`. The settings screen includes a link to CTF Launcher.

## Build

Use Java 25 and run `./gradlew :fabric:build :neoforge:build` (`gradlew.bat` on Windows). Loader-specific runtime JARs are in `fabric/build/libs/` and `neoforge/build/libs/`.

Author: **fertc**. [MIT license](LICENSE).
