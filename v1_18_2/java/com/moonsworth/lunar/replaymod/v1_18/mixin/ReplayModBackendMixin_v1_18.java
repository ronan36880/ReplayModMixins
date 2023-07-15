package com.moonsworth.lunar.replaymod.v1_18.mixin;

import com.moonsworth.lunar.client.external.ExternalLinks;
import com.moonsworth.lunar.replaymod.v1_18.link.ReplayModGuiLink_Impl;
import com.replaymod.core.ReplayModBackend;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReplayModBackend.class)
public class ReplayModBackendMixin_v1_18 {


    @Inject(method = "onInitializeClient", at = @At("HEAD"))
    private void onInitializeClient(CallbackInfo ci) {
        ExternalLinks.registerLink(new ReplayModGuiLink_Impl());
        System.out.println("ReplayModBackendMixin_v1_18.onInitializeClient: initializing ReplayMod");
    }

}
