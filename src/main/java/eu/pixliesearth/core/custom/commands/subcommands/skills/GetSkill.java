package eu.pixliesearth.core.custom.commands.subcommands.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.custom.skills.Skill;
import eu.pixliesearth.core.custom.skills.SkillHandler;

public class GetSkill extends CustomSubCommand {
	
	@Override
	public String getCommandName() {
		return "level";
	}
	
	@Override
	public String getCommandUsage() {
		return "Gets the level of the given skill";
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
		if (!ranByPlayer) {
			commandSender.sendMessage("§c[§r❌§c] §rYou have to be a player to run this command!");
			return false;
		} else if (parameters.length<1) {
			commandSender.sendMessage("§c[§r❌§c] §rIncorrect usage! Follow the usage /skill level <skillUUID>");
			return false;
		}
		SkillHandler skillHandler = SkillHandler.getSkillHandler();
		Skill skill = skillHandler.getSkillFromUUID(parameters[0]);
		if (skill==null) {
			commandSender.sendMessage("§c[§r❌§c] §rThe skill §e"+parameters[0]+" §rdoes not exist!");
			return false;
		} else {
			int level = skillHandler.getLevelOf(((Player)commandSender).getUniqueId(), parameters[0]);
			commandSender.sendMessage("§e+-------------+"); // 15 long (13 -'s)
			commandSender.sendMessage("§7>> §eSkill "+skill.getSkillName());
			commandSender.sendMessage("§7>> §eLevel "+level+"/"+skillHandler.getSkillFromUUID(parameters[0]).getMaxSkillLevel());
			commandSender.sendMessage("§7>> §eXP "+skillHandler.getXPOf(((Player)commandSender).getUniqueId(), parameters[0]));
		}
		return true;
	}	
}
