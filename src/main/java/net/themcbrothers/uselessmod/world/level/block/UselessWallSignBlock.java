package net.themcbrothers.uselessmod.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.themcbrothers.uselessmod.init.ModBlockEntityTypes;

public class UselessWallSignBlock extends WallSignBlock {
    public UselessWallSignBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntityTypes.SIGN.get().create(pos, state);
    }
}
