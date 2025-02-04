package net.themcbrothers.uselessmod.init;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.RegistryObject;

import static net.themcbrothers.uselessmod.init.Registration.PAINTING_VARIANTS;

public final class UselessPaintingVariants {
    static void register() {
    }

    public static final RegistryObject<PaintingVariant> LARGE_LOGO_RED = PAINTING_VARIANTS.register("large_logo_red", () -> new PaintingVariant(128, 32));
    public static final RegistryObject<PaintingVariant> LARGE_LOGO_BLUE = PAINTING_VARIANTS.register("large_logo_blue", () -> new PaintingVariant(128, 32));
    public static final RegistryObject<PaintingVariant> SMALL_LOGO_RED = PAINTING_VARIANTS.register("small_logo_red", () -> new PaintingVariant(96, 16));
    public static final RegistryObject<PaintingVariant> SMALL_LOGO_BLUE = PAINTING_VARIANTS.register("small_logo_blue", () -> new PaintingVariant(96, 16));
}
