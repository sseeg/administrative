package net.seeghend.Administrative.event;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.seeghend.Administrative.event.custom.teleport.TeleportAcceptEvent;
import net.seeghend.Administrative.teleport.TeleportManager;
import net.seeghend.Administrative.teleport.TeleportRequest;

import org.spongepowered.api.event.EventHandler;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

public class TeleportAcceptHandler implements EventHandler<TeleportAcceptEvent> {
	TeleportManager teleportManager = TeleportManager.getTeleportManager();
	List<TeleportRequest> requests = teleportManager.getRequestList();
	
	Calendar calendar = new GregorianCalendar();
	
	public void handle(TeleportAcceptEvent event) throws Exception {
		if (event.isCancelled()) {
			return;
		}
		
		UUID accepter = event.getAccepter().getUniqueId();
		
		for (Iterator<TeleportRequest> iterator = requests.iterator(); iterator.hasNext();) {
			TeleportRequest request = iterator.next();
			
			long currentTime = calendar.getTimeInMillis();
			long expiration = request.getExpirationTime();
			
			boolean isTeleportHere = request.isTeleportHere();
			
			UUID target = request.getRequestee().getUniqueId();
			if (accepter == target) {
				// There is a matching request, check if it is expired
				if (expiration > currentTime) {
					// Request is not expired, teleport!
					
					if (isTeleportHere) {
						teleportManager.teleport(request.getRequestee(), request.getRequester());
						request.getRequester().sendMessage(Texts.of(TextColors.YELLOW, "Player '" + request.getRequestee().getName() + "' teleported to you!"));
						request.getRequestee().sendMessage(Texts.of(TextColors.GREEN, "Teleported!"));
					} else {
						teleportManager.teleport(request.getRequester(), request.getRequestee());
						request.getRequester().sendMessage(Texts.of(TextColors.GREEN, "Teleported!"));
						request.getRequestee().sendMessage(Texts.of(TextColors.YELLOW, "Player '" + request.getRequester().getName() + "' teleported to you!"));
					}
				}
				requests.remove(request);
			}
		}
	}
}
