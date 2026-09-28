package skyhanni;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;

public final class Render {
   public static volatile boolean enabled;
   public static volatile BlockPos center = BlockPos.ZERO;
   public static volatile int radius = 12;
   private static boolean refreshOnNextTick;

   public static void restore(boolean savedEnabled, int savedRadius) {
      enabled = savedEnabled;
      radius = savedRadius;
      refreshOnNextTick = savedEnabled;
   }

   public static boolean toggle(Minecraft client) {
      enabled = !enabled;
      refreshOnNextTick = false;
      center = client.player.blockPosition();
      client.levelExtractor.allChanged();
      return enabled;
   }

   public static void setRadius(Minecraft client, int next) {
      radius = next;
      if (enabled) {
         client.levelExtractor.allChanged();
      }
   }

   public static void tick(Minecraft client) {
      if (!enabled || client.player == null) {
         return;
      }
      if (refreshOnNextTick) {
         center = client.player.blockPosition();
         client.levelExtractor.allChanged();
         refreshOnNextTick = false;
         return;
      }
      BlockPos next = client.player.blockPosition();
      if (next.equals(center)) {
         return;
      }
      BlockPos previous = center;
      center = next;
      rebuild(client, previous);
      rebuild(client, next);
   }

   private static void rebuild(Minecraft client, BlockPos pos) {
      client.levelExtractor.setSectionRangeDirty(
         SectionPos.blockToSectionCoord(pos.getX() - radius - 1),
         SectionPos.blockToSectionCoord(pos.getY() - radius - 1),
         SectionPos.blockToSectionCoord(pos.getZ() - radius - 1),
         SectionPos.blockToSectionCoord(pos.getX() + radius + 1),
         SectionPos.blockToSectionCoord(pos.getY() + radius + 1),
         SectionPos.blockToSectionCoord(pos.getZ() + radius + 1));
   }
}
