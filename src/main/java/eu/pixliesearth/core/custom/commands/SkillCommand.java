package eu.pixliesearth.core.custom.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.custom.skills.Skill;
import eu.pixliesearth.core.custom.skills.SkillHandler;

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
	
	protected class SkillsTabable implements ITabable {
		
		@Override
		public List<String> getTabable(CommandSender commandSender, String[] params) {
			List<String> list = new ArrayList<>();
			for (Skill skill : SkillHandler.getSkillHandler().getSkills()) {
				list.add(skill.getSkillUUID());
			}
			Collections.sort(list);
			return list;
		}
		
		@Override
		public String getTabableName() {
			return "skillUUID";
		}
		
	}

}
