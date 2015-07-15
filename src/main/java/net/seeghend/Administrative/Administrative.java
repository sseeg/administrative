package net.seeghend.Administrative;

import java.io.File;
import java.io.IOException;

import com.google.inject.Inject;

import net.seeghend.Administrative.command.TeleportAcceptCommand;
import net.seeghend.Administrative.command.TeleportHereRequestCommand;
import net.seeghend.Administrative.command.TeleportRequestCommand;
import net.seeghend.Administrative.event.PlayerJoinHandler;
import net.seeghend.Administrative.event.TeleportAcceptHandler;
import net.seeghend.Administrative.event.TeleportRequestHandler;
import net.seeghend.Administrative.event.custom.teleport.TeleportAcceptEvent;
import net.seeghend.Administrative.event.custom.teleport.TeleportRequestEvent;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.EventHandler;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.ConfigDir;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;

@Plugin(id = "Administrative", name = "Administrative", version = "1.0")
public class Administrative {
    // Get reference to game
    @Inject
    public Game game;
    
    // Get reference to logger
    @Inject
    public Logger logger;
    
    // Get config directory
    @Inject
    @ConfigDir(sharedRoot = false)
    private File configDir;
    
    public static Administrative access;
    
    public ConfigurationLoader<CommentedConfigurationNode> config = null;
    public ConfigurationNode configCache = null;
    
    public ConfigurationLoader<CommentedConfigurationNode> getFile(String name) throws IOException {
    	File file = new File(this.configDir, name);
    	if (!file.exists()) {
    		file.getParentFile().mkdirs();
    		file.createNewFile();
    	}
    	
    	return HoconConfigurationLoader.builder().setFile(file).build();
    }
    
    public boolean useJoinMOTD;
    public String JoinMOTDOne;
    public String JoinMOTDTwo;
    public boolean debugging;
    
    @Subscribe
    public void onPreInitialization(PreInitializationEvent event) {
    	try {
    		// Load configuration
    	    config = this.getFile("config.conf");
    	    configCache = config.load();
    	} catch (IOException e) {
    		// Warn
    		logger.warn("Failed to load configuration");
    		e.printStackTrace();
    	}
    	
    	try {
    		// Get welcome message from configuration
    		if (configCache.getNode("useJoinMOTD").isVirtual()) {
    			configCache.getNode("useJoinMOTD").setValue(true);
    		}
    		if (configCache.getNode("JoinMOTDOne").isVirtual()) {
    			configCache.getNode("JoinMOTDOne").setValue("This server is using Administrative");
    		}
    		if (configCache.getNode("JoinMOTDTwo").isVirtual()) {
    			configCache.getNode("JoinMOTDTwo").setValue("This is the second MOTD line");
    		}
    		if (configCache.getNode("debugging").isVirtual()) {
    			configCache.getNode("debugging").setValue(false);
    		}
    		
    		useJoinMOTD = configCache.getNode("useJoinMOTD").getBoolean();
    		JoinMOTDOne = configCache.getNode("JoinMOTDOne").getString();
    		JoinMOTDTwo = configCache.getNode("JoinMOTDTwo").getString();
    		debugging = configCache.getNode("debugging").getBoolean();
    		
    		config.save(configCache);
    	} catch (IOException e) {
    		// Warn
    		logger.warn("Failed to access configuration");
    		e.printStackTrace();
    	}
    	
    	access = this;
    }
    
    @Subscribe
    public void onInitialization(InitializationEvent event) {
    	// TODO: move command and event registration away from here
    	
    	// Register events
    	EventHandler<PlayerJoinEvent> PlayerJoinHandler = new PlayerJoinHandler();
    	EventHandler<TeleportAcceptEvent> TeleportAcceptHandler = new TeleportAcceptHandler();
    	EventHandler<TeleportRequestEvent> TeleportRequestHandler = new TeleportRequestHandler();
    	
    	game.getEventManager().register(this, PlayerJoinEvent.class, PlayerJoinHandler);
    	game.getEventManager().register(this, TeleportAcceptEvent.class, TeleportAcceptHandler);
    	game.getEventManager().register(this, TeleportRequestEvent.class, TeleportRequestHandler);
    	
    	// TODO: Command permissions
    	// Create teleport command
    	CommandSpec teleportRequestCommand = CommandSpec.builder()
    		.arguments(
    			GenericArguments.optional(GenericArguments.player(Texts.of("Player"), game))
    		)
    		.description(Texts.of("Teleport to player"))
    		.executor(new TeleportRequestCommand())
    		.build();
    	
    	// Create teleporthere command
    	CommandSpec teleportHereRequestCommand = CommandSpec.builder()
    		.arguments(
    			GenericArguments.optional(GenericArguments.player(Texts.of("Player"), game))
    		)
    		.description(Texts.of("Teleport a player to you"))
    		.executor(new TeleportHereRequestCommand())
    		.build();
    	
    	// Create teleport accept command
    	CommandSpec teleportAcceptCommand = CommandSpec.builder()
    		.description(Texts.of("Teleport Accept"))
    		.executor(new TeleportAcceptCommand())
    		.build();
    	
    	game.getCommandDispatcher().register(this, teleportRequestCommand, "tp");
    	game.getCommandDispatcher().register(this, teleportHereRequestCommand, "tphere");
    	game.getCommandDispatcher().register(this, teleportAcceptCommand, "tpaccept");
    }
    
    @Subscribe
    public void onServerStart(ServerStartedEvent event) {
        logger.info("Administrative plugin loaded!");
    }
}
