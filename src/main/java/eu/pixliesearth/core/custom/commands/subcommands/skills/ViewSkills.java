package eu.pixliesearth.core.custom.commands.subcommands.skills;

import org.bukkit.command.CommandSender;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.skills.Skill;
import eu.pixliesearth.core.custom.skills.SkillHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class ViewSkills extends CustomSubCommand {
	
	@Override
	public String getCommandName() {
		return "list";
	}
	
	@Override
	public String getCommandUsage() {
		return "Lists all skills";
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		SkillHandler skillHandler = SkillHandler.getSkillHandler();
		commandSender.sendMessage("§eThere are §r"+skillHandler.getSkills().size()+"§e skills");
		commandSender.sendMessage("§e+-------------+"); // 15 long (13 -'s)
		for (Skill skill : skillHandler.getSkills()) 
			commandSender.sendMessage(buildSkillComponent(skill));
		return true;
	}
	
	private TextComponent buildSkillComponent(Skill skill) {
		TextComponent t = new TextComponent(skill.getSkillName());
		t.setColor(ChatColor.GOLD);
		t.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/skill level "+skill.getSkillUUID()));
		return t;
	}
}
