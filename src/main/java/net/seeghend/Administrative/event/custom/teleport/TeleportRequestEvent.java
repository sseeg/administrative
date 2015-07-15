package net.seeghend.Administrative.event.custom.teleport;

import org.spongepowered.api.entity.player.Player;

public class TeleportRequestEvent extends TeleportEvent {
	private Player requester;
	private Player requestee;
	private boolean isTeleportHere;
	
	public Player getRequester() {
		return requester;
	}
	
	public Player getRequestee() {
		return requestee;
	}
	
	public boolean isTeleportHere() {
		return isTeleportHere;
	}
	
	public TeleportRequestEvent(Player requester, Player requestee, boolean teleportHere) {
		this.requester = requester;
		this.requestee = requestee;
		this.isTeleportHere = teleportHere;
	}
}
