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

        get("/block/:x/:y/:z/:world", (request, response) -> {
           World world = Bukkit.getWorld("world");
           String s = request.params("world");
           if (s.equalsIgnoreCase("nether"))
               world = Bukkit.getWorld("world_nether");
           else if (s.equalsIgnoreCase("end"))
               world = Bukkit.getWorld("world_the_end");
           else if (Bukkit.getWorld(s) != null)
               world = Bukkit.getWorld(s);
           Location loc = new Location(world, Integer.parseInt(request.params("x")), Integer.parseInt(request.params("y")), Integer.parseInt(request.params("z")));
           CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
           JsonObject json = new JsonObject();
           json.addProperty("UUID", CustomItemUtil.getUUIDFromLocation(loc));
           json.addProperty("Energy", h.getPowerAtLocation(loc) != null ? Methods.convertEnergyDouble(h.getPowerAtLocation(loc)) : "No energy!");
           json.addProperty("Temperature",  h.getTempratureAtLocation(loc) != null ? Methods.round(h.getTempratureAtLocation(loc), 2) + "°C" : "0°C");
           return json.toString();
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
    }

}
