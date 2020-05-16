package eu.pixliesearth.nations.entities.nation;

import com.google.gson.Gson;
import eu.pixliesearth.Main;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Nation {

    private String nationId;
    private String name;
    private String description;
    private String era;
    private int xpPoints;
    private double money;
    private String leader;
    private List<String> members;


    // ADVANCED METHODS
    public void save() {
        Document nation = new Document("nationId", nationId);
        Document found = Main.getNationCollection().find(nation).first();
        nation.append("name", name);
        nation.append("description", description);
        nation.append("era", era);
        nation.append("xpPoints", xpPoints);
        nation.append("money", money);
        nation.append("leader", leader);
        nation.append("members", members);
        if (found == null) {
            Main.getNationCollection().insertOne(nation);
        } else {
            Main.getNationCollection().deleteOne(found);
            Main.getNationCollection().insertOne(nation);
        }
    }

    public void remove() {
        Document found = Main.getNationCollection().find(new Document("nationId", nationId)).first();
        if (found != null) {
            Main.getNationCollection().deleteOne(found);
        }
        for (String member : members)
            Main.getInstance().getProfile(UUID.fromString(member)).removeFromNation();
    }

    public static Nation getById(String id) {
        Document nation = new Document("nationId", id);
        Document found = Main.getNationCollection().find(nation).first();
        if (found != null)
            return new Gson().fromJson(found.toJson(), Nation.class);
        return null;
    }

    public static Nation getByName(String name) {
        Document nation = new Document("name", name);
        Document found = Main.getNationCollection().find(nation).first();
        if (found != null)
            return new Gson().fromJson(found.toJson(), Nation.class);
        return null;
    }

}
