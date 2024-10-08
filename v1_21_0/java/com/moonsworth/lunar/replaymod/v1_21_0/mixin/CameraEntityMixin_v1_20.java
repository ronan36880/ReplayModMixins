package com.moonsworth.lunar.replaymod.v1_21_0.mixin;

import com.replaymod.core.utils.Utils;
import com.replaymod.replay.camera.CameraEntity;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CameraEntity.class)
public class CameraEntityMixin_v1_20 {

    private long lastUpdate;

    @Shadow public float roll;

    /**
     * @reason Fix camera roll being framerate dependent
     */
    @Redirect(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/KeyMapping;isDown()Z"
            ),
            expect = 2,
            require = 2
    )
    public boolean ichor$update(KeyMapping instance) {
        long now = System.currentTimeMillis();
        if (instance.isDown()) {
            long timePassed = now - this.lastUpdate;
            if (instance.getName().contains("rollclockwise")) {
                roll += (Utils.isCtrlDown() ? 0.2 : 1) * timePassed * 0.05f;
            } else {
                roll -= (Utils.isCtrlDown() ? 0.2 : 1) * timePassed * 0.05f;
            }
        }
        return false;
    }

    /**
     * @reason Register last update time after camera update
     */
    @Inject(
            method = "update",
            at = @At("TAIL")
    )
    public void ichor$update$lastUpdate(CallbackInfo ci) {
        this.lastUpdate = System.currentTimeMillis();
    }

}
