package eu.pixliesearth.discord.commands;

import org.javacord.api.event.message.MessageCreateEvent;

import eu.pixliesearth.discord.DiscordCommand;

public class DiscordPay extends DiscordCommand {

    public DiscordPay() {
        super("pay");
    }

    @Override
    public void run(MessageCreateEvent event) {
/*        Profile profile = Profile.getByDiscord(event.getMessageAuthor().getIdAsString());
        if (profile == null) {
            reply(event, "you don't have your discord and ingame account linked.");
            return;
        }
        String[] split = event.getMessageContent().split(" ");
        if (split.length != 3) {
            reply(event, "correct usage: /pay <PLAYER> <AMOUNT>");
            return;
        }
        if (!NumberUtils.isNumber(split[2])) {
            reply(event, "the amount has to be a number.");
            return;
        }
        OfflinePlayer op = Bukkit.getOfflinePlayerIfCached(split[1]);
        if (op == null) {
            reply(event, "this player does not exist.");
            return;
        }
        Profile target = instance.getProfile(op.getUniqueId());
        double amount = Double.parseDouble(split[2]);
        boolean transaction = profile.withdrawMoney(amount, "payment to " + op.getName());
        if (transaction) {
            target.depositMoney(amount, "payment from " + profile.getAsOfflinePlayer().getName());
            reply(event, "successfully paid " + op.getName() + " $" + amount + ".");
            if (op.isOnline())
                Lang.RECEIVED_MONEY_FROM_PLAYER.send(op.getPlayer(), "%TARGET%;" + profile.getAsOfflinePlayer().getName(), "%AMOUNT%;" + amount);
        } else {
            reply(event, "you don't have enough money for that action. /bal");
        }*/

    }

}
