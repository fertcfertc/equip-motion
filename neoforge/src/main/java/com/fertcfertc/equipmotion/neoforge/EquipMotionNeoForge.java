package com.fertcfertc.equipmotion.neoforge;

import com.fertcfertc.equipmotion.EquipMotion;
import com.fertcfertc.equipmotion.config.EquipMotionConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = EquipMotion.ID, dist = Dist.CLIENT)
public final class EquipMotionNeoForge {
    public EquipMotionNeoForge(ModContainer container) {
        EquipMotion.initialize();
        container.registerExtensionPoint(IConfigScreenFactory.class,
                (mod, parent) -> EquipMotionConfigScreen.create(parent));
    }
}
