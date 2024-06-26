package com.moonsworth.lunar.replaymod.forge.v1_12.mixin;

import com.google.protobuf.Any;
import com.moonsworth.lunar.client.Lunar;
import com.replaymod.replay.FullReplaySender;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.nio.ByteBuffer;

@Mixin(FullReplaySender.class)
public abstract class FullReplaySenderMixin_v1_12 {
    @Inject(
            method = "deserializePacket",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/EnumConnectionState;getPacket(" +
                            "Lnet/minecraft/network/EnumPacketDirection;I)Lnet/minecraft/network/Packet;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void ichor$deserializePacket(byte[] bytes, CallbackInfoReturnable<Packet> cir,
                                         ByteBuf  bb, PacketBuffer pb, int id) {
        if (id == -2) { // Lunar protobuf packet id
            try {
                ByteBuffer buffer = ByteBuffer.allocate(pb.readableBytes());
                pb.getBytes(pb.readerIndex(), buffer);
                buffer.flip();
                Any packet = Any.parseFrom(buffer);
                Lunar.getClient().getAssetsClient().processAny(packet);
                cir.setReturnValue(null);
            } catch (Exception ignored) {}
        }
    }
}
