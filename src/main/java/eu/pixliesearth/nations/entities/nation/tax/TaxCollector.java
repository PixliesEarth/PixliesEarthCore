package eu.pixliesearth.nations.entities.nation.tax;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;

import java.util.UUID;

public class TaxCollector extends Thread {

    private final static Main INSTANCE = Main.getInstance();
    private boolean running = false;

    public TaxCollector() {

    }

    public void startThread() {
        running = true;
        start();
    }

    public long lastTimeCollected() {
        return INSTANCE.getConfig().getLong("tax.lastTimeCollected", System.currentTimeMillis());
    }

    public void run() {
        while (running) {
            try {
                tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void tick() {
        if (System.currentTimeMillis() - lastTimeCollected() > Timer.DAY) {
            INSTANCE.getConfig().set("tax.lastTimeCollected", System.currentTimeMillis());
            INSTANCE.saveConfig();
            collectTaxes();
        }
    }

    public void collectTaxes() {
        final long started = System.currentTimeMillis();
        Bukkit.broadcastMessage(Lang.NATION + "§7We are collecting taxes now...");
        for (Nation nation : NationManager.nations.values()) {
            if (!nation.getTaxSystem().isEnabled()) continue;
            for (String member : nation.getMembers()) {
                Profile profile = INSTANCE.getProfile(UUID.fromString(member));
                double amount = (profile.getBalance() / 100) * nation.getTaxSystem().getPercentage();
                profile.withdrawMoney(amount, "Taxation from " + nation.getName());
                nation.deposit(amount);
            }
        }
        Bukkit.broadcastMessage(Lang.NATION + "§7Finished collecting taxes, took us §b" + (System.currentTimeMillis() - started) + "§7ms");
    }

}
