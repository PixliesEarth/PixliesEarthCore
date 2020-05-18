package eu.pixliesearth;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import eu.pixliesearth.core.commands.*;
import eu.pixliesearth.core.listener.JoinListener;
import eu.pixliesearth.core.listener.LeaveListener;
import eu.pixliesearth.core.listener.MoveListener;
import eu.pixliesearth.core.modules.ChatSystem;
import eu.pixliesearth.core.modules.PrivateMessage;
import eu.pixliesearth.core.modules.WarpSystem;
import eu.pixliesearth.core.modules.economy.EconomySystem;
import eu.pixliesearth.core.modules.economy.VaultAPI;
import eu.pixliesearth.core.objects.Energy;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.utils.FileManager;
import eu.pixliesearth.core.utils.PlayerLists;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.nations.commands.NationCommand;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public final class Main extends JavaPlugin {

    private static @Getter Main instance;

    private static @Getter MongoCollection<Document> playerCollection;

    private static @Getter MongoCollection<Document> nationCollection;

    private static @Getter MongoCollection<Document> chunkCollection;

    private static @Getter Economy economy;

    private @Getter FileManager warpsCfg;

    private @Getter PlayerLists playerLists;

    @Override
    public void onEnable() {

        instance = this;

        init();

    }

    private void init() {

        playerLists = new PlayerLists();

        registerCommands();
        registerEvents(Bukkit.getPluginManager());

        String uri = getConfig().getString("mongodb-connectionstring");
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
        playerCollection = mongoDatabase.getCollection("users");
        nationCollection = mongoDatabase.getCollection("nations");
        chunkCollection = mongoDatabase.getCollection("chunks");

        getServer().getServicesManager().register(Economy.class, new VaultAPI(), this, ServicePriority.Normal);
        economy = new VaultAPI();

        warpsCfg = new FileManager(this, "warps", getDataFolder().getAbsolutePath());
        warpsCfg.save();

        File cfg = new File(getDataFolder().getAbsolutePath() + "/config.yml");
        if (!cfg.exists())
            saveDefaultConfig();

        // PROFILE SCHEDULER
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            Bukkit.getConsoleSender().sendMessage("§7Backing up all profiles in the database.");
            for (Profile profile : playerLists.profiles.values()) {
                if (!playerLists.afk.contains(UUID.fromString(profile.getUniqueId())))
                    profile.setPlayTime(profile.getPlayTime() + 1);
                profile.backup();
            }
            Bukkit.getConsoleSender().sendMessage("§aDone.");
        }, 20 * 60, 20 * 60);

        // ENERGY SCHEDULER
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Profile profile = getProfile(player.getUniqueId());
                Energy.add(profile, 2.0);
            }
        }, (20 * 60) * 60, (20 * 60) * 60);

        new MiniMick().start();

    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("modules").setExecutor(new ModulesCommand());
        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("seen").setExecutor(new SeenCommand());
        getCommand("message").setExecutor(new PrivateMessage());
        getCommand("profile").setExecutor(new ProfileCommand());
        getCommand("link").setExecutor(new LinkCommand());
        getCommand("warp").setExecutor(new WarpSystem());
        getCommand("nation").setExecutor(new NationCommand());
        getCommand("backup").setExecutor(new BackupCommand());
        getCommand("economy").setExecutor(new EconomySystem());
        getCommand("balance").setExecutor(new BalanceCommand());
    }

    private void registerEvents(PluginManager manager) {
        manager.registerEvents(new ChatSystem(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new LeaveListener(), this);
        manager.registerEvents(new MoveListener(), this);
    }

    public Profile getProfile(UUID uuid) {
        if (playerLists.profiles.get(uuid) == null) {
            playerLists.profiles.put(uuid, Profile.get(uuid));
            return playerLists.profiles.get(uuid);
        } else {
            return playerLists.profiles.get(uuid);
        }
    }

}