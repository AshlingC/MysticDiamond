package org.ashling.chunkrandomizer;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private FileConfiguration config;

    // Constructor used in the plugin's runtime
    public ConfigManager(ChunkRandomizer plugin) {
        if (plugin != null) {
            this.config = plugin.getConfig();
        }
    }

    // Constructor used for testing with direct FileConfiguration injection
    public ConfigManager(FileConfiguration config) {
        this.config = config;
    }

    public double getSpawnChance() {
        return config.getDouble("spawn-chance", 0.3);
    }
}
