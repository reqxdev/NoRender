package skyhanni;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import skyhanni.Chat.Commands;

public final class Skyhanni implements ClientModInitializer {
   @Override
   public void onInitializeClient() {
      Config.load();
      Commands.register();
      ClientTickEvents.END_CLIENT_TICK.register(Render::tick);
   }
}
