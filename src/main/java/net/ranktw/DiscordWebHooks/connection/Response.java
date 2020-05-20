package net.ranktw.DiscordWebHooks.connection;

import com.google.gson.annotations.SerializedName;
//import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

//@Getter
public class Response {
    // {
    // "connection": ["Must be 2000 or fewer in length."],
    // "embeds": ["0"],
    // "username": ["Must be between 2 and 32 in length."]
    // }
    boolean global;
    String message;
    @SerializedName("retry_after")
    int retryAfter;
    List<String> username = new ArrayList<>();
    List<String> embeds = new ArrayList<>();
    List<String> connection = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public int getRetryAfter() {
        return retryAfter;
    }

    public List<String> getUsername() {
        return username;
    }

    public List<String> getEmbeds() {
        return embeds;
    }

    public List<String> getConnection() {
        return connection;
    }
}
