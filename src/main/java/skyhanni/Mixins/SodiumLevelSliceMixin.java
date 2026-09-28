package skyhanni.Mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import skyhanni.Render;

@Mixin(targets = "net.caffeinemc.mods.sodium.client.world.LevelSlice", remap = false)
public class SodiumLevelSliceMixin {
   @Unique
   private BlockPos skyhanni$center;
   @Unique
   private int skyhanni$radius;
   @Unique
   private boolean skyhanni$enabled;

   @Inject(method = "copyData", at = @At("HEAD"), remap = false)
   private void skyhanni$captureRange(CallbackInfo ci) {
      this.skyhanni$center = Render.center;
      this.skyhanni$radius = Render.radius;
      this.skyhanni$enabled = Render.enabled;
   }

   @Inject(method = "getBlockState(III)Lnet/minecraft/world/level/block/state/BlockState;", at = @At("HEAD"), cancellable = true, remap = false)
   private void skyhanni$hideDistantBlocks(int x, int y, int z, CallbackInfoReturnable<BlockState> cir) {
      long dx = (long)x - this.skyhanni$center.getX();
      long dy = (long)y - this.skyhanni$center.getY();
      long dz = (long)z - this.skyhanni$center.getZ();
      if (this.skyhanni$enabled && (this.skyhanni$radius == 0 || dx * dx + dy * dy + dz * dz > this.skyhanni$radius * this.skyhanni$radius)) {
         cir.setReturnValue(Blocks.AIR.defaultBlockState());
      }
   }
}
