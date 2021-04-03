package eu.pixliesearth.nations.managers.dynmap.area;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.UUID;

import eu.pixliesearth.nations.entities.nation.Ideology;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerIcon;

import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.Religion;
import eu.pixliesearth.nations.managers.dynmap.TileFlags;

public class AreaCommon {


    /**
     * Find all contiguous blocks, set in target and clear in source
     */
    public static int floodFillTarget(TileFlags src, TileFlags dest, int x, int y) {
        int cnt = 0;
        ArrayDeque<int[]> stack = new ArrayDeque<int[]>();
        stack.push(new int[] { x, y });

        while (!stack.isEmpty()) {
            int[] nxt = stack.pop();
            x = nxt[0];
            y = nxt[1];
            if(src.getFlag(x, y)) { /* Set in src */
                src.setFlag(x, y, false);   /* Clear source */
                dest.setFlag(x, y, true);   /* Set in destination */
                cnt++;
                if(src.getFlag(x+1, y))
                    stack.push(new int[] { x+1, y });
                if(src.getFlag(x-1, y))
                    stack.push(new int[] { x-1, y });
                if(src.getFlag(x, y+1))
                    stack.push(new int[] { x, y+1 });
                if(src.getFlag(x, y-1))
                    stack.push(new int[] { x, y-1 });
            }
        }
        return cnt;
    }

    /**
     * Display name of the owner
     *
     * @param nation
     * @return
     */
    private static String getFactionOwner(final Nation nation) {
        return nation.getLeader().equalsIgnoreCase("NONE") ? "SERVER" : Bukkit.getOfflinePlayer(UUID.fromString(nation.getLeader())).getName();
    }

    public static String formatInfoWindow(final String infoWindow, final Nation nation) {
        String formattedWindow = new StringBuilder("<div class=\"regioninfo\">").append(infoWindow).append("</div>").toString();

        formattedWindow = formattedWindow.replace("%owner%", getFactionOwner(nation));

        formattedWindow = formattedWindow.replace("%regionname%", ChatColor.stripColor(nation.getName()));

        // The describe can be null or empty
        if (nation.getDescription() != null) {
            formattedWindow = formattedWindow.replace("%description%", ChatColor.stripColor(nation.getDescription()));
        }

        final String adm = nation.getLeader().equalsIgnoreCase("NONE") ? "SERVER" : Bukkit.getOfflinePlayer(UUID.fromString(nation.getLeader())).getName();
        formattedWindow = formattedWindow.replace("%playerowners%", adm);

        formattedWindow = formattedWindow.replace("%money%", "$" + nation.getMoney());

        formattedWindow = formattedWindow.replace("%members%", nation.getMembers().size() + "");
        formattedWindow = formattedWindow.replace("%nation%", ChatColor.stripColor(nation.getName()));
        formattedWindow = formattedWindow.replace("%era%", StringUtils.capitalize(nation.getCurrentEra().getName()).replace("_", " "));
        formattedWindow = formattedWindow.replace("%religion%", ChatColor.stripColor(Religion.valueOf(nation.getReligion()).getDisplayName()));
        formattedWindow = formattedWindow.replace("%ideology%", ChatColor.stripColor(Ideology.valueOf(nation.getIdeology()).getDisplayName()));
        formattedWindow = formattedWindow.replace("%capital%", nation.getCapital().getName());

        return formattedWindow;
    }

    public static void addStyle(final Map<String, AreaStyle> cusstyle, final AreaStyle defstyle, final String resid, final AreaMarker areaMarker) {
        AreaStyle as = cusstyle.get(resid);
        if (as == null)
            as = defstyle;

        int sc = 0xFF0000;
        int fc = 0xFF0000;
        try {
            sc = Integer.parseInt(as.getStrokecolor().substring(1), 16);
            fc = Integer.parseInt(as.getFillcolor().substring(1), 16);
        } catch (NumberFormatException ignored) { }

        areaMarker.setLineStyle(as.getStrokeweight(), as.getStrokeopacity(), sc);
        areaMarker.setFillStyle(as.getFillopacity(), fc);
        areaMarker.setBoostFlag(as.isBoost());
    }

    public static MarkerIcon getMarkerIcon(final Map<String, AreaStyle> cusstyle, final AreaStyle defstyle, final String factionName) {
        AreaStyle as = cusstyle.get(factionName);
        if (as == null) {
            as = defstyle;
        }
        return as.getHomeicon();
    }
}
