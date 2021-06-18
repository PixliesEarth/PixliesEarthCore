package eu.pixliesearth;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import eu.pixliesearth.api.REST;
import eu.pixliesearth.core.custom.commands.*;
import eu.pixliesearth.core.custom.items.ItemBlockInspector;
import eu.pixliesearth.core.custom.items.ItemEnergyInspector;
import eu.pixliesearth.core.custom.listeners.*;
import eu.pixliesearth.core.custom.recipes.EnergyMachineBase;
import eu.pixliesearth.core.custom.recipes.SteelDust;
import eu.pixliesearth.core.custom.skills.SkillHandler;
import eu.pixliesearth.core.economy.BalTopThread;
import eu.pixliesearth.core.objects.PixliesCalendar;
import eu.pixliesearth.core.vendors.Vendor;
import eu.pixliesearth.discord.MiniMickServerConfig;
import io.sentry.Sentry;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.command.CommandSender;
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
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import eu.pixliesearth.core.commands.player.BlockCommand;
import eu.pixliesearth.core.commands.player.BoostCommand;
import eu.pixliesearth.core.commands.player.CraftCommand;
import eu.pixliesearth.core.commands.player.CringeCommand;
import eu.pixliesearth.core.commands.player.EnderchestCommand;
import eu.pixliesearth.core.commands.player.FeedCommand;
import eu.pixliesearth.core.commands.player.FlyCommand;
import eu.pixliesearth.core.commands.player.FlySpeedCommand;
import eu.pixliesearth.core.commands.player.GamemodeAdventureCommand;
import eu.pixliesearth.core.commands.player.GamemodeCreativeCommand;
import eu.pixliesearth.core.commands.player.GamemodeSpectatorCommand;
import eu.pixliesearth.core.commands.player.GamemodeSurvivalCommand;
import eu.pixliesearth.core.commands.player.HealCommand;
import eu.pixliesearth.core.commands.player.HomeCommand;
import eu.pixliesearth.core.commands.player.LinkCommand;
import eu.pixliesearth.core.commands.player.LobbyCommand;
import eu.pixliesearth.core.commands.player.NickCommand;
import eu.pixliesearth.core.commands.player.PremiumCommand;
import eu.pixliesearth.core.commands.player.ProfileCommand;
import eu.pixliesearth.core.commands.player.RealNameCommand;
import eu.pixliesearth.core.commands.player.SkullCommand;
import eu.pixliesearth.core.commands.player.SmiteCommand;
import eu.pixliesearth.core.commands.player.StatsCommand;
import eu.pixliesearth.core.commands.player.SudoCommand;
import eu.pixliesearth.core.commands.player.SuicideCommand;
import eu.pixliesearth.core.commands.player.TpHereCommand;
import eu.pixliesearth.core.commands.player.VanishCommand;
import eu.pixliesearth.core.commands.player.WalkSpeedCommand;
import eu.pixliesearth.core.commands.util.BackupCommand;
import eu.pixliesearth.core.commands.util.BroadcastCommand;
import eu.pixliesearth.core.commands.util.ChatCommand;
import eu.pixliesearth.core.commands.util.InvseeCommand;
import eu.pixliesearth.core.commands.util.ModulesCommand;
import eu.pixliesearth.core.commands.util.SeenCommand;
import eu.pixliesearth.core.commands.util.StaffCommand;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.files.JSONFile;
import eu.pixliesearth.core.listener.AchievementListener;
import eu.pixliesearth.core.listener.AnvilListener;
import eu.pixliesearth.core.listener.BlockBreakListener;
import eu.pixliesearth.core.listener.DeathListener;
import eu.pixliesearth.core.listener.ItemInteractListener;
import eu.pixliesearth.core.listener.JoinListener;
import eu.pixliesearth.core.listener.LeaveListener;
import eu.pixliesearth.core.listener.MoveListener;
import eu.pixliesearth.core.listener.PlayerCombatListener;
import eu.pixliesearth.core.listener.PlayerInteractListener;
import eu.pixliesearth.core.listener.PlayerLoginListener;
import eu.pixliesearth.core.listener.ProtectionManager;
import eu.pixliesearth.core.listener.Restrictions;
import eu.pixliesearth.core.modules.ChatSystem;
import eu.pixliesearth.core.modules.PrivateMessage;
import eu.pixliesearth.core.modules.WarpSystem;
import eu.pixliesearth.core.modules.economy.VaultAPI;
import eu.pixliesearth.core.objects.Energy;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.boosts.DoubleExpBoost;
import eu.pixliesearth.core.scoreboard.ScoreboardAdapter;
import eu.pixliesearth.core.vendors.VendorItem;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.guns.guns.AK47;
import eu.pixliesearth.guns.guns.K98K;
import eu.pixliesearth.guns.guns.M16;
import eu.pixliesearth.guns.guns.M1911;
import eu.pixliesearth.guns.guns.MP5;
import eu.pixliesearth.guns.guns.RPG7;
import eu.pixliesearth.guns.guns.Slingshot;
import eu.pixliesearth.guns.guns.Uzi;
import eu.pixliesearth.lib.io.github.thatkawaiisam.assemble.Assemble;
import eu.pixliesearth.lib.io.github.thatkawaiisam.assemble.AssembleStyle;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.NationCommand;
import eu.pixliesearth.nations.commands.subcommand.nation.settlementsCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Era;
import eu.pixliesearth.nations.entities.nation.FlagListener;
import eu.pixliesearth.nations.entities.nation.Ideology;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.NationFlag;
import eu.pixliesearth.nations.entities.nation.Religion;
import eu.pixliesearth.nations.listener.MapClickListener;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.nations.managers.dynmap.DynmapEngine;
import eu.pixliesearth.utils.FastConf;
import eu.pixliesearth.utils.FileManager;
import eu.pixliesearth.utils.InventoryUtils;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import eu.pixliesearth.utils.UtilLists;
import eu.pixliesearth.utils.UtilThread;
import eu.pixliesearth.warsystem.War;
import eu.pixliesearth.warsystem.WarThread;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;

