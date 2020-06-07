package eu.pixliesearth;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import eu.pixliesearth.core.commands.economy.BalanceCommand;
import eu.pixliesearth.core.commands.economy.CoinsCommand;
import eu.pixliesearth.core.commands.economy.PayCommand;
import eu.pixliesearth.core.commands.player.*;
import eu.pixliesearth.core.commands.util.*;
import eu.pixliesearth.core.listener.*;
import eu.pixliesearth.core.modules.ChatSystem;
import eu.pixliesearth.core.modules.PrivateMessage;
import eu.pixliesearth.core.modules.ShopSystem;
import eu.pixliesearth.core.modules.WarpSystem;
import eu.pixliesearth.core.modules.economy.EconomySystem;
import eu.pixliesearth.core.modules.economy.VaultAPI;
import eu.pixliesearth.core.objects.Energy;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.scoreboard.ScoreboardAdapter;
import eu.pixliesearth.utils.FileManager;
import eu.pixliesearth.utils.PlayerLists;
import eu.pixliesearth.core.customitems.commands.CiGiveCommand;
import eu.pixliesearth.core.customitems.listeners.ItemsInteractEvent;
import eu.pixliesearth.core.customitems.listeners.SlingshotListener;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.core.guns.commands.GunGive;
import eu.pixliesearth.core.guns.listeners.AkGun;
import eu.pixliesearth.nations.commands.NationCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.lib.io.github.thatkawaiisam.assemble.Assemble;
import eu.pixliesearth.lib.io.github.thatkawaiisam.assemble.AssembleStyle;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.UUID;

public final class Main extends JavaPlugin {

    private static @Getter final Permission perms = null;
    private static @Getter Main instance;
    private static @Getter MongoCollection<Document> playerCollection;
    private static @Getter MongoCollection<Document> nationCollection;
    private static @Getter VaultAPI economy;
    private static @Getter Assemble assemble = null;
    private static @Getter Scoreboard emptyScoreboard;
    private @Getter FileManager warpsCfg;
    private @Getter FileManager shopCfg;
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

        economy = new VaultAPI();
        getServer().getServicesManager().register(Economy.class, economy, this, ServicePriority.Normal);

        warpsCfg = new FileManager(this, "warps", getDataFolder().getAbsolutePath());
        warpsCfg.save();

        shopCfg = new FileManager(this, "shop", getDataFolder().getAbsolutePath());
        shopCfg.save();

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
        }, 20 * 60, (20 * 60) * 5);

        // ENERGY SCHEDULER
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!playerLists.afk.contains(player.getUniqueId())) {
                    Profile profile = getProfile(player.getUniqueId());
                    Energy.add(profile, 2.0);
                }
            }
        }, (20 * 60) * 60, (20 * 60) * 60);

        // NATION SCHEDULER
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            getLogger().info("§aSaving all nations in the database...");
            for (Nation nation : NationManager.nations.values())
                nation.backup();
            getLogger().info("§aSaved all nations in the database.");
        }, (20 * 60) * 15, (20 * 60) * 15);

        NationManager.init();

        //BungeeCord
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        new MiniMick().start();

        assemble = new Assemble(this, new ScoreboardAdapter());

        assemble.setTicks(2);

        assemble.setAssembleStyle(AssembleStyle.VIPER);

        NationChunk.init();

        emptyScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

/*        // check if brigadier is supported
        if (CommodoreProvider.isSupported()) {

            // get a commodore instance
            Commodore commodore = CommodoreProvider.getCommodore(this);

            // register your completions.
            registerCompletions(commodore, command);
        }*/


        discordEnable();
    }

    @Override
    public void onDisable() {
        discordDisable();
        for (Player player : Bukkit.getOnlinePlayers())
            getProfile(player.getUniqueId()).backup();
        for (Nation nation : NationManager.nations.values())
            nation.backup();
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
        getCommand("gma").setExecutor(new GamemodeAdventureCommand());
        getCommand("staff").setExecutor(new StaffCommand());
        getCommand("flyspeed").setExecutor(new FlySpeedCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("tpaccept").setExecutor(new TpacceptCommand());
        getCommand("skull").setExecutor(new SkullCommand());
        getCommand("walkspeed").setExecutor(new WalkSpeedCommand());
        getCommand("craft").setExecutor(new CraftCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("gungive").setExecutor(new GunGive());
        getCommand("gungive").setTabCompleter(new GunGive());
        getCommand("shop").setExecutor(new ShopSystem());
        getCommand("lobby").setExecutor(new LobbyCommand());
        getCommand("boost").setExecutor(new BoostCommand());
        getCommand("cigive").setExecutor(new CiGiveCommand());
        getCommand("cigive").setTabCompleter(new CiGiveCommand());
    }

    private void registerEvents(PluginManager manager) {
        manager.registerEvents(new ChatSystem(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new LeaveListener(), this);
        manager.registerEvents(new MoveListener(), this);
        manager.registerEvents(new BlockBreakListener(), this);
        manager.registerEvents(new ItemInteractListener(), this);
        manager.registerEvents(new AkGun(this), this);
        manager.registerEvents(new PlayerCombatListener(), this);
        manager.registerEvents(new PacketListener(), this);
        manager.registerEvents(new AchievementListener(), this);
        //THIS IS NOT ITEMINTERACTLISTENER DONT DELETE
        manager.registerEvents(new ItemsInteractEvent(), this);
        manager.registerEvents(new SlingshotListener(), this);
        manager.registerEvents(new MobSpawnListener(), this);
        manager.registerEvents(new DeathListener(), this);
        manager.registerEvents(new AnvilListener(), this);
    }

    /**
     * @param uuid Uuid of the player
     * @return A profile object of the given playerUUID
     */
    public Profile getProfile(UUID uuid) {
        if (playerLists.profiles.get(uuid) == null) {
            playerLists.profiles.put(uuid, Profile.get(uuid));
            return playerLists.profiles.get(uuid);
        } else {
            return playerLists.profiles.get(uuid);
        }
    }

    public void discordEnable() {
        MiniMick.getApi().getServerTextChannelById(getConfig().getString("chatchannel")).get().sendMessage(new EmbedBuilder()
                .setColor(Color.green)
                .setDescription("<:online:716052437848424558> Server is online!")
        );
        MiniMick.getApi().getServerTextChannelById(getConfig().getString("chatchannel")).get().createUpdater().setTopic("<:online:716052437848424558> Earth is online!").update();

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            if (!(MiniMick.getApi().getServerTextChannelById(getConfig().getString("chatchannel")).get().getTopic().equals("<:online:716052437848424558> Earth is online!"))) {
                MiniMick.getApi().getServerTextChannelById(getConfig().getString("chatchannel")).get().createUpdater().setTopic("<:online:716052437848424558> Earth is online!").update();
            }
        }, (20 * 60) * 10);

    }

    public void discordDisable() {
        MiniMick.getApi().getServerTextChannelById(getConfig().getString("chatchannel")).get().sendMessage(new EmbedBuilder()
                .setColor(Color.RED)
                .setDescription("<:offline:716052437688909825> Server is offline!")
        );
        MiniMick.getApi().getServerTextChannelById(getConfig().getString("chatchannel")).get().createUpdater().setTopic("<:offline:716052437688909825> Server is offline!").update();
    }

}