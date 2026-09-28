package skyhanni;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public final class Config {
   private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("skyhanni.json");
   private static final Gson GSON = new Gson();

   private record Settings(boolean enabled, int radius) {
   }

   public static void load() {
      if (!Files.exists(FILE)) {
         return;
      }

      try (var reader = Files.newBufferedReader(FILE)) {
         Settings settings = GSON.fromJson(reader, Settings.class);
         if (settings != null) {
            int radius = settings.radius();
            Render.restore(settings.enabled(), radius >= 0 && radius <= 32 ? radius : 12);
         }
      } catch (IOException | JsonParseException e) {
         System.err.println("Skyhanni: Could not load config: " + e);
      }
   }

   public static void save() {
      try {
         Files.createDirectories(FILE.getParent());
         try (var writer = Files.newBufferedWriter(FILE)) {
            GSON.toJson(new Settings(Render.enabled, Render.radius), writer);
         }
      } catch (IOException e) {
         System.err.println("Skyhanni: Could not save config: " + e);
      }
   }
}
