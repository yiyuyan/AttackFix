package cn.ksmcbrigade.attackfix;

import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AttackFix.MODID)
@Mod.EventBusSubscriber(value = Dist.CLIENT,modid = AttackFix.MODID)
public class AttackFix {
    public static final String MODID = "af";

    public static boolean enabled = true;

    public AttackFix() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void registerCommand(RegisterClientCommandsEvent event){
        event.getDispatcher().register(Commands.literal("afe").executes(context -> {
            enabled = !enabled;
            context.getSource().sendSystemMessage(Component.nullToEmpty("Enabled: "+enabled));
            return 0;
        }));
    }

    public static float[] getAngleToEntity(Entity targetEntity, Player player) {
        double dX = targetEntity.getX() - player.getX();
        double dY = targetEntity.getY() - player.getY();
        double dZ = targetEntity.getZ() - player.getZ();
        double distanceXZ = Math.sqrt(dX * dX + dZ * dZ);
        float yaw = (float) Math.toDegrees(Math.atan2(dZ, dX)) - 90.0F;
        float pitch = (float) -Math.toDegrees(Math.atan2(dY, distanceXZ));
        return new float[] {yaw, pitch};
    }
}

