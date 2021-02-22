package eu.pixliesearth.nations.entities.chunk;

import lombok.Getter;

public enum NationChunkType {

    NORMAL("Uninhabited chunk"),
    CAPITAL("Capital chunk"),
    CITY("City chunk"),
    HOSPITAL("Hospital chunk"),
    BANK("Bank chunk");

    private @Getter final String name;

    NationChunkType(String name) {
        this.name = name;
    }

}
