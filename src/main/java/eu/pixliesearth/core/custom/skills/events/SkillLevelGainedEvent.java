package eu.pixliesearth.core.custom.skills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillLevelGainedEvent extends Event implements Cancellable {
	
	public static HandlerList handlers = new HandlerList();
	protected final Player player;
	protected final String skillUUID;
    protected int level;
    protected final int oldLevel;
    protected boolean isCancelled = false;
    
    public SkillLevelGainedEvent(Player player, String skillUUID, int newLevel, int oldLevel) {
    	this.player = player;
    	this.skillUUID = skillUUID;
    	this.level = newLevel;
    	this.oldLevel = oldLevel;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public String getGainedSkillUUID() {
    	return this.skillUUID;
    }
    
    public double getNewLevel() {
        return this.level;
    }
    
    public double getOldLevel() {
        return this.oldLevel;
    }
    
    @Override
    public HandlerList getHandlers(){
        return handlers;
    }
    
    public static HandlerList getHandlerList(){
        return handlers;
    }
    
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }
	
}
