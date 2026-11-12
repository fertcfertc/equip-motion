package com.fertcfertc.equipmotion.motion;

import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

public final class ItemWeight {
    private ItemWeight() {}

    public static double of(ItemStack stack) {
        if(stack.isEmpty())return .6;
        if(stack.has(DataComponents.BLOCKS_ATTACKS))return 1.35;
        if(stack.is(ItemTags.AXES)||stack.is(ItemTags.PICKAXES))return 1.25;
        if(stack.is(ItemTags.SHOVELS)||stack.is(ItemTags.HOES)||stack.is(ItemTags.SPEARS)||stack.is(Items.TRIDENT))return 1.15;
        if(stack.has(DataComponents.CONSUMABLE)||stack.is(Items.POTION)||stack.is(Items.SPLASH_POTION)
                ||stack.is(Items.LINGERING_POTION)||stack.is(Items.GLASS_BOTTLE)
                ||stack.is(Items.TORCH)||stack.is(Items.SOUL_TORCH)
                ||stack.is(Items.REDSTONE_TORCH))return .7;
        if(stack.getItem() instanceof BlockItem)return 1.15;
        return 1;
    }
}
