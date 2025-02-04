package net.themcbrothers.uselessmod.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.uselessmod.world.level.block.entity.*;

import static net.themcbrothers.uselessmod.init.Registration.BLOCK_ENTITIES;

public final class ModBlockEntityTypes {
    static void register() {
    }

    public static final RegistryObject<BlockEntityType<MachineSupplierBlockEntity>> MACHINE_SUPPLIER =
            BLOCK_ENTITIES.register("machine_supplier", MachineSupplierBlockEntity::new, ModBlocks.MACHINE_SUPPLIER);
    public static final RegistryObject<BlockEntityType<CoffeeMachineBlockEntity>> COFFEE_MACHINE =
            BLOCK_ENTITIES.register("coffee_machine", CoffeeMachineBlockEntity::new, ModBlocks.COFFEE_MACHINE);
    public static final RegistryObject<BlockEntityType<CupBlockEntity>> CUP =
            BLOCK_ENTITIES.register("cup", CupBlockEntity::new, ModBlocks.CUP_COFFEE);
    public static final RegistryObject<BlockEntityType<PaintedWoolBlockEntity>> PAINTED_WOOL =
            BLOCK_ENTITIES.register("painted_wool", PaintedWoolBlockEntity::new, ModBlocks.PAINTED_WOOL);
    public static final RegistryObject<BlockEntityType<PaintBucketBlockEntity>> PAINT_BUCKET =
            BLOCK_ENTITIES.register("paint_bucket", PaintBucketBlockEntity::new, ModBlocks.PAINT_BUCKET);
    public static final RegistryObject<BlockEntityType<UselessBedBlockEntity>> BED =
            BLOCK_ENTITIES.register("bed", UselessBedBlockEntity::new, ModBlocks.USELESS_BED);
    public static final RegistryObject<BlockEntityType<WallClosetBlockEntity>> WALL_CLOSET =
            BLOCK_ENTITIES.register("wall_closet", WallClosetBlockEntity::new, ModBlocks.WALL_CLOSET);
    public static final RegistryObject<BlockEntityType<UselessSkullBlockEntity>> SKULL =
            BLOCK_ENTITIES.register("skull", UselessSkullBlockEntity::new,
                    blocks -> blocks.add(ModBlocks.USELESS_SKELETON_SKULL.get(), ModBlocks.USELESS_SKELETON_WALL_SKULL.get()));
    public static final RegistryObject<BlockEntityType<UselessSignBlockEntity>> SIGN =
            BLOCK_ENTITIES.register("sign", UselessSignBlockEntity::new,
                    blocks -> blocks.add(ModBlocks.USELESS_OAK_WALL_SIGN.get(), ModBlocks.USELESS_OAK_SIGN.get()));
    public static final RegistryObject<BlockEntityType<LightSwitchBlockEntity>> LIGHT_SWITCH =
            BLOCK_ENTITIES.register("light_switch", LightSwitchBlockEntity::new,
                    blocks -> blocks.add(ModBlocks.LIGHT_SWITCH.get(), ModBlocks.LIGHT_SWITCH_BLOCK.get()));
}
