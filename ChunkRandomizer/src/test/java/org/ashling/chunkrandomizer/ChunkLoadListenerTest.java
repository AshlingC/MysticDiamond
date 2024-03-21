package org.ashling.chunkrandomizer;

import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.world.ChunkLoadEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ChunkLoadListenerTest {

    @Mock
    private ChunkLoadEvent mockChunkLoadEvent;

    @Mock
    private Chunk mockChunk;

    @Mock
    private FileConfiguration mockConfig;

    @InjectMocks
    private ChunkRandomizer plugin = new ChunkRandomizer();

    private ChunkLoadListener chunkLoadListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(plugin.getConfigManager()).thenReturn(new ConfigManager(mockConfig));
        when(mockConfig.getDouble("spawn-chance", 0.3)).thenReturn(0.1); // Set a deterministic spawn chance
        chunkLoadListener = new ChunkLoadListener();
        plugin.onEnable(); // Simulate plugin enabling
    }

    @Test
    public void testOnChunkLoad_NewChunk() {
        when(mockChunkLoadEvent.isNewChunk()).thenReturn(true);
        when(mockChunkLoadEvent.getChunk()).thenReturn(mockChunk);

        chunkLoadListener.onChunkLoad(mockChunkLoadEvent);

        // Validate the spawnDiamond method logic or its effects here, depending on implementation
        // This may require additional mocking/spying.
    }

    @Test
    public void testOnChunkLoad_OldChunk() {
        when(mockChunkLoadEvent.isNewChunk()).thenReturn(false);

        chunkLoadListener.onChunkLoad(mockChunkLoadEvent);

        // Verify that no spawning logic occurs for an old chunk.
        verify(mockChunkLoadEvent, never()).getChunk();
    }
}
