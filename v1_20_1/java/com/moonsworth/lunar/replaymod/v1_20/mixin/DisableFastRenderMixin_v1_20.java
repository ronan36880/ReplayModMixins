package com.moonsworth.lunar.replaymod.v1_20.mixin;

import com.moonsworth.lunar.bridge.BridgeManager;
import com.replaymod.compat.optifine.DisableFastRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DisableFastRender.class)
public class DisableFastRenderMixin_v1_20 {
    @Shadow
    public boolean wasFastRender;

    /**
     * @reason replace reflection with bridge
     * @author Gecko
     */
    @Overwrite
    public void onRenderBegin() {
        BridgeManager.getOptiFine().ifPresent(optifine -> {
            wasFastRender = optifine.getConfig().hasFastRender();
            optifine.getConfig().setFastRender(false);
        });
    }

    /**
     * @reason replace reflection with bridge
     * @author Gecko
     */
    @Overwrite
    public void onRenderEnd() {
        BridgeManager.getOptiFine().ifPresent(optifine -> {
            optifine.getConfig().setFastRender(wasFastRender);
        });
    }
}