public final class Main extends JavaPlugin {

    private static @Getter Main instance;
    private static @Getter MongoCollection<Document> playerCollection;
    private static @Getter MongoCollection<Document> nationCollection;
    private static @Getter MongoCollection<Document> warCollection;
    private static @Getter MongoCollection<Document> punishmentCollection;
    private static @Getter VaultAPI economy;
    private static @Getter Assemble assemble = null;
    private static @Getter Scoreboard emptyScoreboard;
    private @Getter FileManager warpsCfg;
    private @Getter FileManager shopCfg;
    private @Getter FileManager dynmapCfg;
    private @Getter FileManager calendarCfg;
    private @Getter UtilLists utilLists;
    private @Getter DynmapEngine dynmapKernel;
    private @Getter LuckPerms luckPerms;
    private @Getter Gson gson;
    private @Getter CustomFeatureLoader loader;
    private @Getter Map<String, VendorItem> vendorItems;
    private @Getter FastConf fastConf;
    private @Getter MiniMick miniMick;
    private @Getter @Setter War currentWar;
    private @Getter UtilThread utilThread;
    private @Getter BalTopThread baltopThread;
    private @Getter final boolean warEnabled = true;
    private @Getter REST rest;
    private @Getter final SkillHandler skillHandler = SkillHandler.getSkillHandler();
    private @Getter boolean testServer;
    private @Getter PixliesCalendar calendar;
    private @Getter Vendor vendor;

    @Override
    public void onEnable() {
        Sentry.init(options -> options.setDsn("https://a52eb2ffd8b94548aab2dd7dc1c5d3c8@o518018.ingest.sentry.io/5626447"));

        instance = this;
        testServer = getConfig().getBoolean("test-server", false);
        loader = new CustomFeatureLoader(this, "eu.pixliesearth.core.custom");
        if (warEnabled) loader.loadCommand(new WarCommand());
        loadStuffThatDoesntLoadBecauseBradsReflectionIsShit();
        fastConf = new FastConf(getConfig().getInt("max-claim-size", 5200), getConfig().getLocation("spawn-location"));
        init();
    }

