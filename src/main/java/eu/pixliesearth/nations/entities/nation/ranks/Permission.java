package eu.pixliesearth.nations.entities.nation.ranks;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Permission {

    INVITE,
    MODERATE,
    CLAIM,
    UNCLAIM,
    BUILD,
    INTERACT,
    MANAGE_SETTLEMENTS,
    EDIT_RANKS,
    MANAGE_RELATIONS,
    BANK_DEPOSIT,
    BANK_WITHDRAW,
    PURCHASE_UPGRADES,
    FOREIGN_PERMS,
    DESCRIPTION,
    NAME,
    CHANGE_LEADERSHIP,
    SET_FLAG,
    DECLARE_WAR,
    JUSTIFY_WAR_GOAL,
    SET_WIKI_LINK,
    MANAGE_WAR,
    EDIT_FLAGS,
    CHANGE_CAPITAL,
    CHUNK_MENU,
    MANAGE_ELECTIONS,
    MANAGE_TAX
    ;

    public boolean hasPermission(CommandSender sender) {
        if (!(sender instanceof Player)) return true;
        Profile profile = Main.getInstance().getProfile(((Player) sender).getUniqueId());
        if (profile.isStaff()) return true;
        return hasNationPermission(profile, this);
    }

    public static boolean hasNationPermission(Profile profile, Permission permission) {
        if (profile.isStaff()) return true;
        if (!profile.isInNation()) return false;
        if (profile.isLeader()) return true;
        Nation nation = profile.getCurrentNation();
        Rank rank = Rank.get(nation.getRanks().get(profile.getNationRank()));
        return rank.getPermissions().contains(permission.name());
    }

    public static boolean hasForeignPermission(Profile profile, Permission permission, Nation host) {
        if (profile.isStaff()) return true;
        if (profile.getNationId().equals(host.getNationId()))
            return hasNationPermission(profile, permission);
        if (profile.getExtras().containsKey("PERMISSION:" + host.getNationId() + ":" + permission.name())) return true;
        if (profile.isInNation())
            return profile.getCurrentNation().getExtras().containsKey("PERMISSION:" + host.getNationId() + ":" + permission.name());
        return false;
    }

    public static boolean hasForeignPermission(Nation guest, Permission permission, Nation host) {
        return guest.getExtras().containsKey("PERMISSION:" + host.getNationId() + ":" + permission.name());
    }

    public static boolean hasAccessHere(Profile profile, NationChunk chunk) {
        if (profile.isInNation()) return chunk.hasAccess(profile.getCurrentNation());
        return chunk.hasAccess(profile);
    }

    public static boolean exists(String name) {
        for (Permission p : values()) {
            if (p.name().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

}
