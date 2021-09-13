package io.github.rank.mod;

import net.fabricmc.api.ModInitializer;

public class MainClient implements ModInitializer {
    public static final String MOD_NAME = "RANK Client";
    public static final String GAME_VERSION = "1.17.1";
    public static final String MOD_VERSION = "0.0.1";

    private static boolean initialized;

    @Override
    public void onInitialize() {
        if (initialized) throw new RuntimeException("ZoomMod.onInitialize() ran more than one!");

        ZoomMod.INSTANCE.initialize();
        initialized = true;

        PerspectiveMod.INSTANCE.initialize();

        MouseSensitiveMod.INSTANCE.initialize();

        GamingSettings.INSTANCE.initialize();
    }
}
