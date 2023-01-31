package net.themcbrothers.uselessmod.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.themcbrothers.lib.wrench.WrenchableBlock;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class PaintBucketBlock extends Block implements SimpleWaterloggedBlock, WrenchableBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final VoxelShape SHAPE;

    public PaintBucketBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.TRUE));
        SHAPE = makeShape();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState().setValue(WATERLOGGED, fluidState.is(Fluids.WATER));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState newState, LevelAccessor world, BlockPos pos, BlockPos newPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, facing, newState, world, pos, newPos);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (this.tryWrench(state, level, pos, player, hand, hit)) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext collision) {
        return SHAPE;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType pathComputationType) {
        return false;
    }

    private VoxelShape makeShape() {
        return Stream.of(
                Block.box(10, 0, 6, 11, 1, 10),
                Block.box(5, 0, 6, 6, 1, 10),
                Block.box(6, 0, 4, 10, 8, 5),
                Block.box(6, 0, 11, 10, 8, 12),
                Block.box(4, 0, 6, 5, 8, 10),
                Block.box(11, 0, 6, 12, 8, 10),
                Block.box(5, 0, 5, 6, 8, 6),
                Block.box(5, 0, 10, 6, 8, 11),
                Block.box(10, 0, 10, 11, 8, 11),
                Block.box(10, 0, 5, 11, 8, 6),
                Block.box(6, 0, 5, 10, 1, 11)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).orElseThrow();
    }
}
