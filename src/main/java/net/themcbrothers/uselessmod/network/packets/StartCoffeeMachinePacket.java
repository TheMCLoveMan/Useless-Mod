package net.themcbrothers.uselessmod.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.themcbrothers.lib.network.PacketMessage;
import net.themcbrothers.uselessmod.world.inventory.CoffeeMachineMenu;
import net.themcbrothers.uselessmod.world.level.block.entity.CoffeeMachineBlockEntity;

import java.util.function.Supplier;

public class StartCoffeeMachinePacket implements PacketMessage {
    private final boolean start;

    public StartCoffeeMachinePacket(boolean start) {
        this.start = start;
    }

    public StartCoffeeMachinePacket(FriendlyByteBuf buffer) {
        this.start = buffer.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.start);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> context) {
        ServerPlayer serverPlayerEntity = context.get().getSender();
        context.get().enqueueWork(() -> {
            if (serverPlayerEntity != null) {
                if (serverPlayerEntity.containerMenu instanceof CoffeeMachineMenu) {
                    CoffeeMachineBlockEntity coffeeMachine = ((CoffeeMachineMenu) serverPlayerEntity.containerMenu).blockEntity;
                    coffeeMachine.startMachine(this.start);
                }
            }
        });
    }
}
