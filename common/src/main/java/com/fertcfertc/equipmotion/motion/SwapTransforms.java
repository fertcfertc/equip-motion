package com.fertcfertc.equipmotion.motion;

import com.fertcfertc.equipmotion.EquipMotion;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.core.component.DataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;

public final class SwapTransforms {
    private static final SwapBlend MAIN=new SwapBlend(), OFF=new SwapBlend();
    private static ClientLevel level;
    private SwapTransforms() {}

    public static boolean apply(PoseStack pose, ItemStack stack, boolean left, boolean offHand,
                                boolean usingItem, float lowered) {
        var settings = EquipMotion.CONFIG.get();
        if(level!=Minecraft.getInstance().level){MAIN.reset();OFF.reset();level=Minecraft.getInstance().level;}
        SwapBlend blend=offHand?OFF:MAIN;
        if (!settings.enabled() || settings.amount() == 0 || (offHand ? !settings.offHand() : !settings.mainHand())
                || (stack.isEmpty() && !settings.emptyHand()) || stack.has(DataComponents.MAP_ID)
                || (usingItem && settings.preserveUseAnimations())){blend.reset();return false;}
        double weight=settings.weightedMotion()?ItemWeight.of(stack):1;
        SwapPose target=EquipMotion.TRACKER.active(offHand,lowered)
                ? SwapPose.sample(lowered,left,settings.amount()*weight,settings.style()) : SwapPose.REST;
        SwapPose motion=blend.update(target,System.nanoTime()/50_000_000.0,settings.responseTicks());
        if(motion.atRest())return false;
        pose.translate(motion.x(), motion.y(), motion.z());
        pose.rotateDegrees(Axis.XP, motion.pitch());
        pose.rotateDegrees(Axis.YP, motion.yaw());
        pose.rotateDegrees(Axis.ZP, motion.roll());
        return true;
    }
}
