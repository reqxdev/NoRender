package skyhanni.Chat;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import skyhanni.Config;
import skyhanni.Render;

public final class Commands {
   public static void register() {
      ClientCommandRegistrationCallback.EVENT.register((dispatcher, registry) -> {
         var command = ClientCommands.literal("skyhanni")
            .executes(context -> 1);

         command.then(ClientCommands.literal("render")
            .executes(Commands::toggleRender)
            .then(ClientCommands.argument("radius", IntegerArgumentType.integer(0, 32))
               .executes(Commands::setRenderRadius)));

         dispatcher.register(command);
      });
   }

   private static int toggleRender(CommandContext<FabricClientCommandSource> context) {
      boolean enabled = Render.toggle(Minecraft.getInstance());
      Config.save();
      return Output.toggle(context.getSource(), "Render", enabled);
   }

   private static int setRenderRadius(CommandContext<FabricClientCommandSource> context) {
      int radius = IntegerArgumentType.getInteger(context, "radius");
      Render.setRadius(Minecraft.getInstance(), radius);
      Config.save();
      return Output.radius(context.getSource(), radius);
   }
}