    private void loadStuffThatDoesntLoadBecauseBradsReflectionIsShit() {
        loader.loadCommand(new SkillCommand());
        loader.loadCommand(new GiveCustomItems());
        loader.loadCommand(new SusCommand());
        loader.loadCommand(new ReplyCommand());
        loader.loadListener(new CustomMoneyPickupListener());
        loader.loadListener(new SuicideVestListener());
        loader.loadListener(new PoliticalPowerListener());
        loader.loadListener(new CustomCraftingListener());
        loader.loadListener(new PixlieFunGUIListener());
        loader.loadCustomItem(new ItemEnergyInspector());
        loader.loadCustomItem(new ItemBlockInspector());
        loader.loadCustomRecipe(new EnergyMachineBase());
        loader.loadCustomRecipe(new SteelDust());
    }

    @SuppressWarnings("resource")
	private void init() {
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
        playerCollection = mongoDatabase.getCollection(getConfig().getString("users-collection", "users"));
        nationCollection = mongoDatabase.getCollection(getConfig().getString("nations-collection", "nations"));
        warCollection = mongoDatabase.getCollection(getConfig().getString("wars-collection", "wars"));
        punishmentCollection = mongoDatabase.getCollection(getConfig().getString("punishment-collection", "punishments"));

        for (Document doc : warCollection.find())
            utilLists.wars.put(doc.getString("id"), gson.fromJson(doc.getString("json"), War.class));

        economy = new VaultAPI();
        getServer().getServicesManager().register(Economy.class, economy, this, ServicePriority.Normal);

        vendorItems = new HashMap<>();
        JSONFile vendorItemsFile = new JSONFile(getDataFolder().getAbsolutePath() + "/", "vendoritems");
        for (String s : vendorItemsFile.keySet())
            if (!s.equalsIgnoreCase("balance"))
                vendorItems.put(s, gson.fromJson(vendorItemsFile.get(s), VendorItem.class));

        warpsCfg = new FileManager(this, "warps", getDataFolder().getAbsolutePath());
        warpsCfg.save();

        shopCfg = new FileManager(this, "shop", getDataFolder().getAbsolutePath());
        shopCfg.save();

        dynmapCfg = new FileManager(this, "dynmap", getDataFolder().getAbsolutePath());
        dynmapCfg.save();

        calendarCfg = new FileManager(this, "calendar", getDataFolder().getAbsolutePath());
        calendarCfg.save();

        String[] date = calendarCfg.getConfiguration().getString("date", "0/0/0").split("/");
        calendar = new PixliesCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        calendar.startRunner();

        saveDefaultConfig();

        // PROFILE & AFK SCHEDULER
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getConsoleSender().sendMessage("§7Backing up all profiles in the database.");
                for (Player player : getServer().getOnlinePlayers()) {
                    // if (CitizensAPI.getNPCRegistry().isNPC(player)) continue;
                    try {
                        Profile profile = getProfile(player.getUniqueId());
                        profile.syncDiscordAndIngameRoles();
                        profile.backup();
                    } catch (Exception e) {
                        getLogger().log(Level.SEVERE, "Couldn't backup " + player.getName() + "'s profile on to the database.");
                        e.printStackTrace();
                    }
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
                    try {
                        nation.backup();
                    } catch (Exception e) {
                        getLogger().log(Level.SEVERE, "Couldn't backup " + nation.getName() + " on to the database.");
                        e.printStackTrace();
                    }
                System.out.println("§aSaved all nations in the database.");
            }
        }.runTaskTimerAsynchronously(this, (20 * 60) * 16, (20 * 60) * 15);

