package eu.pixliesearth.nations.entities.chunk;

import com.google.gson.Gson;
import eu.pixliesearth.Main;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;

@Data
@AllArgsConstructor
public class NationChunk {

    private String chunkId;
    private String nationId;
    private int x;
    private int z;

    public void claim() {
        Document chunk = new Document("chunkId", chunkId);
        chunk.append("nationId", nationId);
        chunk.append("x", x);
        chunk.append("z", z);
        Main.getChunkCollection().insertOne(chunk);
    }

    public static NationChunk get(int x, int z) {
        Document chunk = new Document("x", x);
        chunk.append("z", z);
        Document found = Main.getChunkCollection().find(chunk).first();
        if (found != null)
            return new Gson().fromJson(found.toJson(), NationChunk.class);
        return null;
    }

    public void unclaim() {
        Document chunk = new Document("chunkId", chunkId);
        chunk.append("nationId", nationId);
        chunk.append("x", x);
        chunk.append("z", z);
        Document found = Main.getChunkCollection().find(chunk).first();
        if (found != null)
            Main.getChunkCollection().deleteOne(found);
    }

}
