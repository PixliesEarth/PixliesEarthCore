package eu.pixliesearth;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import eu.pixliesearth.api.REST;
import eu.pixliesearth.core.commands.economy.BalanceCommand;
import eu.pixliesearth.core.commands.economy.CoinsCommand;
import eu.pixliesearth.core.commands.economy.PayCommand;
import eu.pixliesearth.core.commands.player.*;
import eu.pixliesearth.core.commands.util.*;
import eu.pixliesearth.core.customitems.commands.CiGiveCommand;
import eu.pixliesearth.core.customitems.listeners.CIEntityDamageByEntityListener;
import eu.pixliesearth.core.customitems.listeners.ItemsInteractEvent;
import eu.pixliesearth.core.customitems.listeners.SlingshotListener;
import eu.pixliesearth.core.guns.commands.GunGive;
import eu.pixliesearth.core.guns.listeners.GunListener;
import eu.pixliesearth.core.listener.*;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.core.machines.cargo.CargoListener;
import eu.pixliesearth.core.machines.carpentrymill.CarpentryMillListener;
import eu.pixliesearth.core.machines.MachineTask;
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
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.lib.io.github.thatkawaiisam.assemble.Assemble;
import eu.pixliesearth.lib.io.github.thatkawaiisam.assemble.AssembleStyle;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.NationCommand;
import eu.pixliesearth.nations.commands.subcommand.nation.settlementsCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.NTop;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.listener.MapClickListener;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.nations.managers.dynmap.DynmapEngine;
import eu.pixliesearth.utils.*;
import eu.pixliesearth.warsystem.*;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public final class Main extends JavaPlugin {

    private static @Getter Main instance;
    private static @Getter MongoCollection<Document> playerCollection;
    private static @Getter MongoCollection<Document> nationCollection;
    private static @Getter VaultAPI economy;
    private static @Getter Assemble assemble = null;
    private static @Getter Scoreboard emptyScoreboard;
    private @Getter FileManager warpsCfg;
    private @Getter FileManager shopCfg;
    private @Getter FileManager dynmapCfg;
    private @Getter UtilLists utilLists;
    private @Getter DynmapEngine dynmapKernel;
    private @Getter NTop nationsTop;
    private @Getter REST rest;
    public boolean gulagActive = false;
    private @Getter MachineTask machineTask;
    private @Getter FileManager flags;
    private @Getter LuckPerms luckPerms;

    @Override
    public void onEnable() {
        instance = this;
        init();
    }

    private void init() {

        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }

        utilLists = new UtilLists();

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

        dynmapCfg = new FileManager(this, "dynmap", getDataFolder().getAbsolutePath());
        dynmapCfg.save();

        flags = new FileManager(this, "flags", getDataFolder().getAbsolutePath());
        flags.save();

        saveDefaultConfig();

        // PROFILE & AFK SCHEDULER
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            for (UUID uuid : utilLists.locationMap.keySet()) {
                if (utilLists.locationMap.get(uuid).getLocation() == Bukkit.getPlayer(uuid).getLocation()) {
                    AfkMap map = utilLists.locationMap.get(uuid);
                    map.setMinutes(map.getMinutes() + 1);
                    utilLists.locationMap.put(uuid, map);
                } else {
                    utilLists.locationMap.get(uuid).setMinutes(0);
                }
                if (utilLists.locationMap.get(uuid).getMinutes() == getConfig().getInt("afktime", 15)) {
                    utilLists.afk.add(uuid);
                    Bukkit.broadcastMessage("§8Player §7" + Bukkit.getPlayer(uuid).getDisplayName() + " §8is now AFK.");
                }
            }
            Bukkit.getConsoleSender().sendMessage("§7Backing up all profiles in the database.");
            for (Player player : getServer().getOnlinePlayers()) {
                Profile profile = getProfile(player.getUniqueId());
                if (!utilLists.afk.contains(UUID.fromString(profile.getUniqueId())))
                    profile.setPlayTime(profile.getPlayTime() + 1);
                profile.syncDiscordAndIngameRoles();
                profile.save();
            }
            Bukkit.getConsoleSender().sendMessage("§aDone.");
        }, 20 * 60, (20 * 60) * 5);

        // NATION SCHEDULER
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            System.out.println("§aSaving all nations in the database...");
            for (Nation nation : NationManager.nations.values())
                nation.backup();
            System.out.println("§aSaved all nations in the database.");
        }, (20 * 60) * 15, (20 * 60) * 15);

        // ENERGY SCHEDULER
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!utilLists.afk.contains(player.getUniqueId())) {
                    Profile profile = getProfile(player.getUniqueId());
                    Energy.add(profile, 1D);
                }
            }
        }, (20 * 60) * 60, (20 * 60) * 60);

        // MACHINES
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            for (Machine machine : utilLists.machines.values()) {
                try {
                    machine.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, (20 * 60) * 5, (20 * 60) * 5);

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

        //LANGUAGE STUFF
        saveResource("languages/LANG_DE.yml", true);
        saveResource("languages/LANG_ENG.yml", true);
        saveResource("languages/LANG_fr.yml", true);
        saveResource("languages/LANG_es.yml", true);
        saveResource("languages/LANG_nl.yml", true);

        Lang.init();

        discordEnable();

        new UtilThread().start();
        new GulagThread().start();

        dynmapKernel = new DynmapEngine();
        dynmapKernel.onEnable();

        nationsTop = new NTop();
        rest = new REST();
        machineTask = new MachineTask();

        // MACHINES
        machineTask.init();

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null)
            luckPerms = provider.getProvider();

    }

    @Override
    public void onDisable() {
        discordDisable();
        machineTask.stop();
        dynmapKernel.onDisable();
        getUtilLists().awaitingGulag1.clear();
        getUtilLists().awaitingGulag2.clear();
        for (Player player : Bukkit.getOnlinePlayers())
            getProfile(player.getUniqueId()).backup();
        for (Nation nation : NationManager.nations.values())
            nation.backup();
        for (Block chest : utilLists.deathChests.keySet())
            chest.setType(Material.AIR);
        for (Machine machine : utilLists.machines.values()) {
            try {
                machine.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        getCommand("marry").setExecutor(new MarryCommand());
        getCommand("divorce").setExecutor(new DivorceCommand());
        getCommand("sudo").setExecutor(new SudoCommand());
        getCommand("tphere").setExecutor(new TpHereCommand());
        getCommand("family").setExecutor(new FamilyCommand());
        getCommand("sex").setExecutor(new SexCommand());
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
    }

    private void registerEvents(PluginManager manager) {
        manager.registerEvents(new ChatSystem(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new LeaveListener(), this);
        manager.registerEvents(new MoveListener(), this);
        manager.registerEvents(new ItemInteractListener(), this);
        manager.registerEvents(new GunListener(this), this);
        manager.registerEvents(new PlayerCombatListener(), this);
        //TODO manager.registerEvents(new PacketListener(), this);
        manager.registerEvents(new AchievementListener(), this);
        //THIS IS NOT ITEMINTERACTLISTENER DONT DELETE
        manager.registerEvents(new ItemsInteractEvent(), this);
        manager.registerEvents(new SlingshotListener(), this);
        manager.registerEvents(new Restrictions(), this);
        manager.registerEvents(new DeathListener(), this);
        manager.registerEvents(new PlayerInteractListener(), this);
        manager.registerEvents(new AnvilListener(), this);
        manager.registerEvents(new BlockBreakListener(), this);
        manager.registerEvents(new CIEntityDamageByEntityListener(), this);
        manager.registerEvents(new EntitySpawnListener(), this);
        manager.registerEvents(new MapClickListener(), this);
        manager.registerEvents(new settlementsCommand(), this);
        manager.registerEvents(new PlayerLoginListener(), this);
        manager.registerEvents(new CommandListener(), this);
        manager.registerEvents(new GulagDeathListener(), this);
        manager.registerEvents(new GulagStartListener(), this);
        manager.registerEvents(new ProtectionListener(), this);
        manager.registerEvents(new DoubleExpBoost(), this);
        manager.registerEvents(new CarpentryMillListener(), this);
        manager.registerEvents(new CargoListener(), this);
    }

    /**
     * @param uuid Uuid of the player
     * @return A profile object of the given playerUUID
     */
    public Profile getProfile(UUID uuid) {
        if (utilLists.profiles.get(uuid) == null) {
            utilLists.profiles.put(uuid, Profile.get(uuid));
            return utilLists.profiles.get(uuid);
        } else {
            return utilLists.profiles.get(uuid);
        }
    }

    public void discordEnable() {
        for (Server server : MiniMick.getApi().getServers())
            if (!server.getIdAsString().equals("589958750866112512")) {
                try {
                    server.leave().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
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
        MiniMick.getApi().disconnect();
    }
    public boolean isGulagActive(){
        return gulagActive;
    }

}