        // PP SCHEDULER
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : getServer().getOnlinePlayers()) {
                    // if (CitizensAPI.getNPCRegistry().isNPC(player)) continue;
                    Profile profile = getProfile(player.getUniqueId());
                    if (!profile.isInNation()) continue;
                    Nation nation = profile.getCurrentNation();
                    nation.setXpPoints(nation.getXpPoints() + 0.25);
                    player.sendActionBar("§a+0.25 §7Political-Power");
                }
            }
        }.runTaskTimerAsynchronously(this, Timer.DAY, Timer.DAY);

        // ENERGY SCHEDULER
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Profile profile = getProfile(player.getUniqueId());
                    if (profile.isInNation()) {
                        Energy.add(profile, 0.1 * profile.getCurrentNation().getCurrentEra().getNumber());
                    } else {
                        Energy.add(profile, 0.1);
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, (20 * 60) * 5, (20 * 60) * 5);

        // TABLIST UPDATER
        new BukkitRunnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					TabListener.update(player);
				}
			}
        }.runTaskTimerAsynchronously(this, 1L, 1L);

        // WAR UPDATER
        new BukkitRunnable() {
            @Override
            public void run() {
                for (War war : utilLists.wars.values())
                    war.backup();
            }
        }.runTaskTimerAsynchronously(this, 0L, (20 * 60) * 5);

        NationManager.init();

        //BungeeCord
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        miniMick = new MiniMick();
        miniMick.start();

        JSONFile miniMickConfigs = new JSONFile(getDataFolder().getAbsolutePath() + "/", "miniMickConfigs");
        for (String s : miniMickConfigs.keySet())
            MiniMick.getConfigs().put(s, gson.fromJson(miniMickConfigs.get(s), MiniMickServerConfig.class));

        assemble = new Assemble(this, new ScoreboardAdapter());

        assemble.setTicks(1);

        assemble.setAssembleStyle(AssembleStyle.MODERN);

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
        saveResource("languages/LANG_PT.yml", true);

        Lang.init();

        discordEnable();

        utilThread = new UtilThread();
        utilThread.start();
        if (warEnabled) new WarThread().start();

        dynmapKernel = new DynmapEngine();
        dynmapKernel.onEnable();

        // nationsTop = new NTop();
        if (!testServer) rest = new REST();
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
            new Nation("safezone", "SafeZone", "You are safe here", Era.FUTURE.getName(), Ideology.TRIBE.name(), Religion.ATHEISM.name(), InventoryUtils.serialize(flag), 2020, 2020.0, "NONE", "#34eb71", "#28ad54", System.currentTimeMillis()+"", "NONE", new HashMap<>(), NationFlag.defaultServerNations(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()).create();
        }

        if (!NationManager.nations.containsKey("warzone")) {
            ItemStack flag = new ItemStack(Material.RED_BANNER);
            BannerMeta meta = (BannerMeta) flag.getItemMeta();
            meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.GLOBE));
            flag.setItemMeta(meta);
            new Nation("warzone", "WarZone", "Everyone can attack you here!", Era.FUTURE.getName(), Ideology.TRIBE.name(), Religion.ATHEISM.name(), InventoryUtils.serialize(flag), 2020, 2020.0, "NONE","#e64135", "#78221c", System.currentTimeMillis()+"", "NONE", new HashMap<>(), NationFlag.defaultServerNations(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()).create();
        }

        loader.loadCustomItem(new AK47());
    	loader.loadCustomItem(new K98K());
    	loader.loadCustomItem(new M16());
    	loader.loadCustomItem(new M1911());
    	loader.loadCustomItem(new MP5());
    	loader.loadCustomItem(new Slingshot());
    	loader.loadCustomItem(new Uzi());
    	loader.loadCustomItem(new RPG7());
    	loader.loadListener(new CustomMobListener());
    	Bukkit.getPluginManager().registerEvents(new VendorListener(), this);

    	if (!vendorItemsFile.containsKey("balance")) vendorItemsFile.put("balance", 50.0);

    	vendor = new Vendor("§bJohn the Vendor", "§bJohn the Vendor", vendorItemsFile.getAsJsonElement("balance").getAsDouble(),
                new ItemStack(Material.PRISMARINE),
                new ItemStack(Material.PRISMARINE_BRICKS),
                new ItemStack(Material.DARK_PRISMARINE),
                new ItemStack(Material.QUARTZ_BLOCK),
                new ItemStack(Material.SMOOTH_QUARTZ),
                new ItemStack(Material.BRICKS),
                new ItemStack(Material.SPONGE),
                new ItemStack(Material.SEA_LANTERN),
                new ItemStack(Material.STONE),
                new ItemStack(Material.ANDESITE),
                new ItemStack(Material.DIORITE),
                new ItemStack(Material.GRANITE),
                new ItemStack(Material.SANDSTONE),
                new ItemStack(Material.GRAVEL),
                new ItemStack(Material.TERRACOTTA),
                new ItemStack(Material.BLACK_CONCRETE),
                new ItemStack(Material.WHITE_CONCRETE),
                new ItemStack(Material.GRAY_CONCRETE),
                new ItemStack(Material.YELLOW_CONCRETE),
                new ItemStack(Material.DIAMOND)
                );

        baltopThread = new BalTopThread();
        baltopThread.start();
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        calendar.stopRunner();
    	loader.save();
        discordDisable();
        dynmapKernel.onDisable();
        for (Profile profile : utilLists.profiles.values())
            profile.backup();
        for (Nation nation : NationManager.nations.values())
            nation.backup();
        for (Block chest : utilLists.deathChests.keySet())
            chest.setType(Material.AIR);
        for (War war : utilLists.wars.values())
            war.backup();
        JSONFile vendorItemsFile = new JSONFile(getDataFolder().getAbsolutePath() + "/", "vendoritems");
        vendorItemsFile.clearFile();
        for (Map.Entry<String, VendorItem> entry : vendorItems.entrySet())
            vendorItemsFile.put(entry.getKey(), gson.toJson(entry.getValue()));
        vendorItemsFile.put("balance", vendor.getBalance());
        vendorItemsFile.saveJsonToFile();

        JSONFile miniMickConfigs = new JSONFile(getDataFolder().getAbsolutePath() + "/", "miniMickConfigs");
        miniMickConfigs.clearFile();
        for (Map.Entry<String, MiniMickServerConfig> entry: MiniMick.getConfigs().entrySet())
            miniMickConfigs.put(entry.getKey(), gson.toJsonTree(entry.getValue()));
        miniMickConfigs.saveJsonToFile();
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
        // getCommand("pay").setExecutor(new PayCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        // getCommand("tpa").setExecutor(new TpaCommand());
        // getCommand("tpaccept").setExecutor(new TpacceptCommand());
        getCommand("skull").setExecutor(new SkullCommand());
        getCommand("walkspeed").setExecutor(new WalkSpeedCommand());
        getCommand("craft").setExecutor(new CraftCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        // getCommand("shop").setExecutor(new ShopSystem());
        getCommand("lobby").setExecutor(new LobbyCommand());
        getCommand("boost").setExecutor(new BoostCommand());
        // getCommand("marry").setExecutor(new MarryCommand());
        // getCommand("divorce").setExecutor(new DivorceCommand());
        getCommand("sudo").setExecutor(new SudoCommand());
        getCommand("tphere").setExecutor(new TpHereCommand());
        // getCommand("family").setExecutor(new FamilyCommand());
        // getCommand("woohoo").setExecutor(new WoohooCommand());
        // getCommand("adopt").setExecutor(new AdoptCommand());
        getCommand("block").setExecutor(new BlockCommand());
        getCommand("nick").setExecutor(new NickCommand());
        getCommand("realname").setExecutor(new RealNameCommand());
        getCommand("smite").setExecutor(new SmiteCommand());
        getCommand("premium").setExecutor(new PremiumCommand());
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("cringe").setExecutor(new CringeCommand());
    }

    private void registerEvents(PluginManager manager) {
        manager.registerEvents(new ChatSystem(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new LeaveListener(), this);
        manager.registerEvents(new MoveListener(), this);
        manager.registerEvents(new ItemInteractListener(), this);
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

    public boolean isStaff(CommandSender sender) {
        if (!(sender instanceof Player)) return true;
        return getProfile(((Player)sender).getUniqueId()).isStaff();
    }

}