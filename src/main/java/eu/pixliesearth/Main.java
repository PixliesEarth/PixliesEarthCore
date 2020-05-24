package eu.pixliesearth;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import eu.pixliesearth.core.commands.*;
import eu.pixliesearth.core.io.github.thatkawaiisam.assemble.Assemble;
import eu.pixliesearth.core.io.github.thatkawaiisam.assemble.AssembleStyle;
import eu.pixliesearth.core.listener.*;
import eu.pixliesearth.core.modules.ChatSystem;
import eu.pixliesearth.core.modules.PrivateMessage;
import eu.pixliesearth.core.modules.WarpSystem;
import eu.pixliesearth.core.modules.economy.EconomySystem;
import eu.pixliesearth.core.modules.economy.VaultAPI;
import eu.pixliesearth.core.objects.Energy;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.scoreboard.ScoreboardAdapter;
import eu.pixliesearth.core.utils.FileManager;
import eu.pixliesearth.core.utils.PlayerLists;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.nations.commands.NationCommand;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
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

    private static @Getter Permission perms = null;

    private static @Getter Assemble assemble = null;

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

        // PROFILE & AFK SCHEDULER
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            for (UUID uuid : playerLists.locationMap.keySet()) {
                if (playerLists.locationMap.get(uuid).getLocation().toLocation() == Bukkit.getPlayer(uuid).getLocation()) {
                    playerLists.locationMap.get(uuid).setMinutes(playerLists.locationMap.get(uuid).getMinutes() + 1);
                } else {
                    playerLists.locationMap.get(uuid).setMinutes(0);
                }
                if (playerLists.locationMap.get(uuid).getMinutes() == getConfig().getInt("afktime", 15)) {
                    playerLists.afk.add(uuid);
                    Bukkit.broadcastMessage("§8Player §7" + Bukkit.getPlayer(uuid).getDisplayName() + " §8is now AFK.");
                }
            }
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
                if (!playerLists.afk.contains(player.getUniqueId())) {
                    Profile profile = getProfile(player.getUniqueId());
                    Energy.add(profile, 2.0);
                }
            }
        }, (20 * 60) * 60, (20 * 60) * 60);

        new MiniMick().start();

        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();

        assemble = new Assemble(this, new ScoreboardAdapter());

        assemble.setTicks(2);

        assemble.setAssembleStyle(AssembleStyle.VIPER);

    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("modules").setExecutor(new ModulesCommand());
        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("seen").setExecutor(new SeenCommand());
        getCommand("coins").setExecutor(new CoinsCommand());
        getCommand("message").setExecutor(new PrivateMessage());
        getCommand("profile").setExecutor(new ProfileCommand());
        getCommand("link").setExecutor(new LinkCommand());
        getCommand("warp").setExecutor(new WarpSystem());
        getCommand("nation").setExecutor(new NationCommand());
        getCommand("backup").setExecutor(new BackupCommand());
        getCommand("economy").setExecutor(new EconomySystem());
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("gmc").setExecutor(new GamemodeCreativeCommand());
        getCommand("gms").setExecutor(new GamemodeSurvivalCommand());
        getCommand("gmsp").setExecutor(new GamemodeSpectatorCommand());
    }

    private void registerEvents(PluginManager manager) {
        manager.registerEvents(new ChatSystem(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new LeaveListener(), this);
        manager.registerEvents(new MoveListener(), this);
        manager.registerEvents(new BlockBreakListener(), this);
        manager.registerEvents(new ItemInteractListener(), this);
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