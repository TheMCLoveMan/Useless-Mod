package net.themcbrothers.uselessmod.event;

import net.minecraft.world.item.DyeItem;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.themcbrothers.uselessmod.UselessMod;
import net.themcbrothers.uselessmod.world.entity.animal.UselessSheep;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = UselessMod.MOD_ID)
public class UselessForgeEvents {
    @SubscribeEvent
    static void onEntityInteract(final PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof UselessSheep && event.getItemStack().getItem() instanceof DyeItem) {
            event.setCanceled(true);
        }
    }
}
