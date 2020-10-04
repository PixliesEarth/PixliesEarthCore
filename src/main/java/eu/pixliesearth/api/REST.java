package eu.pixliesearth.api;

// import static spark.Spark.*;

public class REST {

    // private final Gson gson;

/*    public REST() {
        gson = new GsonBuilder().setPrettyPrinting().create();

        get("/profile/:uuid", (request, response) -> {
            UUID uuid = UUID.fromString(request.params("uuid"));
            response.type("application/json");
            return gson.toJsonTree(getInstance().getProfile(uuid));
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
    }*/

}
