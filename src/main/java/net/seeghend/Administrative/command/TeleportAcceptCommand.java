package net.seeghend.Administrative.command;

import net.seeghend.Administrative.Administrative;
import net.seeghend.Administrative.event.custom.teleport.TeleportAcceptEvent;

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class TeleportAcceptCommand implements CommandExecutor {
	private Game game = Administrative.access.game;
	private Server server = game.getServer();
	
	public CommandResult execute(CommandSource src, CommandContext args) {
		Player accepter = server.getPlayer(src.getName()).get();
		
		game.getEventManager().post(new TeleportAcceptEvent(accepter));
		return CommandResult.success();
	}
}
