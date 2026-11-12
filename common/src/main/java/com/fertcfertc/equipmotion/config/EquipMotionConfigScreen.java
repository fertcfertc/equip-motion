package com.fertcfertc.equipmotion.config;

import com.fertcfertc.equipmotion.EquipMotion;
import com.fertcfertc.equipmotion.motion.SwapStyle;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;

import java.net.URI;
import java.util.Locale;

public final class EquipMotionConfigScreen {
    private static final SystemToast.SystemToastId SAVE_ERROR = new SystemToast.SystemToastId();

    private EquipMotionConfigScreen() {}

    public static Screen create(Screen parent) {
        var current = EquipMotion.CONFIG.get();
        var defaults = EquipMotionSettings.defaults();
        var builder = ConfigBuilder.create().setParentScreen(parent).setTitle(text("title")).setDoesConfirmSave(true);
        var entries = builder.entryBuilder();
        var motion = builder.getOrCreateCategory(text("motion"));
        var hands = builder.getOrCreateCategory(text("hands"));
        var enabled = entries.startBooleanToggle(text("enabled"), current.enabled())
                .setDefaultValue(defaults.enabled()).setTooltip(text("enabled.tooltip")).build();
        var style = entries.startEnumSelector(text("style"), SwapStyle.class, current.style())
                .setDefaultValue(defaults.style()).setTooltip(text("style.tooltip"))
                .setEnumNameProvider(value -> text("style." + value.name().toLowerCase(Locale.ROOT))).build();
        var amount = entries.startIntSlider(text("amount"), (int) Math.round(current.amount() * 100), 0, 150)
                .setDefaultValue(100).setTextGetter(value -> Component.literal(value + "%"))
                .setTooltip(text("amount.tooltip")).build();
        var preserve = entries.startBooleanToggle(text("preserveUseAnimations"), current.preserveUseAnimations())
                .setDefaultValue(defaults.preserveUseAnimations()).setTooltip(text("preserveUseAnimations.tooltip")).build();
        motion.addEntry(enabled).addEntry(style).addEntry(amount).addEntry(preserve);
        var weighted = entries.startBooleanToggle(text("weightedMotion"),current.weightedMotion())
                .setDefaultValue(defaults.weightedMotion()).setTooltip(text("weightedMotion.tooltip")).build();
        var response = entries.startIntSlider(text("responseTicks"),current.responseTicks(),0,8)
                .setDefaultValue(defaults.responseTicks()).setTooltip(text("responseTicks.tooltip")).build();
        motion.addEntry(weighted).addEntry(response);
        motion.addEntry(entries.startTextDescription(text("sponsor").copy().withStyle(value -> value
                        .withColor(0xB9D9F0).withUnderlined(true)
                        .withClickEvent(new ClickEvent.OpenUrl(URI.create("https://ctf.mom")))))
                .setTooltip(text("sponsor.tooltip")).build());
        var main = entries.startBooleanToggle(text("mainHand"), current.mainHand())
                .setDefaultValue(defaults.mainHand()).setTooltip(text("mainHand.tooltip")).build();
        var off = entries.startBooleanToggle(text("offHand"), current.offHand())
                .setDefaultValue(defaults.offHand()).setTooltip(text("offHand.tooltip")).build();
        var empty = entries.startBooleanToggle(text("emptyHand"), current.emptyHand())
                .setDefaultValue(defaults.emptyHand()).setTooltip(text("emptyHand.tooltip")).build();
        hands.addEntry(main).addEntry(off).addEntry(empty);
        builder.setSavingRunnable(() -> {
            var next = new EquipMotionSettings(enabled.getValue(), style.getValue(), amount.getValue() / 100.0,
                    main.getValue(), off.getValue(), empty.getValue(), preserve.getValue(),weighted.getValue(),response.getValue());
            if (!EquipMotion.CONFIG.save(next)) {
                SystemToast.add(Minecraft.getInstance().gui.toastManager(), SAVE_ERROR,
                        text("saveError"), text("saveError.detail"));
            }
        });
        return builder.build();
    }

    private static Component text(String key) { return Component.translatable("equipmotion.config." + key); }
}
