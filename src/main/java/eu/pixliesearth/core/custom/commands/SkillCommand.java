package eu.pixliesearth.core.custom.commands;

import org.bukkit.command.CommandSender;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand.TabableSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.skills.GetSkill;
import eu.pixliesearth.core.custom.commands.subcommands.skills.LeaderboardSkill;
import eu.pixliesearth.core.custom.commands.subcommands.skills.RanksSkill;
import eu.pixliesearth.core.custom.commands.subcommands.skills.ViewSkills;
import eu.pixliesearth.core.custom.interfaces.ITabable;

public class SkillCommand extends CustomCommand {

	@Override
	public String getCommandName() {
		return "skill";
	}

	@Override
	public String getCommandDescription() {
		return "A command to interface with skills";
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		return true;
	}
	
	@Override
	public ITabable[] getParams() {
		return new ITabable[] {new TabableSubCommand(new GetSkill(), new ViewSkills(), new LeaderboardSkill(), new RanksSkill())};
	}

}
