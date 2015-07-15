package net.seeghend.Administrative.teleport;

import org.spongepowered.api.entity.player.Player;

public class TeleportRequest {
	private Player requester;
	private Player requestee;
	private boolean teleportHere;
	private long expirationTime;
	
	public Player getRequester() {
		return requester;
	}
	
	public Player getRequestee() {
		return requestee;
	}
	
	public boolean isTeleportHere() {
		return teleportHere;
	}
	
	public long getExpirationTime() {
		return expirationTime;
	}
	
	public TeleportRequest(Player requester, Player requestee, boolean teleportHere, long expirationTime) {
		this.requester = requester;
		this.requestee = requestee;
		this.teleportHere = teleportHere;
		this.expirationTime = expirationTime;
	}
}
