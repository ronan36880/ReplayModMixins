package com.moonsworth.lunar.replaymod.v1_17.mixin;

import com.moonsworth.lunar.bridge.BridgeManager;
import com.moonsworth.lunar.client.util.BrowserUtil;
import com.replaymod.core.versions.MCVer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.net.URI;

@Mixin(MCVer.class)
public class MCVerMixin_v1_17 {

    /**
     * Don't use Accessor.
     *
     * @author phase
     */
    @Overwrite
    public static void addButton(Screen screen, Button button) {
        screen.addRenderableWidget(button);
    }

    /**
     * Use bridge check.
     *
     * @author Tre
     */
    @Overwrite
    public static boolean hasOptifine() {
        return BridgeManager.getOptiFine().isPresent();
    }

    /**
     * Use our own url open'er
     *
     * @author Tre
     */
    @Overwrite
    public static void openURL(URI url) {
        BrowserUtil.openURL(url);
    }
}
