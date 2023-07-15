package com.moonsworth.lunar.replaymod.v1_18.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import com.moonsworth.lunar.bridge.BridgeManager;
import com.moonsworth.lunar.bridge.client.settings.KeyBindingBridge;
import com.moonsworth.lunar.client.feature.mod.replaymod.ReplayMod;
import com.moonsworth.lunar.client.particles.mclib.math.functions.classic.Mod;
import com.moonsworth.lunar.client.util.Ref;
import com.replaymod.core.KeyBindingRegistry;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Set;

@Mixin(KeyBindingRegistry.class)
public abstract class KeyBindingRegisterMixin_v1_18 {

    @Shadow public Map<String, KeyBindingRegistry.Binding> bindings;

    @Shadow public Set<KeyMapping> onlyInReplay;

//    @Redirect(
//            method = "registerKeyBinding(Ljava/lang/String;IZ)Lcom/replaymod/core/KeyBindingRegistry$Binding;",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/fabricmc/fabric/api/client/keybinding/FabricKeyBinding$Builder;build()Lnet/fabricmc/fabric/api/client/keybinding/FabricKeyBinding;"
//            )
//    )
//    public FabricKeyBinding ichor$registerKeyBinding(FabricKeyBinding.Builder instance) {
//        return null;
//    }
//
//    @Redirect(
//            method = "registerKeyBinding(Ljava/lang/String;IZ)Lcom/replaymod/core/KeyBindingRegistry$Binding;",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"
//            )
//    )
//    public Object ichor$redirectkeymap(Map instance, Object k, Object v) {
//        KeyBindingRegistry.Binding value = (KeyBindingRegistry.Binding) v;
//        KeyBindingBridge key = BridgeManager.getImplementation().initKeyBinding(value.keyBinding.name, value.keyBinding.key.value, value.keyBinding.category);
//        value.keyBinding = (KeyMapping) key;
//        return instance.put(k, value);
//    }
//
//    @Redirect(
//            method = "registerKeyBinding(Ljava/lang/String;IZ)Lcom/replaymod/core/KeyBindingRegistry$Binding;",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/fabricmc/fabric/api/client/keybinding/KeyBindingRegistry;register(Lnet/fabricmc/fabric/api/client/keybinding/FabricKeyBinding;)Z"
//            )
//    )
//    public boolean ichor$registerKeyBinding$register(net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry instance, FabricKeyBinding keyBinding) {
//        return false;
//    }
}
