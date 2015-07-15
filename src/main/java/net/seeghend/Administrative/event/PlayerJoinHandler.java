package net.seeghend.Administrative.event;

import net.seeghend.Administrative.Administrative;

import org.slf4j.Logger;
import org.spongepowered.api.event.EventHandler;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;

public class PlayerJoinHandler implements EventHandler<PlayerJoinEvent> {
	Logger logger = Administrative.access.logger;
	boolean debugging = Administrative.access.debugging;
	
	Text MOTDOne = Texts.of(Administrative.access.JoinMOTDOne);
	Text MOTDTwo = Texts.of(Administrative.access.JoinMOTDTwo);
	
	public void handle(PlayerJoinEvent event) throws Exception {
		if (Administrative.access.useJoinMOTD) {
			if (debugging) {
	        	logger.info("Sending player join MOTD");
	        	logger.info("Line 1: " + MOTDOne.toString());
	        	logger.info("Line 2: " + MOTDTwo.toString());
	        }
			
        	event.getUser().sendMessage(MOTDOne);
        	event.getUser().sendMessage(MOTDTwo);
        }
    }
}
