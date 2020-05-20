package net.ranktw.DiscordWebHooks.embed;

public class FooterEmbed {
    String text;
    String icon_url;
    String proxy_icon_url;
    public FooterEmbed(String text, String icon_url) {
        this.text = text;
        this.icon_url = icon_url;
    }
    public FooterEmbed(String text, String icon_url, String proxy_icon_url) {
        this.text = text;
        this.icon_url = icon_url;
        this.proxy_icon_url = proxy_icon_url;
    }

}