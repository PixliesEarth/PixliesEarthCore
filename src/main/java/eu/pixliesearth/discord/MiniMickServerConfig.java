package eu.pixliesearth.discord;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MiniMickServerConfig {

    private String serverId;
    private String updatesChannel;
    private String nationId;

}
