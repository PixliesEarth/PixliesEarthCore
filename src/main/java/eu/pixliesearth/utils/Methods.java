package eu.pixliesearth.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Methods {
	
	public static String convertEnergyDouble(double d) {
		String s = "";
		d = Methods.round(d, 3);
		if (d>=1000000000D) {
			s += Double.toString(d/1000000000D)+"BW";
		} else if (d>=1000000D) {
			s += Double.toString(d/1000000D)+"MW";
		} else if (d>=1000D) {
			s += Double.toString(d/1000D)+"KW";
		} else if (d>=1D) {
			s += Double.toString(d)+"W";
		} else {
			s += Double.toString(d);
		}
		return s;
	}
	
    public static String generateId(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String getIp(Player player) {
        return player.getAddress().toString().substring(1).split(":")[0];
    }

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static boolean testPermission(CommandSender sender, String permission) {
        if (!(sender instanceof Player)) return true;
        return sender.hasPermission(permission);
    }

    public static double calculateDistance(double x1, double x2, double z1, double z2) {
        return Math.sqrt(Math.pow((x2 - z1), 2) + Math.pow((z2 - x1), 2));
    }

    public static double calculateDistance(Location l1, Location l2) {
        return l1.distanceSquared(l2);
    }

    public static String getTimeAsString(long duration, boolean useMilliseconds) {
        long r = (duration / 100);
        long sec = r / 10;
        if (useMilliseconds) {
            long millis = r % 10;
            return sec >= 60 ? format((int) sec) : sec + "." + millis + "s";
        } else {
            return sec >= 60 ? format((int) sec) : "00:" + (sec < 10 ? "0" + sec : sec);
        }
    }

    public static String format(int time) {
        int sec = time % 60;
        int min = time / 60 % 60;
        int h = time / 3600 % 24;

        return (h > 0 ? h + ":" : "") + (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
    }

    public static Material getSBWoolByCC(String chatColor) {
        switch (chatColor) {
            case "§b":
                return Material.LIGHT_BLUE_WOOL;
            case "§c":
                return Material.RED_WOOL;
            case "§e":
                return Material.YELLOW_WOOL;
            case "§a":
                return Material.GREEN_WOOL;
            case "§3":
                return Material.CYAN_WOOL;
        }
        return Material.CYAN_WOOL;
    }

    public static ChatColor getNeighbourColor(String color) {
        ChatColor cc = ChatColor.AQUA;
        switch (color) {
            case "§b":
                return ChatColor.DARK_AQUA;
            case "§c":
                return ChatColor.DARK_RED;
            case "§e":
                return ChatColor.GOLD;
            case "§a":
                return ChatColor.DARK_GREEN;
            case "§3":
                return ChatColor.BLUE;
        }
        return cc;
    }

    public String getProgressBar(double current_progress, double required_progress, ChatColor finished, ChatColor left) {
        double progress_percentage = current_progress / required_progress;
        StringBuilder sb = new StringBuilder();
        int bar_length = 20;
        for (int i = 0; i < bar_length; i++) {
            if (i < bar_length * progress_percentage) {
                sb.append(finished).append("▇");
            } else {
                sb.append(left).append("▇");
            }
        }
       return sb.toString();
    }

    private final static int CENTER_PX = 154;

    public static String getCenteredMessage(String message){
        if(message == null || message.equals("")) return "";
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            }else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        return ChatColor.translateAlternateColorCodes('&', sb.toString() + message);
    }

    public static String getCenterSpaces(String message){
        if(message == null || message.equals("")) return "";
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            }else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        return ChatColor.translateAlternateColorCodes('&', sb.toString());
    }

    public static Material getStainedGlassPaneByColChar(char colChar) {
        switch (colChar) {
            case 'b':
                return Material.LIGHT_BLUE_STAINED_GLASS_PANE;
            case 'c':
                return Material.RED_STAINED_GLASS_PANE;
            case 'f':
                return Material.WHITE_STAINED_GLASS_PANE;
            case 'd':
                return Material.MAGENTA_STAINED_GLASS_PANE;
        }
        return Material.WHITE_STAINED_GLASS_PANE;
    }

    public static String translateToHex(String s) {
        String returner = s;
        String[] hexes = StringUtils.substringsBetween(s, "#{", "}");
        if (hexes != null) {
            for (String s1 : hexes) {
                StringBuilder builder = new StringBuilder();
                for (char c : s1.toCharArray()) {
                    if (c != '&') {
                        builder.append('&');
                        builder.append(c);
                    }
                }
                returner = returner.replace("#{" + s1 + "}", "&x" + builder.toString());
            }
        }
        return returner;
    }

    public static boolean removeRequiredAmount(ItemStack item, final Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack value = inventory.getItem(i);
            if (value == null) continue;
            if (!value.isSimilar(item)) continue;
            if (value.getAmount() != 0) {
                if (value.getAmount() == item.getAmount()) { inventory.clear(i);
                } else {
                    value.setAmount(value.getAmount() - item.getAmount());
                    inventory.setItem(i, value);
                }
                return true;
            }
        }
        return false;
    }

    public static void removeRequiredAmountWithinBound(ItemStack item, final Inventory inventory, List<Integer> slots) {
        for (int i : slots) {
            ItemStack value = inventory.getItem(i);
            if (value == null) continue;
            if (!value.isSimilar(item)) continue;
            if (value.getAmount() != 0) {
                if (value.getAmount() == item.getAmount()) inventory.clear(i);
                else {
                    value.setAmount(value.getAmount() - item.getAmount());
                    inventory.setItem(i, value);
                }
                break;
            }
        }
    }

    public static String replaceBadWord(String s) {
        StringBuilder builder = new StringBuilder();
        //for (char c : s.toCharArray())
        for (int i = 0; i < s.toCharArray().length; i++)
            builder.append("*");
        return builder.toString();
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String formatNumber(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatNumber(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatNumber(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}