package @package@;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BlockEntityClientSerializableMapper extends BlockEntityMapper {

	public BlockEntityClientSerializableMapper(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public void syncData() {
		if (level instanceof ServerLevel) {
			((ServerLevel) level).getChunkSource().blockChanged(worldPosition);
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		final CompoundTag compoundTag = new CompoundTag();
		writeCompoundTag(compoundTag);
		return new ClientboundBlockEntityDataPacket(worldPosition, 127, compoundTag);
	}

	@Override
	public final CompoundTag getUpdateTag() {
		final CompoundTag compoundTag = super.getUpdateTag();
		writeCompoundTag(compoundTag);
		return compoundTag;
	}
}
