package eu.pixliesearth.core.custom.commands.subcommands.skills;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.custom.skills.Skill;
import eu.pixliesearth.core.custom.skills.SkillHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.hover.content.Text;

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
			commandSender.sendMessage("§c[§r❌§c] §rIncorrect usage! Follow the usage /skill leaderboard <skillUUID>");
			return false;
		}
		SkillHandler skillHandler = SkillHandler.getSkillHandler();
		Skill skill = skillHandler.getSkillFromUUID(parameters[0]);
		if (skill==null) {
			commandSender.sendMessage("§c[§r❌§c] §rThe skill §e"+parameters[0]+" §rdoes not exist!");
			return false;
		} else {
			TextComponent t = new TextComponent("§e+-------------+");
			t.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(ChatColor.GOLD+"Refreshes every 7.5 seconds!")));
			commandSender.sendMessage(t); // 15 long (13 -'s)
			List<UUID> leaderboard = skillHandler.getLeaderboardOf(skill.getSkillUUID());
			if (leaderboard==null) {
				commandSender.sendMessage("§c[§r❌§c] §rAn unknown error occured");
				return false;
			}
			while (leaderboard.size()>10) {
				leaderboard.remove(leaderboard.size()-1);
			}
			for (int i = 0; i < leaderboard.size(); i++) {
				TextComponent t2 = new TextComponent(ChatColor.GOLD+Integer.toString(i+1)+"# "+ChatColor.GRAY+Bukkit.getOfflinePlayer(leaderboard.get(i)).getName());
				t2.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(ChatColor.GOLD+"XP:"+ChatColor.GRAY+Integer.toString(skillHandler.getXPOf(leaderboard.get(i), skill.getSkillUUID()))), new Text("\n"+ChatColor.GOLD+"Level:"+ChatColor.GRAY+skillHandler.getLevelOf(leaderboard.get(i), skill.getSkillUUID())+"/"+skill.getMaxSkillLevel())));
				commandSender.sendMessage(t2);
			}
		}
		return true;
	}
}
