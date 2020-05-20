package net.ranktw.DiscordWebHooks.embed;

public class ImageEmbed {
    String url;
    int height;
    int width;
    String proxy_url;
    public ImageEmbed(String url, int height, int width) {
        this.url = url;
        this.height = height;
        this.width = width;
    }
    public ImageEmbed(String url, int height, int width, String proxy_url) {
        this.url = url;
        this.height = height;
        this.width = width;
        this.proxy_url = proxy_url;
    }

}
