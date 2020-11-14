package eu.pixliesearth;

import com.google.gson.Gson;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import eu.pixliesearth.core.commands.economy.BalanceCommand;
import eu.pixliesearth.core.commands.economy.CoinsCommand;
import eu.pixliesearth.core.commands.economy.PayCommand;
import eu.pixliesearth.core.commands.player.*;
import eu.pixliesearth.core.commands.util.*;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.listeners.CustomBlockListener;
import eu.pixliesearth.core.custom.listeners.TabListener;
import eu.pixliesearth.core.files.JSONFile;
import eu.pixliesearth.core.listener.*;
import eu.pixliesearth.core.modules.ChatSystem;
import eu.pixliesearth.core.modules.PrivateMessage;
import eu.pixliesearth.core.modules.ShopSystem;
import eu.pixliesearth.core.modules.WarpSystem;
import eu.pixliesearth.core.modules.economy.EconomySystem;
import eu.pixliesearth.core.modules.economy.VaultAPI;
import eu.pixliesearth.core.objects.Energy;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.boosts.DoubleExpBoost;
import eu.pixliesearth.core.scoreboard.ScoreboardAdapter;
import eu.pixliesearth.core.vendors.VendorItem;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.guns.commands.GunGiveCommand;
import eu.pixliesearth.guns.listeners.GunListener;
import eu.pixliesearth.lib.io.github.thatkawaiisam.assemble.Assemble;
import eu.pixliesearth.lib.io.github.thatkawaiisam.assemble.AssembleStyle;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.NationCommand;
import eu.pixliesearth.nations.commands.subcommand.nation.settlementsCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.*;
import eu.pixliesearth.nations.listener.MapClickListener;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.nations.managers.dynmap.DynmapEngine;
import eu.pixliesearth.utils.*;
import eu.pixliesearth.warsystem.*;
import eu.pixliesearth.warsystem.gulag.Gulag;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public final class Main extends JavaPlugin {

    private static @Getter Main instance;
    private static @Getter MongoCollection<Document> playerCollection;
    private static @Getter MongoCollection<Document> nationCollection;
    private static @Getter MongoCollection<Document> warCollection;
    private static @Getter VaultAPI economy;
    private static @Getter Assemble assemble = null;
    private static @Getter Scoreboard emptyScoreboard;
    private @Getter FileManager warpsCfg;
    private @Getter FileManager shopCfg;
    private @Getter FileManager dynmapCfg;
    private @Getter UtilLists utilLists;
    private @Getter DynmapEngine dynmapKernel;
    private @Getter NTop nationsTop;
    private @Getter @Setter boolean gulagActive = false;
    private @Getter LuckPerms luckPerms;
    private @Getter Gson gson;
    private @Getter CustomFeatureLoader loader;
    private @Getter Map<String, VendorItem> vendorItems;
    private @Getter FastConf fastConf;
    private @Getter MiniMick miniMick;
    private @Getter @Setter War currentWar;
    private @Getter UtilThread utilThread;
    //TODO LOAD GULAG
    private @Getter Gulag gulag;

    @Override
    public void onEnable() {
        instance = this;
        loader = new CustomFeatureLoader(this, "eu.pixliesearth.core.custom");
        fastConf = new FastConf(getConfig().getInt("max-claim-size", 3000));
        init();
    }

    @SuppressWarnings("resource")
	private void init() {

        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }

        gson = new Gson();

        utilLists = new UtilLists();
        
        registerCommands();
        registerEvents(Bukkit.getPluginManager());

        String uri = getConfig().getString("mongodb-connectionstring");
        if (uri == null) {
            getLogger().warning("Plugin can't start because MongoDB URI is missing.");
            Bukkit.getPluginManager().disablePlugin(instance);
            return;
        }
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
        playerCollection = mongoDatabase.getCollection("users");
        nationCollection = mongoDatabase.getCollection("nations");
        warCollection = mongoDatabase.getCollection("wars");

        MongoCursor<Document> cursor = warCollection.find().iterator();
        while (cursor.hasNext()) utilLists.wars.put(cursor.next().getString("id"), gson.fromJson(cursor.next().getString("json"), War.class));

        economy = new VaultAPI();
        getServer().getServicesManager().register(Economy.class, economy, this, ServicePriority.Normal);

        vendorItems = new HashMap<>();
        JSONFile vendorItemsFile = new JSONFile(getDataFolder().getAbsolutePath() + "/", "vendoritems");
        for (String s : vendorItemsFile.keySet())
            vendorItems.put(s, gson.fromJson(vendorItemsFile.get(s), VendorItem.class));

        warpsCfg = new FileManager(this, "warps", getDataFolder().getAbsolutePath());
        warpsCfg.save();

        shopCfg = new FileManager(this, "shop", getDataFolder().getAbsolutePath());
        shopCfg.save();

        dynmapCfg = new FileManager(this, "dynmap", getDataFolder().getAbsolutePath());
        dynmapCfg.save();

        saveDefaultConfig();

        // PROFILE & AFK SCHEDULER
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getConsoleSender().sendMessage("§7Backing up all profiles in the database.");
                for (Player player : getServer().getOnlinePlayers()) {
                    Profile profile = getProfile(player.getUniqueId());
                    profile.syncDiscordAndIngameRoles();
                    profile.backup();
                }
                Bukkit.getConsoleSender().sendMessage("§aDone.");
            }
        }.runTaskTimerAsynchronously(this, (20 * 60), (20 * 60));

        // NATION SCHEDULER
        new BukkitRunnable() {
            @Override
            public void run() {
                System.out.println("§aSaving all nations in the database...");
                for (Nation nation : NationManager.nations.values())
                    nation.backup();
                System.out.println("§aSaved all nations in the database.");
            }
        }.runTaskTimerAsynchronously(this, (20 * 60) * 16, (20 * 60) * 15);

        // ENERGY SCHEDULER
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!utilLists.afk.contains(player.getUniqueId())) {
                        Profile profile = getProfile(player.getUniqueId());
                        Energy.add(profile, 1D);
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, (20 * 60) * 60, (20 * 60) * 60);

        // TABLIST UPDATER
        new BukkitRunnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					TabListener.update(player);
				}
			}
        }.runTaskTimerAsynchronously(this, 1L, 1L);

        NationManager.init();

        //BungeeCord
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        miniMick = new MiniMick();
        miniMick.start();

        assemble = new Assemble(this, new ScoreboardAdapter());

        assemble.setTicks(2);

        assemble.setAssembleStyle(AssembleStyle.VIPER);

        NationChunk.init();

        emptyScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        //LANGUAGE STUFF
        saveResource("languages/LANG_DE.yml", true);
        saveResource("languages/LANG_ENG.yml", true);
        saveResource("languages/LANG_fr.yml", true);
        saveResource("languages/LANG_es.yml", true);
        saveResource("languages/LANG_nl.yml", true);
        saveResource("languages/LANG_SWE.yml", true);
        saveResource("languages/LANG_FA.yml", true);

        Lang.init();

        discordEnable();

        utilThread = new UtilThread();
        utilThread.start();
        new GulagThread().start();

        dynmapKernel = new DynmapEngine();
        dynmapKernel.onEnable();

        nationsTop = new NTop();
        // rest = new REST();
        // machineTask = new MachineTask();

        // MACHINES
        // machineTask.init();

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null)
            luckPerms = provider.getProvider();

        if (!NationManager.nations.containsKey("safezone")) {
            ItemStack flag = new ItemStack(Material.LIME_BANNER);
            BannerMeta meta = (BannerMeta) flag.getItemMeta();
            meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.GLOBE));
            flag.setItemMeta(meta);
            new Nation("safezone", "SafeZone", "You are safe here", Era.FUTURE.getName(), Ideology.NON_ALIGNED.name(), Religion.ATHEISM.name(), InventoryUtils.serialize(flag), 2020, 2020.0, "NONE", "#34eb71", "#28ad54", System.currentTimeMillis()+"", new HashMap<>(), NationFlag.defaultServerNations(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>()).create();
        }

        if (!NationManager.nations.containsKey("warzone")) {
            ItemStack flag = new ItemStack(Material.RED_BANNER);
            BannerMeta meta = (BannerMeta) flag.getItemMeta();
            meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.GLOBE));
            flag.setItemMeta(meta);
            new Nation("warzone", "WarZone", "Everyone can attack you here!", Era.FUTURE.getName(), Ideology.NON_ALIGNED.name(), Religion.ATHEISM.name(), InventoryUtils.serialize(flag), 2020, 2020.0, "NONE", "#e64135", "#78221c", System.currentTimeMillis()+"", new HashMap<>(), NationFlag.defaultServerNations(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>()).create();
        }
    }

    @SneakyThrows
    @Override
    public void onDisable() {
    	loader.save();
        discordDisable();
        // machineTask.stopThread();
        dynmapKernel.onDisable();
        getUtilLists().awaitingGulag1.clear();
        getUtilLists().awaitingGulag2.clear();
        for (Profile profile : utilLists.profiles.values())
            profile.backup();
        for (Nation nation : NationManager.nations.values())
            nation.backup();
        for (Block chest : utilLists.deathChests.keySet())
            chest.setType(Material.AIR);
        JSONFile vendorItemsFile = new JSONFile(getDataFolder().getAbsolutePath() + "/", "vendoritems");
        vendorItemsFile.clearFile();
        for (Map.Entry<String, VendorItem> entry : vendorItems.entrySet())
            vendorItemsFile.put(entry.getKey(), gson.toJson(entry.getValue()));
        vendorItemsFile.saveJsonToFile();
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
        getCommand("gungive").setExecutor(new GunGiveCommand());
        getCommand("gungive").setTabCompleter(new GunGiveCommand());
        getCommand("shop").setExecutor(new ShopSystem());
        getCommand("lobby").setExecutor(new LobbyCommand());
        getCommand("boost").setExecutor(new BoostCommand());
        getCommand("marry").setExecutor(new MarryCommand());
        getCommand("divorce").setExecutor(new DivorceCommand());
        getCommand("sudo").setExecutor(new SudoCommand());
        getCommand("tphere").setExecutor(new TpHereCommand());
        getCommand("family").setExecutor(new FamilyCommand());
        getCommand("woohoo").setExecutor(new WoohooCommand());
        getCommand("adopt").setExecutor(new AdoptCommand());
        getCommand("block").setExecutor(new BlockCommand());
        getCommand("nick").setExecutor(new NickCommand());
        getCommand("realname").setExecutor(new RealNameCommand());
        getCommand("skipgulag").setExecutor(new GulagSkipCommand());
        getCommand("smite").setExecutor(new SmiteCommand());
        getCommand("premium").setExecutor(new PremiumCommand());
        getCommand("gulagsetspawn").setExecutor(new GulagSetSpawn());
        getCommand("gulagsetspawn").setTabCompleter(new GulagSetSpawnTab());
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("invsee").setExecutor(new InvseeCommand());
    }

    private void registerEvents(PluginManager manager) {
        manager.registerEvents(new ChatSystem(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new LeaveListener(), this);
        manager.registerEvents(new MoveListener(), this);
        manager.registerEvents(new ItemInteractListener(), this);
        manager.registerEvents(new GunListener(this), this);
        manager.registerEvents(new PlayerCombatListener(), this);
        manager.registerEvents(new AchievementListener(), this);
        manager.registerEvents(new Restrictions(), this);
        manager.registerEvents(new DeathListener(), this);
        manager.registerEvents(new PlayerInteractListener(), this);
        manager.registerEvents(new AnvilListener(), this);
        manager.registerEvents(new BlockBreakListener(), this);
        manager.registerEvents(new MapClickListener(), this);
        manager.registerEvents(new settlementsCommand(), this);
        manager.registerEvents(new PlayerLoginListener(), this);
        manager.registerEvents(new CommandListener(), this);
        // manager.registerEvents(new GulagDeathListener(), this);
        manager.registerEvents(new GulagStartListener(), this);
        manager.registerEvents(new ProtectionManager(), this);
        manager.registerEvents(new DoubleExpBoost(), this);
        manager.registerEvents(new FlagListener(), this);
        manager.registerEvents(new CustomBlockListener(), this);
    }

    /**
     * @param uuid Uuid of the player
     * @return A profile object of the given playerUUID
     */
    public Profile getProfile(UUID uuid) {
       if (utilLists.profiles.get(uuid) == null)
            utilLists.profiles.put(uuid, Profile.get(uuid));
       return utilLists.profiles.get(uuid);
    }

    public void discordEnable() {
        for (Server server : MiniMick.getApi().getServers())
            if (!server.getIdAsString().equals("589958750866112512")) {
                try {
                    server.leave().get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        ServerTextChannel chatChannel = MiniMick.getApi().getServerTextChannelById(getConfig().getString("chatchannel")).get();
        chatChannel.sendMessage(new EmbedBuilder()
                .setColor(Color.green)
                .setDescription("<:online:716052437848424558> Server is online!"));
        chatChannel.createUpdater().setTopic("<:online:716052437848424558> Earth is online!").update();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!(chatChannel.getTopic().equals("<:online:716052437848424558> Earth is online!"))) {
                    chatChannel.createUpdater().setTopic("<:online:716052437848424558> Earth is online!").update();
                }
            }
        }.runTaskLaterAsynchronously(this, (20 * 60) * 10);

    }

    public void discordDisable() {
        ServerTextChannel chatChannel = MiniMick.getApi().getServerTextChannelById(getConfig().getString("chatchannel")).get();
        chatChannel.sendMessage(new EmbedBuilder()
                .setColor(Color.RED)
                .setDescription("<:offline:716052437688909825> Server is offline!")
        );
        chatChannel.createUpdater().setTopic("<:offline:716052437688909825> Server is offline!").update();
        MiniMick.getApi().disconnect();
    }

}