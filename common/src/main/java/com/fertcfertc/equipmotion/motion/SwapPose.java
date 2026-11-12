package com.fertcfertc.equipmotion.motion;

public record SwapPose(float x, float y, float z, float pitch, float yaw, float roll) {
    public static final SwapPose REST = new SwapPose(0,0,0,0,0,0);

    public SwapPose blend(SwapPose other,double amount) {
        float t=(float)Math.clamp(amount,0,1);
        return new SwapPose(x+(other.x-x)*t,y+(other.y-y)*t,z+(other.z-z)*t,
                pitch+(other.pitch-pitch)*t,yaw+(other.yaw-yaw)*t,roll+(other.roll-roll)*t);
    }

    public boolean atRest() {
        return Math.abs(x)+Math.abs(y)+Math.abs(z)<.0001f
                && Math.abs(pitch)+Math.abs(yaw)+Math.abs(roll)<.001f;
    }

    public static SwapPose sample(float lowered, boolean left, double amount, SwapStyle style) {
        float progress = Float.isFinite(lowered) ? Math.clamp(lowered, 0, 1) : 0;
        float eased = progress * progress * (3 - 2 * progress) * (float) amount;
        float arc = (float) Math.sin(progress * Math.PI) * (float) amount;
        float side = left ? -1 : 1;
        return switch (style) {
            case SWEEP -> new SwapPose(side * 0.18f * eased, 0.22f * arc - 0.12f * eased, 0.06f * eased,
                    12 * eased, side * 12 * eased, side * -28 * eased);
            case PIVOT -> new SwapPose(side * 0.04f * eased, 0.16f * arc - 0.08f * eased, 0.1f * eased,
                    30 * eased, side * -18 * eased, side * -8 * eased);
            case LIFT -> new SwapPose(0, -0.18f * eased, -0.08f * eased,
                    -16 * eased, 0, side * 5 * eased);
        };
    }
}
