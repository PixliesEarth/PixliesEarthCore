package eu.pixliesearth.utils;

import eu.pixliesearth.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

import java.io.InputStream;
import java.util.Collections;

public class DynmapUtil {
	
	public static DynmapAPI getDynmapAPI() {
		return (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("Dynmap");
	}
	
	public static MarkerAPI getMarkerAPI() {
		return getDynmapAPI().getMarkerAPI();
	}
	
	private static DynmapMissile dynmapMissile = new DynmapMissile();
	
	public static DynmapMissile getDynmapMissile() {
		return dynmapMissile;
	}
	
	public static class DynmapMissile {
		
		private MarkerAPI markerAPI;
		private MarkerIcon missileIcon;
		private MarkerSet missileSet;
		
		public DynmapMissile() {
			this.markerAPI = getMarkerAPI();
			InputStream inputStream = Main.getInstance().getResource("icons/Missile.png");
			this.missileIcon = (markerAPI.getMarkerIcon("Missile")==null) ? markerAPI.createMarkerIcon("Missile", "Missile", inputStream) : markerAPI.getMarkerIcon("Missile");
			this.missileSet = markerAPI.createMarkerSet("missiles.markerset", "Meteors", Collections.singleton(missileIcon), false);
		}
		
		public Marker addMissileAt(Location location, double d, double d2, Location l2) {
			if(missileSet != null){
				Marker marker = missileSet.createMarker("Missile", "Missile", location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), missileIcon, false);
				marker.setDescription("Explosive:    " + d
									 +"\nPlayerDamage: " + d2
									 +"\nGoing to:" + ((l2==null) ? "Unknown" : ("\n x: "+l2.getBlockX()+"\n z: "+l2.getBlockZ()))
									 );
	            return marker;
	        } else {
	        	return null;
	        }
		}
		
	}
	
}
