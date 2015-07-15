package net.seeghend.Administrative.event.custom.teleport;

import org.spongepowered.api.event.AbstractEvent;
import org.spongepowered.api.event.Cancellable;

public class TeleportEvent extends AbstractEvent implements Cancellable {
	private boolean cancelled;
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
}
