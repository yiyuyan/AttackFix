package cn.ksmcbrigade.attackfix.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommonPacketListenerImpl.class)
public class ServerCommonPacketListenerImplMixin {
    @Inject(method = "disconnect",at = @At("HEAD"), cancellable = true)
    public void disconnect(Component p_299122_, CallbackInfo ci){
        if(p_299122_.getString().equalsIgnoreCase(Component.translatable("multiplayer.disconnect.invalid_entity_attacked").getString())){
            ci.cancel();
        }
    }
}
