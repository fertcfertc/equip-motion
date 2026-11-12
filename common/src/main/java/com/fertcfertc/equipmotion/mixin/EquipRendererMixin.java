package com.fertcfertc.equipmotion.mixin;

import com.fertcfertc.equipmotion.motion.SwapTransforms;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FirstPersonHandsAndItemsRenderer.class)
abstract class EquipRendererMixin {
    @Inject(method = "submitArmWithItem", at = @At(value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER, ordinal = 0))
    private void equipmotion$pose(PlayerRenderState player, FirstPersonHandsAndItemsRenderState hands,
                                  float partialTick, float pitch, InteractionHand hand, float swing,
                                  ItemStack stack, float lowered, PoseStack pose, SubmitNodeCollector collector,
                                  int light, CallbackInfo callback) {
        if (player.avatarRenderState == null || hands.isScoping) return;
        boolean offHand = hand == InteractionHand.OFF_HAND;
        HumanoidArm arm = player.avatarRenderState.mainArm;
        if (offHand) arm = arm.getOpposite();
        SwapTransforms.apply(pose, stack, arm == HumanoidArm.LEFT, offHand, hands.useItemRemainingTicks > 0, lowered);
    }
}
