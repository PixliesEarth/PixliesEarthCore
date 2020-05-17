package eu.pixliesearth.core.modules.economy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@AllArgsConstructor
public class Receipt {

    private double amount;
    private boolean lost;
    private long time;

    public static Receipt fromString(String string) {
        String[] split = string.split(";");
        return new Receipt(Double.parseDouble(split[0]), Boolean.parseBoolean(split[1]),Long.parseLong(split[2]));
    }

    public static String create(double amount, boolean lost) {
        return amount + ";" + lost + ";" + System.currentTimeMillis();
    }

    public static LocalDateTime millsToLocalDateTime(long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime date = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return date;
    }

}
