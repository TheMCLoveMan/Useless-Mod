package net.themcbrothers.uselessmod.data;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.themcbrothers.uselessmod.UselessMod;
import net.themcbrothers.uselessmod.init.ModBlocks;
import net.themcbrothers.uselessmod.init.ModItems;

public class UselessItemModelProvider extends ItemModelProvider {
    public UselessItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, UselessMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.RAW_USELESS.get());
        basicItem(ModItems.RAW_SUPER_USELESS.get());
        basicItem(ModItems.USELESS_DUST.get());
        basicItem(ModItems.SUPER_USELESS_DUST.get());
        basicItem(ModItems.USELESS_INGOT.get());
        basicItem(ModItems.SUPER_USELESS_INGOT.get());
        basicItem(ModItems.USELESS_NUGGET.get());
        basicItem(ModItems.SUPER_USELESS_NUGGET.get());
        basicItem(ModBlocks.USELESS_OAK_DOOR.asItem());
        basicItem(ModItems.USELESS_SHEARS.get());
        basicTool(ModItems.USELESS_SWORD.get());
        basicTool(ModItems.SUPER_USELESS_SWORD.get());
        basicTool(ModItems.USELESS_SHOVEL.get());
        basicTool(ModItems.SUPER_USELESS_SHOVEL.get());
        basicTool(ModItems.USELESS_PICKAXE.get());
        basicTool(ModItems.SUPER_USELESS_PICKAXE.get());
        basicTool(ModItems.USELESS_AXE.get());
        basicTool(ModItems.SUPER_USELESS_AXE.get());
        basicTool(ModItems.USELESS_HOE.get());
        basicTool(ModItems.SUPER_USELESS_HOE.get());
        basicItem(ModItems.USELESS_HELMET.get());
        basicItem(ModItems.SUPER_USELESS_HELMET.get());
        basicItem(ModItems.USELESS_CHESTPLATE.get());
        basicItem(ModItems.SUPER_USELESS_CHESTPLATE.get());
        basicItem(ModItems.USELESS_LEGGINGS.get());
        basicItem(ModItems.SUPER_USELESS_LEGGINGS.get());
        basicItem(ModItems.USELESS_BOOTS.get());
        basicItem(ModItems.SUPER_USELESS_BOOTS.get());
        basicItem(ModItems.USELESS_WHEAT_SEEDS.get());
        basicItem(ModItems.USELESS_WHEAT.get());
        basicItem(ModItems.COFFEE_SEEDS.get());
        basicItem(ModItems.COFFEE_BEANS.get());
        basicItem(ModItems.USELESS_BONE.get());
        basicItem(ModItems.USELESS_LEATHER.get());
        basicItem(ModItems.USELESS_FEATHER.get());

        final ModelFile brokenUselessElytra = basicItem(UselessMod.rl("broken_useless_elytra"));
        basicItem(ModItems.USELESS_ELYTRA.get()).override().predicate(mcLoc("broken"), 1).model(brokenUselessElytra).end();
        final ModelFile brokenSuperUselessElytra = basicItem(UselessMod.rl("broken_super_useless_elytra"));
        basicItem(ModItems.SUPER_USELESS_ELYTRA.get()).override().predicate(mcLoc("broken"), 1).model(brokenSuperUselessElytra).end();

        withExistingParent("paint_brush", mcLoc(ITEM_FOLDER + "/handheld"))
                .texture("layer0", modLoc(ITEM_FOLDER + "/paint_brush_0"))
                .texture("layer1", modLoc(ITEM_FOLDER + "/paint_brush_1"));

        getBuilder(String.valueOf(ModItems.USELESS_SKELETON_SKULL.getId())).parent(getExistingFile(mcLoc(ITEM_FOLDER + "/template_skull")));

        this.withExistingParent("useless_sheep_spawn_egg", mcLoc(ITEM_FOLDER + "/template_spawn_egg"));
        this.withExistingParent("useless_pig_spawn_egg", mcLoc(ITEM_FOLDER + "/template_spawn_egg"));
        this.withExistingParent("useless_chicken_spawn_egg", mcLoc(ITEM_FOLDER + "/template_spawn_egg"));
        this.withExistingParent("useless_cow_spawn_egg", mcLoc(ITEM_FOLDER + "/template_spawn_egg"));
        this.withExistingParent("useless_skeleton_spawn_egg", mcLoc(ITEM_FOLDER + "/template_spawn_egg"));

        // Items with special renderer
        final ModelFile builtInEntityModel = new ModelFile.UncheckedModelFile("builtin/entity");

        final ModelFile uselessShieldBlocking = shieldBlockingModel("useless_shield_blocking", builtInEntityModel, "useless_block");
        shieldModel("useless_shield", builtInEntityModel, "useless_block", uselessShieldBlocking);

        final ModelFile superUselessShieldBlocking = shieldBlockingModel("super_useless_shield_blocking", builtInEntityModel, "super_useless_block");
        shieldModel("super_useless_shield", builtInEntityModel, "super_useless_block", superUselessShieldBlocking);
    }

    private void basicTool(ItemLike item) {
        final ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.asItem());
        this.singleTexture(id.getPath(), mcLoc(ITEM_FOLDER + "/handheld"), "layer0", modLoc(ITEM_FOLDER + "/" + id.getPath()));
    }

    private void shieldModel(String path, ModelFile parent, String particleTexture, ModelFile blockingModel) {
        getBuilder(path)
                .parent(parent).guiLight(BlockModel.GuiLight.FRONT)
                .texture("particle", modLoc(BLOCK_FOLDER + "/" + particleTexture))
                .transforms()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND)
                .rotation(0, 90, 0).translation(10, 6, -4).scale(1, 1, 1).end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND)
                .rotation(0, 90, 0).translation(10, 6, 12).scale(1, 1, 1).end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 180, 5).translation(-10, 2, -10).scale(1.25F, 1.25F, 1.25F).end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 180, 5).translation(10, 0, -10).scale(1.25F, 1.25F, 1.25F).end()
                .transform(ItemTransforms.TransformType.GUI)
                .rotation(15, -25, -5).translation(2, 3, 0).scale(0.65F, 0.65F, 0.65F).end()
                .transform(ItemTransforms.TransformType.FIXED)
                .rotation(0, 180, 0).translation(-2, 4, -5).scale(0.5F, 0.5F, 0.5F).end()
                .transform(ItemTransforms.TransformType.GROUND)
                .rotation(0, 0, 0).translation(4, 4, 2).scale(0.25F, 0.25F, 0.25F).end()
                .end()
                .override().predicate(mcLoc("blocking"), 1).model(blockingModel).end();
    }

    private ModelFile shieldBlockingModel(String path, ModelFile parent, String particleTexture) {
        return getBuilder(path)
                .parent(parent).guiLight(BlockModel.GuiLight.FRONT)
                .texture("particle", modLoc(BLOCK_FOLDER + "/" + particleTexture))
                .transforms()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND)
                .rotation(45, 135, 0).translation(3.51F, 11, -2).scale(1, 1, 1).end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND)
                .rotation(45, 135, 0).translation(13.51F, 3, 5).scale(1, 1, 1).end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 180, -5).translation(-15, 5, -11).scale(1.25F, 1.25F, 1.25F).end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 180, -5).translation(5, 5, -11).scale(1.25F, 1.25F, 1.25F).end()
                .transform(ItemTransforms.TransformType.GUI)
                .rotation(15, -25, -5).translation(2, 3, 0).scale(0.65F, 0.65F, 0.65F).end()
                .end();
    }
}
