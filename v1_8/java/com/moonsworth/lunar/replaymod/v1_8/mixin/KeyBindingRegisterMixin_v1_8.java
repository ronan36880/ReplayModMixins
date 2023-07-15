package com.moonsworth.lunar.replaymod.v1_8.mixin;

import com.moonsworth.lunar.client.feature.mod.AbstractMod;
import com.moonsworth.lunar.client.options.KeyBind;
import com.moonsworth.lunar.client.util.Ref;
import com.replaymod.core.KeyBindingRegistry;
import com.replaymod.core.ReplayMod;
import com.replaymod.core.events.KeyBindingEventCallback;
import com.replaymod.replay.ReplayModReplay;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.IntHashMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(KeyBinding.class)
public abstract class KeyBindingRegisterMixin_v1_8 {

    @Shadow
    @Final
    public static List<KeyBinding> keybindArray;
    @Unique
    private static final List<KeyBinding> temporarilyRemoved = new ArrayList<>();

    @Inject(method = "onTick", at = @At("HEAD"), cancellable = true)
    private static void beforeKeyBindingTick(CallbackInfo ci) {
        KeyBindingEventCallback.EVENT.invoker().onKeybindingEvent();
    }

    @Inject(method = "resetKeyBindingArrayAndHash", at = @At("HEAD"))
    private static void preContextualKeyBindings(CallbackInfo ci) {
        Set<KeyBinding> onlyInReplay = ReplayMod.instance.getKeyBindingRegistry().getOnlyInReplay();
        if (ReplayModReplay.instance.getReplayHandler() != null) {
            // In replay, remove any conflicting key bindings, so that ours are guaranteed in
            keybindArray.removeIf(keyBinding -> {
                for (KeyBinding exclusiveBinding : onlyInReplay) {
                    if (keyBinding.keyCode == exclusiveBinding.keyCode && keyBinding != exclusiveBinding) {
                        temporarilyRemoved.add(keyBinding);
                        return true;
                    }
                }
                return false;
            });
        } else {
            // Not in a replay, remove all replay-exclusive keybindings
            for (KeyBinding keyBinding : onlyInReplay) {
                if (keybindArray.remove(keyBinding)) {
                    temporarilyRemoved.add(keyBinding);
                }
            }
        }
    }

    @Inject(method = "resetKeyBindingArrayAndHash", at = @At("RETURN"))
    private static void postContextualKeyBindings(CallbackInfo ci) {
        keybindArray.addAll(temporarilyRemoved);
        temporarilyRemoved.clear();
    }
}
