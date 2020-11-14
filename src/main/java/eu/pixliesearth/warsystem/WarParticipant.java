package eu.pixliesearth.warsystem;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WarParticipant {

    private String nationId;
    private WarSide side;

    public enum WarSide {

        DEFENDER,
        AGGRESSOR

    }

}
