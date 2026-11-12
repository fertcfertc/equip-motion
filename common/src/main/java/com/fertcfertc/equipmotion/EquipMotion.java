package com.fertcfertc.equipmotion;

import com.fertcfertc.equipmotion.config.EquipMotionConfig;
import com.fertcfertc.equipmotion.motion.SwapTracker;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.platform.Platform;

public final class EquipMotion {
    public static final String ID = "equipmotion";
    public static final EquipMotionConfig CONFIG = new EquipMotionConfig(Platform.getConfigFolder().resolve(ID + ".json"));
    public static final SwapTracker TRACKER = new SwapTracker();

    private EquipMotion() {}

    public static void initialize() {
        CONFIG.load();
        ClientTickEvent.CLIENT_POST.register(TRACKER::tick);
    }
}
