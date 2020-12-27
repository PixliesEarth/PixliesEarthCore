package eu.pixliesearth.core.custom.skills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillXPGainedEvent extends Event implements Cancellable {
	
	public static HandlerList handlers = new HandlerList();
	protected final Player player;
	protected final String skillUUID;
    protected int amount;
    protected boolean isCancelled = false;
    
    public SkillXPGainedEvent(Player player, String skillUUID, int amount) {
    	this.player = player;
    	this.skillUUID = skillUUID;
    	this.amount = amount;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public String getGainedSkillUUID() {
    	return this.skillUUID;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public void setAmount(int amount) {
    	this.amount = amount;
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
