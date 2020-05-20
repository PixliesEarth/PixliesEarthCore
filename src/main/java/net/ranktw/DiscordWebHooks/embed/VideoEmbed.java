package net.ranktw.DiscordWebHooks.embed;

public class VideoEmbed {
    String url;
    int height;
    int width;

    public VideoEmbed(String url, int height, int width) {
        this.url = url;
        this.height = height;
        this.width = width;
    }
}