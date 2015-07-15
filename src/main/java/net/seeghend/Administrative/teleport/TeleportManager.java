package net.seeghend.Administrative.teleport;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.world.Location;

import com.flowpowered.math.vector.Vector3d;

public class TeleportManager {
    private static TeleportManager instance = null;
    protected TeleportManager() {}
    
    private List<TeleportRequest> requests = new ArrayList<TeleportRequest>();
    
    public void teleport(Player teleportee, Player target) {
		// Get target location and rotation
		Location destination = target.getPlayer().get().getLocation();
		Vector3d destRotation = target.getPlayer().get().getRotation();
		
		// Teleport
		teleportee.setRotation(destRotation);
		teleportee.setLocation(destination);
		teleportee.setRotation(destRotation);
    }
    
    public List<TeleportRequest> getRequestList() {
    	return requests;
    }
    
    public static TeleportManager getTeleportManager() {
    	if (instance == null) {
    		instance = new TeleportManager();
    	}
    	
    	return instance;
    }
}
