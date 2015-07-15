package net.seeghend.Administrative.command;

import net.seeghend.Administrative.Administrative;
import net.seeghend.Administrative.event.custom.teleport.TeleportRequestEvent;

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class TeleportHereRequestCommand implements CommandExecutor {
	private Game game = Administrative.access.game;
	private Server server = game.getServer();
	
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		Player requester = server.getPlayer(src.getName()).get();
		Player requestee;
		
		try {
			// TODO: stop ugly sponge player offline message and use our own
			requestee = args.<Player>getOne("Player").get();
		} catch (IllegalStateException e) {
			// No username was provided
			Text warning = Texts.builder("Usage: ").color(TextColors.RED)
					.append(Texts.builder("/tphere <username>").style(TextStyles.ITALIC).build()).build();
			src.sendMessage(warning);
			
			return CommandResult.empty();
		}
		
		if (!requestee.getPlayer().isPresent()) {
			// Player isn't present
			Text warning = Texts.builder("User '" + requestee + "' isn't present").color(TextColors.RED).build();
			src.sendMessage(warning);
			
			return CommandResult.empty();
			
		} else {
			// Post new teleport request
			game.getEventManager().post(new TeleportRequestEvent(requester, requestee, true));
		    requester.sendMessage(Texts.of("Request sent"));
			
			return CommandResult.success();
		}
	}
}
