package com.fertcfertc.equipmotion.fabric;

import com.fertcfertc.equipmotion.config.EquipMotionConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public final class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return EquipMotionConfigScreen::create;
    }
}
