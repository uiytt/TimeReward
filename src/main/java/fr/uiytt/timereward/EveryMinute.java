package fr.uiytt.timereward;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.earth2me.essentials.Essentials;

import net.ess3.api.IUser;

public class EveryMinute extends BukkitRunnable {

	@Override
	public void run() {
		double money_reward = Main.CONFIG.getMoney_reward();
		Essentials ess = (Essentials) Main.getInstance().getServer().getPluginManager().getPlugin("Essentials");
		
		Bukkit.getOnlinePlayers().forEach(player -> {
			boolean increment = true;
			int time = Main.getTimePlayersonline().get(player);
			
			if(ess != null) {
				IUser user = ess.getUser(player.getUniqueId());
				if(user.isAfk()) {
					increment = false;
				}
			}
			if(increment) {
				time++;
			}
			
			if(time == Main.CONFIG.getTime_for_money()) {
				time = 0;
				player.sendMessage(Main.CONFIG.HEADER + " Tu as gagné " + String.valueOf(money_reward) + " " + Main.getEcon().currencyNamePlural());
				Main.getEcon().depositPlayer(player, money_reward);
			}
			Main.getTimePlayersonline().put(player, time);
		});

	}

}
