package io.github.rank.mod;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;

import net.fabricmc.api.ModInitializer;

import java.io.File;
import java.time.Instant;

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
