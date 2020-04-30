package fr.uiytt.timereward;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventsListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Integer time = Main.DATASTORAGE.getOrSetDefault(player.getUniqueId().toString(), 0);
		Main.getTimePlayersonline().put(player, time);
	}
	
	public void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Integer time = Main.getTimePlayersonline().get(player);
		Main.DATASTORAGE.set(player.getUniqueId().toString(), time);
		Main.getTimePlayersonline().remove(player);
	}
}
