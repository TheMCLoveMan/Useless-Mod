package net.themcbrothers.uselessmod.setup;

import net.minecraft.resources.ResourceKey;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.uselessmod.api.LampRegistry;
import net.themcbrothers.uselessmod.compat.VanillaCompatibility;
import net.themcbrothers.uselessmod.config.ClientConfig;
import net.themcbrothers.uselessmod.config.ServerConfig;
import net.themcbrothers.uselessmod.init.ModBiomes;
import net.themcbrothers.uselessmod.init.ModBlocks;
import net.themcbrothers.uselessmod.init.ModEntityTypes;
import net.themcbrothers.uselessmod.init.Registration;
import net.themcbrothers.uselessmod.network.Messages;
import net.themcbrothers.uselessmod.util.RecipeHelper;
import net.themcbrothers.uselessmod.util.WallClosetRecipeManager;
import org.jetbrains.annotations.Nullable;

public class CommonSetup {
    public CommonSetup() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        Registration.register(bus);
        bus.addListener(this::setup);
        bus.addListener(this::entityAttributes);

        MinecraftForge.EVENT_BUS.register(new RecipeHelper());
        MinecraftForge.EVENT_BUS.register(new WallClosetRecipeManager());

        Messages.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.RED_ROSE.getId(), ModBlocks.POTTED_RED_ROSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.BLUE_ROSE.getId(), ModBlocks.POTTED_BLUE_ROSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.USELESS_ROSE.getId(), ModBlocks.POTTED_USELESS_ROSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.USELESS_OAK_SAPLING.getId(), ModBlocks.POTTED_USELESS_OAK_SAPLING);
        });

        event.enqueueWork(() -> {
            LampRegistry.registerLampState(Blocks.REDSTONE_LAMP);
            LampRegistry.registerLampState(ModBlocks.WHITE_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.ORANGE_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.MAGENTA_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.LIGHT_BLUE_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.YELLOW_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.LIME_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.PINK_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.GRAY_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.LIGHT_GRAY_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.CYAN_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.PURPLE_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.BLUE_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.BROWN_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.GREEN_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.RED_LAMP.get());
            LampRegistry.registerLampState(ModBlocks.BLACK_LAMP.get());
            LampRegistry.registerLampState(Blocks.LANTERN, state -> state,
                    state -> ModBlocks.LANTERN.get().defaultBlockState()
                            .setValue(BlockStateProperties.HANGING, state.getValue(BlockStateProperties.HANGING))
                            .setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED)));
            LampRegistry.registerLampState(ModBlocks.LANTERN.get(),
                    state -> Blocks.LANTERN.defaultBlockState()
                            .setValue(BlockStateProperties.HANGING, state.getValue(BlockStateProperties.HANGING))
                            .setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED)), state -> state);
        });

        // Biome Manager
        event.enqueueWork(() -> {
            ResourceKey<Biome> key = ModBiomes.USELESS_FOREST.getKey();
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(key, 10));
        });

        // make sure the stats appear in the menu
        event.enqueueWork(() -> Registration.CUSTOM_STATS.getEntries().stream().map(RegistryObject::get).forEach(Stats.CUSTOM::get));

        // Vanilla Compatibility: Flammable, Strippable, Compostable, Cauldron
        event.enqueueWork(VanillaCompatibility::register);
    }

    private void entityAttributes(final EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.USELESS_SHEEP.get(), Sheep.createAttributes().build());
        event.put(ModEntityTypes.USELESS_PIG.get(), Pig.createAttributes().build());
        event.put(ModEntityTypes.USELESS_CHICKEN.get(), Chicken.createAttributes().build());
        event.put(ModEntityTypes.USELESS_COW.get(), Cow.createAttributes().build());
        event.put(ModEntityTypes.USELESS_SKELETON.get(), AbstractSkeleton.createAttributes().build());
    }

    public @Nullable Player getLocalPlayer() {
        return null;
    }
}
