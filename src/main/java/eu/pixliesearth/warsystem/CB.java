package eu.pixliesearth.warsystem;

import com.google.gson.Gson;
import eu.pixliesearth.Main;
import lombok.Data;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class CB {

    public static final Map<String, CB> cbList = new HashMap<>();

    static {
        for (Document doc : Main.getCbCollection().find())
            cbList.put(doc.getString("uuid"), new Gson().fromJson(doc.getString("json"), CB.class));
    }

    private String uuid;

    private Status status;
    private String answeredBy;
    private long answeredOn;
    private String denyReason;

    private String aggressor;
    private String defender;
    private String reason;
    private List<String> proof;

    public CB(String aggressor, String defender, String reason, String... proof) {
        this.uuid = UUID.randomUUID().toString();
        this.aggressor = aggressor;
        this.defender = defender;
        this.reason = reason;
        this.proof = List.of(proof);
    }

    public void accept(String who) {
        status = Status.APPROVED;
        answeredBy = who;
        answeredOn = System.currentTimeMillis();
    }

    public void deny(String who, String reason) {
        status = Status.DENIED;
        answeredOn = System.currentTimeMillis();
        denyReason = reason;
        answeredBy = who;
    }

    public void save() {
        cbList.put(uuid, this);
        Document document = new Document("uuid", uuid);
        document.put("json", new Gson().toJson(this));
        Main.getCbCollection().insertOne(document);
    }

    public enum Status {

        PENDING,
        APPROVED,
        DENIED

    }

}
