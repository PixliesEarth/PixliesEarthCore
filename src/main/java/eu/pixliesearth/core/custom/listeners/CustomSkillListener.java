package eu.pixliesearth.core.custom.listeners;

import org.bukkit.event.EventHandler;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.skills.SkillHandler;
import eu.pixliesearth.core.custom.skills.events.SkillLevelGainedEvent;
import eu.pixliesearth.core.custom.skills.events.SkillXPGainedEvent;

public class CustomSkillListener extends CustomListener {
	
	@EventHandler
	public void SkillXPGainedEvent(SkillXPGainedEvent event) {
		if (event.isCancelled()) return;
		int level = SkillHandler.getSkillHandler().getLevelOf(event.getPlayer().getUniqueId(), event.getGainedSkillUUID());
		SkillHandler.getSkillHandler().addXPTo(event.getPlayer().getUniqueId(), event.getGainedSkillUUID(), event.getAmount());
		if (level<SkillHandler.getSkillHandler().getLevelOf(event.getPlayer().getUniqueId(), event.getGainedSkillUUID())) {
			new SkillLevelGainedEvent(event.getPlayer(), event.getGainedSkillUUID(), SkillHandler.getSkillHandler().getLevelOf(event.getPlayer().getUniqueId(), event.getGainedSkillUUID()), level).callEvent();
		}
	}
	
}
