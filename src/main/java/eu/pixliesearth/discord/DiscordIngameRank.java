package eu.pixliesearth.discord;

import eu.pixliesearth.Main;
import net.luckperms.api.LuckPerms;
import org.javacord.api.DiscordApi;

import java.util.HashMap;
import java.util.Map;

public class DiscordIngameRank {

    public static Map<String, String> groupRoleMap() {
        Map<String, String> groupRoleMap = new HashMap<>();
        groupRoleMap.put("dev", "589958812635758645");
        groupRoleMap.put("manager", "689599240644722783");
        groupRoleMap.put("admin", "589958914741764108");
        groupRoleMap.put("mod", "589958871263608838");
        groupRoleMap.put("helper", "600088871711277067");
        groupRoleMap.put("builder", "622571577174917130");
        groupRoleMap.put("ultimate", "673917317540741131");
        groupRoleMap.put("snow", "650805708291440666");
        groupRoleMap.put("adventsnow", "650805708291440666");
        groupRoleMap.put("nitrogen", "628334932606844968");
        groupRoleMap.put("royal", "621472811395055649");
        groupRoleMap.put("corporal", "621472716691603486");
        groupRoleMap.put("default", "622254230882746378");
        return groupRoleMap;
    }

}
