package eu.pixliesearth.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import eu.pixliesearth.Main;
import lombok.Data;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
public class PixliesNPC {

    private Player npcPlayer;
    private EntityPlayer npc;
    private GameProfile gameProfile;

    public PixliesNPC(Player player, String npcName) {
        Location location = player.getLocation();
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.translateAlternateColorCodes('&', npcName));

        npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
        npcPlayer = npc.getBukkitEntity().getPlayer();
        npcPlayer.setPlayerListName("");

        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
    }

    public void setSkin(String playerName) {
        gameProfile.getProperties().clear();
        PixliesSkin pixliesSkin = new PixliesSkin(Bukkit.getPlayerUniqueId(playerName).toString().replace("-", ""));

        if (pixliesSkin.getSkinName() != null)
            gameProfile.getProperties().put(pixliesSkin.getSkinName(), new Property(pixliesSkin.getSkinValue(), pixliesSkin.getSkinValue(), pixliesSkin.getSkinSignatur()));

        Main instance = Main.getInstance();

        Bukkit.getScheduler().runTaskLater(instance, () -> {
            for (Player p : Bukkit.getOnlinePlayers())
                p.hidePlayer(instance, npcPlayer);
        }, 1);

        Bukkit.getScheduler().runTaskLater(instance, () -> {
           for (Player p : Bukkit.getOnlinePlayers())
               p.showPlayer(instance, npcPlayer);
        }, 15);

    }

}
