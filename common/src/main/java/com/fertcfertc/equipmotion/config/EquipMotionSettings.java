package com.fertcfertc.equipmotion.config;

import com.fertcfertc.equipmotion.motion.SwapStyle;

public record EquipMotionSettings(boolean enabled, SwapStyle style, double amount, boolean mainHand,
                                   boolean offHand, boolean emptyHand, boolean preserveUseAnimations,
                                   boolean weightedMotion, int responseTicks) {
    public static EquipMotionSettings defaults() {
        return new EquipMotionSettings(true, SwapStyle.SWEEP, 1, true, true, false, true, true, 4);
    }

    public EquipMotionSettings normalized() {
        return new EquipMotionSettings(enabled, style == null ? SwapStyle.SWEEP : style,
                Double.isFinite(amount) ? Math.clamp(amount, 0, 1.5) : 1,
                mainHand, offHand, emptyHand, preserveUseAnimations, weightedMotion, Math.clamp(responseTicks,0,8));
    }
}
