package eu.pixliesearth.core.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Boost {

    private UUID owner;
    private BoostType boostType;

    public enum BoostType {



    }

}
