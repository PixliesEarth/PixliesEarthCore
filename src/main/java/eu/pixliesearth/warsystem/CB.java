package eu.pixliesearth.warsystem;

import lombok.Data;

import java.util.List;

@Data
public class CB {

    private boolean approved;
    private String approvedBy;
    private long approvedOn;

    private String aggressor;
    private String defender;
    private String reason;
    private List<String> proof;

    public CB(String aggressor, String defender, String reason, String... proof) {
        this.aggressor = aggressor;
        this.defender = defender;
        this.reason = reason;
        this.proof = List.of(proof);
    }

    

}
