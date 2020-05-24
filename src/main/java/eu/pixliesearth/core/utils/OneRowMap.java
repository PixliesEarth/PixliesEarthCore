package eu.pixliesearth.core.utils;

import com.mongodb.BasicDBObject;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * THIS ONEROWMAP IS USED TO KEEP TWO VALUES INTO
 * ONE OBJECT, WITHOUT MAKING A BIG MAP.
 * @author MickMMars
 */
public class OneRowMap extends BasicDBObject {

    public static final String KEY = "Key";
    public static final String VALUE = "Value";

    private static final long serialVersionUID = 2105061907470199595L;

    private Object Key, Value;

    public OneRowMap(Object Key, Object Value) {
        this.Key = Key;
        this.Value = Value;
    }

    public Object getKey() {
        return get(KEY);
    }

    public Object getValue() {
        return get(VALUE);
    }

    public void setKey(Object key) {
        put(KEY, key);
    }

    public void setValue(Object value) {
        put(VALUE, value);
    }
}
