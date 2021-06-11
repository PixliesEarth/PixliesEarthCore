package eu.pixliesearth.vehicles;

import eu.pixliesearth.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.HashMap;
import java.util.UUID;

public class PixliesVehicle implements Listener {
    //static bit
    public static HashMap<Player, PixliesVehicle> map = new HashMap<Player, PixliesVehicle>();

    public static HashMap<Player, PixliesVehicle> getVehicleMap() {
        return map;
    }

    public static void registerVehicle(Player p, PixliesVehicle v) {
        map.put(p, v);
    }

    public static void unregisterVehicle(Player p, PixliesVehicle v) {
        map.remove(p, v);
    }

    public static PixliesVehicle getRegisteredVehicles(Player p) {
        return map.get(p);
    }

    //class bit
    public String name;
    public int health;
    public int maxhealth;
    private double fuel;
    private double maxfuel;
    public double speed;
    private Vector vec;
    private boolean spawned;
    private boolean locked;
    public UUID owner;
    private ArmorStand ds;
    private Player driver;
    private boolean allowshift;

    public PixliesVehicle(String name, int maxhealth, int maxfuel) {
        this.name = name;
        this.health = maxhealth;
        this.fuel = maxfuel;
        this.maxfuel = maxfuel;
        this.speed = 0.0;
        this.vec = new Vector();
        this.spawned = false;
        this.locked = false;
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    public boolean addFuel(int i) {
        if (this.fuel>=this.maxfuel) return false;
        if (this.fuel+i>=this.maxfuel) return false;
        this.fuel = this.fuel+i;
        return true;
    }

    public boolean removeFuel(int i) {
        if (this.fuel<=0) return false;
        if (this.fuel-i<=0) return false;
        this.fuel = this.fuel-i;
        return true;
    }

    public double getFuel() {
        return this.fuel;
    }

    public double getMaxFuel() {
        return this.maxfuel;
    }

    public void sitInDriverSeat(Player p) {
        if (this.spawned) {
            if (!this.isLocked()) {
                this.ds.addPassenger(p);
                this.driver = p;
            } else if (p.getUniqueId().equals(owner)) {
                this.ds.addPassenger(p);
                this.driver = p;
            }
        }
    }

    public void spawnAt(World w, Location loc, Player p) {
        if (this.spawned) return;
        this.ds = (ArmorStand) w.spawnEntity(loc, EntityType.ARMOR_STAND);
        this.ds.setHeadPose(new EulerAngle(0, ds.getLocation().getYaw() * Math.PI / 180, 0));
        this.ds.setVisible(true);
        this.ds.setCollidable(true);
        this.ds.setSmall(true);
        this.ds.setSilent(true);
        this.ds.setCustomName(p.getUniqueId()+this.name);
        this.ds.setCustomNameVisible(false);
        this.owner = p.getUniqueId();
        this.vec = new Vector();
        this.allowshift = true;
        registerVehicle(p, this);
    }

    public void updateMovement(Input input, Player p) {
        if (this.fuel==0) return;
        String i = input.getDirection();
        if (i.contains("w")) {
            //
        }
        if (i.contains("a")) {
            //
        }
        if (i.contains("s")) {
            //
        }
        if (i.contains("d")) {
            //
        }
        if (i.contains("u")) {
            //
        }
        if (i.contains("v")) {
            //
        }
        addToLocation(this.vec);
    }

    static class Input {

        public String direction = "";

        public Input(boolean w, boolean a, boolean s, boolean d, boolean space, boolean down) {
            if (w) direction +="w";
            if (s) direction += "s";
            if (a) direction += "a";
            if (d) direction += "d";
            if (space) direction += "u";
            if (down) direction += "v";
        }

        public String getDirection() {
            return this.direction;
        }

    }

    public Location getLocation() {
        return this.ds.getLocation();
    }

    public void setLocation(double x, double y, double z) {
        this.ds.getLocation().setX(x);
        this.ds.getLocation().setY(y);
        this.ds.getLocation().setZ(z);
    }

    public void addToLocation(Location vec) {
        this.ds.getLocation().add(vec);
    }

    public void addToLocation(Vector vec) {
        this.ds.getLocation().add(vec);
    }

    public void despawn() {
        if (this.spawned) {
            this.ds.remove();
            unregisterVehicle(Bukkit.getOfflinePlayer(owner).getPlayer(), this);
            this.owner = null;
            this.vec = null;
        }
    }

    public void setLocked(boolean lock) {
        this.locked = lock;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setOwner(Player p) {
        this.owner = p.getUniqueId();
    }

    public String getName() {
        return this.name;
    }

    public String getCustomName() {
        if (this.spawned) return this.owner+this.name; else return "Error:NotSpawned";
    }

    public boolean isSpawned() {
        return this.spawned;
    }

    public Player getDriver() {
        return this.driver;
    }

    public boolean hasDriver() {
        if (this.driver==null) return false; else return true;
    }

    public void allowShift(boolean shift) {
        this.allowshift = shift;
    }

    public boolean canShift() {
        return !this.allowshift;
    }

    public void dismountDriver() {
        if (hasDriver()) {
            this.ds.removePassenger(this.driver);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent e) {
        if (!(e.getPlayer().getInventory().getItemInMainHand()==null)) return;
        if (e.getRightClicked().getType().equals(EntityType.ARMOR_STAND) && isSpawned() && e.getRightClicked().getCustomName().equalsIgnoreCase(getCustomName())) {

            if (isLocked()) {
                e.getPlayer().sendMessage("This vehicle is locked!");
            } else if (hasDriver()) {
                e.getPlayer().sendMessage("This vehicle is occupied");
            } else {
                if (e.getPlayer().isSneaking()) {
                    //some fuel thing
                    e.getPlayer().sendMessage("The fuel inventory is not added yet!");
                } else {
                    sitInDriverSeat(e.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onShift(EntityDismountEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Entity d = e.getDismounted();
        if (d instanceof ArmorStand && d.getCustomName().equalsIgnoreCase(getCustomName()) && isSpawned()) {
            if (!canShift()) {
                sitInDriverSeat((Player)e.getEntity());
                updateMovement(new Input(false, false, false, false, false, true), (Player)e.getEntity());
            } else {
                dismountDriver();
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (getDriver() == null) return;
        if (!getDriver().getUniqueId().equals(event.getPlayer().getUniqueId())) return;
        ds.setVelocity(event.getPlayer().getVelocity().clone());
        this.vec = event.getPlayer().getVelocity().clone();
    }

}
