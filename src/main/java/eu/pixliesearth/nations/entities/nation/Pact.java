package eu.pixliesearth.nations.entities.nation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Pact {

    private String pactId;
    private String name;
    private String dynmapFill;
    private String dynmapStroke;
    private List<String> members;
    private Map<String, Object> extras;

}
