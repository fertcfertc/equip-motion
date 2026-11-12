package com.fertcfertc.equipmotion.motion;

public final class SwapBlend {
    private SwapPose pose=SwapPose.REST;
    private double previous=Double.NaN;

    public SwapPose update(SwapPose target,double tick,int response) {
        double elapsed=Double.isFinite(previous)?Math.clamp(tick-previous,0,4):0;
        previous=tick;
        pose=response<=0?target:pose.blend(target,1-Math.exp(-elapsed*6/response));
        if(target.atRest() && pose.atRest())pose=SwapPose.REST;
        return pose;
    }

    public void reset(){pose=SwapPose.REST;previous=Double.NaN;}
}
