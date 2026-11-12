package com.fertcfertc.equipmotion.motion;

import com.fertcfertc.equipmotion.EquipMotion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.Item;

public final class SwapTracker {
    private LocalPlayer player;
    private ClientLevel level;
    private Item main;
    private Item off;
    private final int[] remaining = new int[2];
    private final int[] age = new int[2];

    public void tick(Minecraft client) {
        if (client.player != player || client.level != level) {
            level = client.level;
            player = client.player;
            main = player == null ? null : player.getMainHandItem().getItem();
            off = player == null ? null : player.getOffhandItem().getItem();
            remaining[0] = remaining[1] = 0;
        }
        if (player == null || client.isPaused()) return;
        for (int index = 0; index < 2; index++) {
            if (remaining[index] > 0) remaining[index]--;
            age[index]++;
        }
        Item nextMain = player.getMainHandItem().getItem();
        Item nextOff = player.getOffhandItem().getItem();
        if (nextMain != main) start(0);
        if (nextOff != off) start(1);
        main = nextMain;
        off = nextOff;
        if (!EquipMotion.CONFIG.get().enabled()) remaining[0] = remaining[1] = 0;
    }

    private void start(int index) {
        remaining[index] = 64;
        age[index] = 0;
    }

    public boolean active(boolean offHand, float lowered) {
        int index = offHand ? 1 : 0;
        if (age[index] >= 3 && lowered < 0.005f) remaining[index] = 0;
        return remaining[index] > 0;
    }
}
