package eu.pixliesearth.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * THIS ONEROWMAP IS USED TO KEEP TWO VALUES INTO
 * ONE OBJECT, WITHOUT MAKING A BIG MAP.
 * @author MickMMars
 */
@Data
@AllArgsConstructor
public class OneRowMap {

    private Object K;
    private Object V;

}
