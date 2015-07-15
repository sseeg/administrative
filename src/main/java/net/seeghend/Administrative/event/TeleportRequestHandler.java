package net.seeghend.Administrative.event;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import net.seeghend.Administrative.Administrative;
import net.seeghend.Administrative.event.custom.teleport.TeleportRequestEvent;
import net.seeghend.Administrative.teleport.TeleportManager;
import net.seeghend.Administrative.teleport.TeleportRequest;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.EventHandler;
import org.spongepowered.api.service.pagination.PaginationBuilder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;

public class TeleportRequestHandler implements EventHandler<TeleportRequestEvent> {
	private Game game = Administrative.access.game;
	
	TeleportManager teleportManager = TeleportManager.getTeleportManager();
	List<TeleportRequest> requests = teleportManager.getRequestList();
	
	Calendar calendar = new GregorianCalendar();
	
	private PaginationService paginationService = game.getServiceManager().provide(PaginationService.class).get();
	private PaginationBuilder paginationBuilder = paginationService.builder();
	
	public void handle(TeleportRequestEvent event) throws Exception {
		if (event.isCancelled()) {
			return;
		}
		
		Player requester = event.getRequester();
		Player requestee = event.getRequestee();
		
		boolean isTeleportHere = event.isTeleportHere();
		
		String requestQuestion;
		if (!isTeleportHere) {
			requestQuestion = "Player '" + requester.getName() + "' would like to teleport to you";
		} else {
			requestQuestion = "Player '" + requester.getName() + "' would like you to teleport to them";
		}
		
		CommandSource requesteeCommandSource = requestee.getPlayer().get().getCommandSource().get();
		
		long expiration = calendar.getTimeInMillis() + 60000;
		requests.add(new TeleportRequest(requester, requestee, isTeleportHere, expiration));
		
		// Ask requestee
		paginationBuilder
			.title(Texts.of(TextColors.GOLD, "Teleport Request"))
			.contents(
				Texts.of(TextColors.YELLOW, requestQuestion),
				Texts.of(TextColors.YELLOW, "Type '/tpaccept' to accept their request, or ignore this for 60 seconds to decline")
			)
			.paddingString("*")
			.sendTo(requesteeCommandSource);
	}
}
