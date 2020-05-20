package net.ranktw.DiscordWebHooks;

public class DiscordModify extends Payload {
    String name,avatar,channel_id;

    DiscordModify(){
        this.name="";
        this.avatar="";
        this.channel_id="";
    }
    public DiscordModify(String name, String avatar, String channel_id) {
        this.name=name;
        this.avatar=avatar;
        this.channel_id=channel_id;
    }

}
