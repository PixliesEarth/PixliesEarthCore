package eu.pixliesearth.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

import static spark.Spark.*;

public class REST {

    private final Gson gson;

    public REST() {
        gson = new GsonBuilder().setPrettyPrinting().create();

        get("/profile/:uuid", (request, response) -> {
            UUID uuid = UUID.fromString(request.params("uuid"));
            response.type("application/json");
            return gson.toJsonTree(Main.getInstance().getProfile(uuid));
        });

        get("/nation/:id", (request, response) -> {
            Nation nation = Nation.getById(request.params("id"));
            response.type("application/json");
            return gson.toJsonTree(nation);
        });
        
        post("/nation", (request, response) -> {
            Nation nation = gson.fromJson(request.body(), Nation.class);
            nation.save();
            return "OK";
        });

        post("/profile", (request, response) -> {
            Profile profile = gson.fromJson(request.body(), Profile.class);
            profile.save();
            return "OK";
        });

        get("/discordProfile/:id", (request, response) -> {
            String uniqueId = Profile.getUniqueIdByDiscord(request.params("id"));
            if (uniqueId == null) {
                response.status(401);
                return new Gson().toJson("{}");
            }
            Profile profile = Main.getInstance().getProfile(UUID.fromString(uniqueId));
            response.status(200);
            return new Gson().toJson(profile);
        });

    }

}
