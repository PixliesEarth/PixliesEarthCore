package net.ranktw.DiscordWebHooks;

import com.google.gson.annotations.SerializedName;
import net.ranktw.DiscordWebHooks.embed.*;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class DiscordEmbed extends Payload {
    String title;
    String type;
    String description;
    @SerializedName("url")
    String title_url;
    String timestamp;
    int color;
    FooterEmbed footer;
    ImageEmbed image;
    ThumbnailEmbed thumbnail;
    VideoEmbed video;
    ProviderEmbed provider;
    AuthorEmbed author;
    List<FieldEmbed> fields = new ArrayList<>();

    public DiscordEmbed() {
        this(null, null, null);
    }
    public DiscordEmbed(String title, String description) {
        this(title, description, null);
    }
    public DiscordEmbed(String title, String description, String titleUrl) {
        setTitle(title);
        setDescription(description);
        setTitleUrl(titleUrl);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitleUrl(String url) {
        this.title_url = url;
    }

    public void setTimestamp(long millisTimestamp) {
        Calendar gmt = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmt.setTimeInMillis(millisTimestamp);
        this.timestamp = OffsetDateTime.ofInstant(gmt.toInstant(), gmt.getTimeZone().toZoneId()).toString();
    }

    public void setColor(Color color) {
        if (color != null) {
            this.color = 65536 * color.getRed() + 256 * color.getGreen() + color.getBlue();
        }
    }

    public void setFooter(FooterEmbed footer) {
        this.footer = footer;
    }

    public void setImage(ImageEmbed image) {
        this.image = image;
    }

    public void setThumbnail(ThumbnailEmbed thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setVideo(VideoEmbed video) {
        this.video = video;
    }

    public void setProvider(ProviderEmbed provider) {
        this.provider = provider;
    }

    public void setAuthor(AuthorEmbed author) {
        this.author = author;
    }

    public void setFields(List<FieldEmbed> fields) {
        this.fields = fields;
    }
    public void addFields(FieldEmbed field) {
        this.fields.add(field);
    }

    public static class Builder {
        private final DiscordEmbed embed;

        public Builder() {
            this.embed = new DiscordEmbed();
        }

        public Builder withTitle(String title) {
            embed.setTitle(title);
            return this;
        }

        public Builder withType(String type) {
            embed.setType(type);
            return this;
        }

        public Builder withDescription(String description) {
            embed.setDescription(description);
            return this;
        }

        public Builder withTitleUrl(String url) {
            embed.setTitleUrl(url);
            return this;
        }

        public Builder withColor(Color color) {
            embed.setColor(color);
            return this;
        }

        public Builder withAuthor(AuthorEmbed author) {
            embed.setAuthor(author);
            return this;
        }

        public Builder withProvider(ProviderEmbed provider) {
            embed.setProvider(provider);
            return this;
        }

        public Builder withImage(ImageEmbed image) {
            embed.setImage(image);
            return this;
        }

        public Builder withThumbnail(ThumbnailEmbed thumbnail) {
            embed.setThumbnail(thumbnail);
            return this;
        }

        public Builder withVideo(VideoEmbed video) {
            embed.setVideo(video);
            return this;
        }

        public Builder withFooter(FooterEmbed footer) {
            embed.setFooter(footer);
            return this;
        }

        public Builder withField(FieldEmbed field) {
            embed.addFields(field);
            return this;
        }

        public Builder withTimestamp(long millisTimestamp) {
            embed.setTimestamp(millisTimestamp);
            return this;
        }

        public DiscordEmbed build() {
            return embed;
        }
    }

}
