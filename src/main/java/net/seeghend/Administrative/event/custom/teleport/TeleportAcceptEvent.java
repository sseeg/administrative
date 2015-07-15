package net.seeghend.Administrative.event.custom.teleport;

import org.spongepowered.api.entity.player.Player;

public class TeleportAcceptEvent extends TeleportEvent {
	private Player accepter;
	
	public Player getAccepter() { 
		return accepter;
	}
	
	public TeleportAcceptEvent(Player accepter) {
		this.accepter = accepter;
	}
}
