package skyhanni.Chat;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public final class Output {
   public static int toggle(FabricClientCommandSource source, String feature, boolean enabled) {
      source.sendFeedback(Component.literal("Skyhanni >> " + feature + " ")
         .append(Component.literal(enabled ? "ENABLED" : "DISABLED")
            .withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED, ChatFormatting.BOLD)));
      return 1;
   }

   public static int radius(FabricClientCommandSource source, int radius) {
      source.sendFeedback(Component.literal("Skyhanni >> ")
         .append(Component.literal(Integer.toString(radius)).withStyle(ChatFormatting.BOLD))
         .append(Component.literal(" blocks radius")));
      return 1;
   }
}
