package eu.pixliesearth.nations.entities.nation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NationCurrency {

    private String name;
    private String nameShort;
    private String bank;
    private double price;
    private int inTheMarket;

}