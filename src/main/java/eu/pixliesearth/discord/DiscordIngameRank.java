package eu.pixliesearth.discord;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class DiscordIngameRank {

    private @Getter static final Map<String, String> groupRoleMap = new HashMap<>();
    private @Getter static final Map<String, String> roleGroupMap = new HashMap<>();

    static {
        groupRoleMap.put("dev", "589958812635758645");
        groupRoleMap.put("manager", "689599240644722783");
        groupRoleMap.put("admin", "589958914741764108");
        groupRoleMap.put("jrdev", "734993628396650547");
        groupRoleMap.put("mod", "589958871263608838");
        groupRoleMap.put("helper", "600088871711277067");
        groupRoleMap.put("builder", "622571577174917130");
        groupRoleMap.put("ruby", "673917317540741131");
        groupRoleMap.put("sapphire", "650805708291440666");
        groupRoleMap.put("amethyst", "621472811395055649");
        groupRoleMap.put("torpaz", "621472716691603486");
        groupRoleMap.put("default", "622254230882746378");

        roleGroupMap.put("589958812635758645", "dev");
        roleGroupMap.put("689599240644722783", "manager");
        roleGroupMap.put("589958914741764108", "admin");
        roleGroupMap.put("734993628396650547", "jrdev");
        roleGroupMap.put("589958871263608838", "mod");
        roleGroupMap.put("600088871711277067", "helper");
        roleGroupMap.put("622571577174917130", "builder");
        roleGroupMap.put("673917317540741131", "ruby");
        roleGroupMap.put("650805708291440666", "sapphire");
        roleGroupMap.put("621472811395055649", "amethyst");
        roleGroupMap.put("621472716691603486", "topaz");
        roleGroupMap.put("622254230882746378", "default");
    }

}
