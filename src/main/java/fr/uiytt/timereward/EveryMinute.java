package fr.uiytt.timereward;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class EveryMinute extends BukkitRunnable {

	@Override
	public void run() {
		double money_reward = Main.CONFIG.getMoney_reward();
		
		Bukkit.getOnlinePlayers().forEach(player -> {
			int time = Main.getTimePlayersonline().get(player);
			time++;
			if(time == Main.CONFIG.getTime_for_money()) {
				time = 0;
				player.sendMessage(Main.CONFIG.HEADER + " Tu as gagné " + String.valueOf(money_reward) + " " + Main.getEcon().currencyNamePlural());
				Main.getEcon().depositPlayer(player, money_reward);
			}
			Main.getTimePlayersonline().put(player, time);
		});

	}

}
