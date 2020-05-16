package eu.pixliesearth.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CooldownMap {

    private double timeLeft;
    private int taskId;

}
