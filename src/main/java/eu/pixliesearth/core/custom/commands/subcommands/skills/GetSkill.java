package eu.pixliesearth.core.custom.commands.subcommands.skills;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.pixliesearth.core.custom.CustomSubCommand;
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
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		if (!ranByPlayer) {
			commandSender.sendMessage("§c[§e!§c] §rYou have to be a player to run this command!");
			return false;
		} else if (parameters.length<1) {
			commandSender.sendMessage("§c[§e!§c] §rIncorrect usage! Follow the usage /skill level <skillUUID>");
			return false;
		}
		SkillHandler skillHandler = SkillHandler.getSkillHandler();
		Skill skill = skillHandler.getSkillFromUUID(parameters[0]);
		if (skill==null) {
			commandSender.sendMessage("§c[§e!§c] §rThe skill §e"+parameters[0]+" §rdoes not exist!");
			return false;
		} else {
			int level = skillHandler.getLevelOf(((Player)commandSender).getUniqueId(), parameters[0]);
			commandSender.sendMessage("§e+-------------+"); // 15 long (13 -'s)
			commandSender.sendMessage("§7>> §eSkill "+skill.getSkillName());
			commandSender.sendMessage("§7>> §eLevel "+level);
		}
		return true;
	}	
}
