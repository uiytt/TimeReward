package fr.uiytt.timereward;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventsListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		//Add player to the "live" vars 
		Player player = event.getPlayer();
		Integer time = Main.DATASTORAGE.getOrSetDefault(player.getUniqueId().toString(), 0);
		Main.getTimePlayersonline().put(player.getName(), time);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		//Store the time of the player in a file 
		Player player = event.getPlayer();
		Integer time = Main.getTimePlayersonline().get(player.getName());
		if(time == null) {
			Main.getInstance().getLogger().log(Level.WARNING, "No time register for player : " + player.getName());
			return;
		}
		Main.DATASTORAGE.set(player.getUniqueId().toString(), time);
		Main.getTimePlayersonline().remove(player.getName());
	}

}
