package eu.pixliesearth.core.custom.commands.subcommands.skills;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.custom.skills.Skill;
import eu.pixliesearth.core.custom.skills.SkillHandler;

public class LeaderboardSkill extends CustomSubCommand {
	
	@Override
	public String getCommandName() {
		return "leaderboard";
	}
	
	@Override
	public String getCommandUsage() {
		return "Gets the leaderboard of the given skill";
	}
	
	@Override
	public ITabable[] getParams() {
		return new ITabable[] {new ITabable() {
			
			@Override
			public String getTabableName() {
				return "skillUUID";
			}
			
			@Override
			public List<String> getTabable(CommandSender commandSender, String[] params) {
				List<String> list = new ArrayList<>();
				SkillHandler.getSkillHandler().getSkills().forEach((skill) -> list.add(skill.getSkillUUID()));
				return list;
			}
			
		}};
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		if (parameters.length<1) {
			commandSender.sendMessage("§7[§c❌§7] §7Incorrect usage! Follow the usage /skill leaderboard <skillUUID>");
			return false;
		}
		SkillHandler skillHandler = SkillHandler.getSkillHandler();
		Skill skill = skillHandler.getSkillFromUUID(parameters[0]);
		if (skill==null) {
			commandSender.sendMessage("§7[§c❌§7] §7The skill §e"+parameters[0]+" §7does not exist!");
			return false;
		} else {
			TextComponent t = Component.text("§7+-------------+");
			t.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("Refreshes every 7.5 seconds!").color(NamedTextColor.GOLD)));
			commandSender.sendMessage(t); // 15 long (13 -'s)
			List<UUID> leaderboard = skillHandler.getLeaderboardOf(skill.getSkillUUID());
			if (leaderboard==null) {
				commandSender.sendMessage("§8[§c❌§8] §7An unknown error occured");
				return false;
			}
			while (leaderboard.size()>10) {
				leaderboard.remove(leaderboard.size()-1);
			}
			for (int i = 0; i < leaderboard.size(); i++) {
				NamedTextColor co;
				if (i+1 == 1) {
					co = NamedTextColor.GOLD;
				} else if (i+1 == 2) {
					co = NamedTextColor.GRAY;
				} else if (i+1 == 3) {
					co = NamedTextColor.RED;
				} else {
					co = NamedTextColor.DARK_AQUA;
				}
				TextComponent t2 = Component.text(co + Integer.toString(i+1)+"# "+ ChatColor.GRAY+Bukkit.getOfflinePlayer(leaderboard.get(i)).getName());
				t2.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text(ChatColor.GOLD+"XP: " + ChatColor.GRAY + Integer.toString(skillHandler.getXPOf(leaderboard.get(i), skill.getSkillUUID())) + "\n" + ChatColor.GOLD+"Level: " + ChatColor.GRAY+skillHandler.getLevelOf(leaderboard.get(i), skill.getSkillUUID()) + "/" + skill.getMaxSkillLevel())));
				commandSender.sendMessage(t2);
			}
			commandSender.sendMessage(t);
		}
		return true;
	}
}
