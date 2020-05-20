package net.ranktw.DiscordWebHooks;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DiscordMessage extends Payload {
    String username;
    String content;
    @SerializedName("avatar_url")
    String avatarUrl;
    @SerializedName("tts")
    boolean textToSpeech;
    List<DiscordEmbed> embeds = new ArrayList<>();

    public DiscordMessage() {
        this(null, "",null, false);
    }
    public DiscordMessage(String content) {
        this(null, content,null, false);
    }
    public DiscordMessage(String username, String content, String avatar_url) {
        this(username, content, avatar_url, false);
    }
    public DiscordMessage(String username, String content, String avatar_url, boolean tts) {
        setUsername(username);
        setContent(content);
        setAvatarUrl(avatar_url);
        setTextToSpeech(tts);
    }

    public void setUsername(String username) {
        if (username != null) {
            this.username = username.substring(0, Math.min(31, username.length()));
        } else {
            this.username = null;
        }
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public void setTextToSpeech(boolean textToSpeech) {
        this.textToSpeech = textToSpeech;
    }

    public void setEmbeds(List<DiscordEmbed> embeds) {
        this.embeds = embeds;
    }
    public void addEmbeds(DiscordEmbed embed) {
        this.embeds.add(embed);
    }

    public static class Builder {
        private final DiscordMessage message;

        public Builder() {
            this.message = new DiscordMessage();
            this.message.setEmbeds(new ArrayList<DiscordEmbed>());
        }
        public Builder(String content) {
            this.message = new DiscordMessage(content);
            this.message.setEmbeds(new ArrayList<DiscordEmbed>());
        }

        public Builder withUsername(String username) {
            message.setUsername(username);
            return this;
        }

        public Builder withContent(String content) {
            message.setContent(content);
            return this;
        }

        public Builder withAvatarURL(String avatarURL) {
            message.setAvatarUrl(avatarURL);
            return this;
        }

        public Builder withTTS(boolean tts) {
            message.setTextToSpeech(tts);
            return this;
        }

        public Builder withEmbed(DiscordEmbed embed) {
            message.addEmbeds(embed);
            return this;
        }

        public DiscordMessage build() {
            return message;
        }
    }

}
