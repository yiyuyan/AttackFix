package cn.ksmcbrigade.attackfix.mixin;

import cn.ksmcbrigade.attackfix.AttackFix;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static cn.ksmcbrigade.attackfix.AttackFix.getAngleToEntity;

@Mixin(Player.class)
public abstract class EntityMixin extends LivingEntity {

    protected EntityMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "attack",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/Entity;skipAttackInteraction(Lnet/minecraft/world/entity/Entity;)Z"))
    public void attack(Entity p_36347_, CallbackInfo ci){
        if(!AttackFix.enabled) return;
        Minecraft MC = Minecraft.getInstance();
        if(MC.player==null) return;
        if(MC.getConnection()==null) return;
        float[] yp = getAngleToEntity(p_36347_, MC.player);
        this.setYRot(yp[0]);
        this.setXRot(yp[1]);
        MC.getConnection().getConnection().send(new ServerboundMovePlayerPacket.Rot(yp[0],yp[1],this.onGround()));
        System.out.println(Component.translatable("options.accessibility.high_contrast.error.tooltip"));
    }
}
