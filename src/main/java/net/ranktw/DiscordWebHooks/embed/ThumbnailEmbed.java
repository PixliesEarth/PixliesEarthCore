package net.ranktw.DiscordWebHooks.embed;

public class ThumbnailEmbed {
    String url;
    String proxy_url;
    int height;
    int width;

    public ThumbnailEmbed(String url, int height, int width) {
        this.url = url;
        this.height = height;
        this.width = width;
    }
    public ThumbnailEmbed(String url, String proxy_url, int height, int width) {
        this.url = url;
        this.proxy_url = proxy_url;
        this.height = height;
        this.width = width;
    }

}