package net.themcbrothers.uselessmod.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.themcbrothers.uselessmod.UselessTags;
import net.themcbrothers.uselessmod.init.ModBlockEntityTypes;
import net.themcbrothers.uselessmod.init.UselessFluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PaintBucketBlockEntity extends BlockEntity {
    public final FluidTank colorTank = new FluidTank(FluidType.BUCKET_VOLUME, fluidStack -> (fluidStack.getFluid().is(FluidTags.WATER) || fluidStack.getFluid().is(UselessTags.Fluids.PAINT)) && fluidStack.getFluid().isSource(fluidStack.getFluid().defaultFluidState()));
    public final ItemStackHandler stackHandler = new ItemStackHandler() {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            PaintBucketBlockEntity.this.setChanged();
        }
    };

    public PaintBucketBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.PAINT_BUCKET.get(), pos, state);
    }

    public boolean hasColor() {
        return !this.colorTank.isEmpty() && this.colorTank.getFluid().getFluid().is(UselessTags.Fluids.PAINT) && this.colorTank.getFluid().hasTag();
    }

    public int getColor() {
        CompoundTag tag = this.colorTank.getFluid().getTag();
        return tag != null ? tag.getInt("Color") : -1;
    }

    public void setColor(float[] colorValues) {
        int r = (int) (colorValues[0] * 255.0F) << 16;
        int g = (int) (colorValues[1] * 255.0F) << 8;
        int b = (int) (colorValues[2] * 255.0F);

        this.colorTank.setFluid(new FluidStack(UselessFluids.PAINT.get(), FluidType.BUCKET_VOLUME));
        this.colorTank.getFluid().getOrCreateTag().putInt("Color", r + g + b);
        this.setChanged();
    }

    private void saveColorAndItem(CompoundTag tag) {
        tag.put("Tank", this.colorTank.writeToNBT(new CompoundTag()));
        tag.put("Slots", this.stackHandler.serializeNBT());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveColorAndItem(tag);
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        this.saveColorAndItem(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.colorTank.readFromNBT(tag.getCompound("Tank"));
        this.stackHandler.deserializeNBT(tag.getCompound("Slots"));
    }

    private LazyOptional<IFluidHandler> tankHolder = LazyOptional.of(() -> this.colorTank);

    private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> this.stackHandler);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER && (side == null || side.getAxis().isVertical())) {
            return this.tankHolder.cast();
        } else if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.tankHolder.invalidate();
        this.itemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.tankHolder = LazyOptional.of(() -> this.colorTank);
        this.itemHandler = LazyOptional.of(() -> this.stackHandler);
    }
}
