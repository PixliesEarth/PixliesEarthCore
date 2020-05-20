package net.ranktw.DiscordWebHooks.embed;

public class AuthorEmbed {
    String name, url, icon_url, proxy_icon_url;

    public AuthorEmbed(String name, String url) {
        this(name,url,null);
    }
    public AuthorEmbed(String name, String url, String icon_url) {
        this(name,url,icon_url,null);
    }
    public AuthorEmbed(String name, String url, String icon_url,String proxy_icon_url) {
        this.name = name;
        this.url = url;
        this.icon_url = icon_url;
        this.proxy_icon_url = proxy_icon_url;
    }
}