package eu.pixliesearth.core.modules.economy;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;

import java.util.List;

public class VaultAPI extends AbstractEconomy {

    @Override
    public boolean isEnabled() {
        return Main.getInstance() != null;
    }

    @Override
    public String getName() {
        return "PixliesEarthCore";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return String.valueOf(amount);
    }

    @Override
    public String currencyNamePlural() {
        return "";
    }

    @Override
    public String currencyNameSingular() {
        return "";
    }

    @Override
    public boolean hasAccount(String playerName) {
        return Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName)) != null;
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName)) != null;
    }

    @Override
    public double getBalance(String playerName) {
        return Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName)).getBalance();
    }

    @Override
    public double getBalance(String playerName, String world) {
        return Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName)).getBalance();
    }

    @Override
    public boolean has(String playerName, double amount) {
        return Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName)).getBalance() - amount >= 0;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName)).getBalance() - amount >= 0;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        if (!hasAccount(playerName))
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The player does not have an account!");
        if (amount < 0)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Amount is less than zero");
        if (!has(playerName, amount))
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player does not have the money.");
        Profile profile = Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName));
        profile.setBalance(getBalance(playerName) - amount);
        profile.getRececipts().add(Receipt.create(amount, true));
        profile.backup();
        return new EconomyResponse(amount, 0.0D, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        if (!hasAccount(playerName))
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The player does not have an account!");
        if (amount < 0)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Amount is less than zero");
        if (!has(playerName, amount))
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Player does not have the money.");
        Profile profile = Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName));
        profile.setBalance(getBalance(playerName) - amount);
        profile.getRececipts().add(Receipt.create(amount, true));
        profile.backup();
        return new EconomyResponse(amount, 0.0D, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        if (!hasAccount(playerName))
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The player does not have an account!");
        if (amount < 0)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Amount is less than zero");
        if (amount < Main.getInstance().getConfig().getDouble("modules.economy.min-amount"))
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Amount is less than the min. deposit amount.");
        Profile profile = Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName));
        profile.setBalance(getBalance(playerName) + amount);
        profile.getRececipts().add(Receipt.create(amount, false));
        profile.backup();
        return new EconomyResponse(amount, 0.0D, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        if (!hasAccount(playerName))
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The player does not have an account!");
        if (amount < 0)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Amount is less than zero");
        if (amount < Main.getInstance().getConfig().getDouble("modules.economy.min-amount"))
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Amount is less than the min. deposit amount.");
        Profile profile = Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName));
        profile.setBalance(getBalance(playerName) + amount);
        profile.getRececipts().add(Receipt.create(amount, false));
        profile.backup();
        return new EconomyResponse(amount, 0.0D, EconomyResponse.ResponseType.SUCCESS, "");
    }

    public EconomyResponse setPlayer(String playerName, double amount) {
        if (!hasAccount(playerName))
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The player does not have an account!");
        if (amount < 0)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Amount is less than zero");
        Profile profile = Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName));
        profile.setBalance(amount);
        profile.backup();
        return new EconomyResponse(amount, 0.0D, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName));
        return true;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(playerName));
        return true;
    }
}
