# Floristics

### Overview

Floristics is a server plugin which recreates natural plant growth in the world over time. Plants grow in their natural biomes and to approximately their natural density and distribution. This includes all vanilla biomes, growing the appropriate grass, ferns, flowers, trees, seagrass, kelp, lily pads, bamboo, sugarcane, dead bushes, cacti, berry bushes, small and giant mushrooms, and even chorus trees.

The distribution of plants is chunk-based using the world seed and biome, meaning that if a chunk regrows poppies, it will always regrow poppies - and if it doesn't it never will. This means the distribution of plants will always be patchy like a generated world, and won't become unnaturally uniform over time. Maximum plant densities are built-in according to biome and type. The aim is that after enough time to regrow, the world will be indistinguishable from vanilla generation.

### Configuration

Currently the configuration is very simple. The config file has two options:
* `delay` - the number of ticks between growth cycles. Increase this number to make plants grow more slowly. Default is `1`.
* `worlds` - list of worlds growth will occur in. Default is `world` and `world_the_end`.

### GriefPrevention

Floristics is compatible with GriefPrevention. By default, growth does not occur in claims. This can be adjusted by the player who owns the claim by using the command `/floristics` (or `/flo`):
* `/floristics growth` - displays whether growth is currently enabled in the claim you're standing in.
* `/floristics growth <enable|disable>` - enables or disables growth in the claim you're standing in.

### More information
* Floristics is Copyright (C) 2019 Jay Avery. It was originally inspired by [Botany](https://dev.bukkit.org/projects/botany), Copyright (C) 2014 Auke Kok.
* View the source code on [GitHub](https://github.com/JayAvery/floristics).
* Give us [issues](https://github.com/JayAvery/floristics/issues)!
* Donate via [PayPal](http://paypal.me/jayvery).
