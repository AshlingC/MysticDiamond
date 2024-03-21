package org.ashling.chunkrandomizer;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the ChunkRandomizer plugin. Initializes and manages plugin lifecycle and components.
 */
public final class ChunkRandomizer extends JavaPlugin {

    private static ChunkRandomizer instance;
    private ConfigManager configManager;

    /**
     * Called when the plugin is enabled. Initializes components and registers events.
     */
    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager(this);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ChunkLoadListener(), this);
    }

    /**
     * Gets the singleton instance of this plugin.
     *
     * @return The active ChunkRandomizer instance.
     */
    public static ChunkRandomizer getInstance() {
        return instance;
    }

    /**
     * Gets the configuration manager for this plugin.
     *
     * @return The ConfigManager instance associated with this plugin.
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }
}
