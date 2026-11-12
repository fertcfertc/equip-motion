package com.fertcfertc.equipmotion.fabric;

import com.fertcfertc.equipmotion.EquipMotion;
import net.fabricmc.api.ClientModInitializer;

public final class EquipMotionFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EquipMotion.initialize();
    }
}
