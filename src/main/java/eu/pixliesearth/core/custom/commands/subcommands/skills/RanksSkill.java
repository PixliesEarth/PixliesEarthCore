package eu.pixliesearth.core.custom.commands.subcommands.skills;

import java.util.List;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.skills.Skill;
import eu.pixliesearth.core.custom.skills.SkillHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class RanksSkill extends CustomSubCommand {
	
	@Override
	public String getCommandName() {
		return "rank";
	}
	
	@Override
	public String getCommandUsage() {
		return "Gets the players ranks in all skills";
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		if (!ranByPlayer) {
			commandSender.sendMessage("§c[§r❌§c] §rYou have to be a player to run this command!");
			return false;
		}
		SkillHandler skillHandler = SkillHandler.getSkillHandler();
		commandSender.sendMessage("§e+-------------+"); // 15 long (13 -'s)
		for (Skill skill : skillHandler.getSkills()) {
			TextComponent t = new TextComponent(ChatColor.GRAY+">> "+ChatColor.GOLD+skill.getSkillName()+ChatColor.YELLOW+" "+(getPosition(((Player)commandSender).getUniqueId(), skill.getSkillUUID())+1));
			t.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/skill leaderboard "+skill.getSkillUUID()));
			commandSender.sendMessage(t);
		}
		return true;
	}
	
	private int getPosition(UUID uuid, String skillUUID) {
		SkillHandler skillHandler = SkillHandler.getSkillHandler();
		List<UUID> leaderboard = skillHandler.getLeaderboardOf(skillUUID);
		for (int i = 0; i < leaderboard.size(); i++) {
			if (leaderboard.get(i).equals(uuid)) {
				return i;
			}
		}
		return -2;
	}
}
