package fr.uiytt.timereward;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class EveryMinute extends BukkitRunnable {

	@Override
	public void run() {
		//Get the config 
		double money_reward = Main.CONFIG.getMoney_reward();

		Bukkit.getOnlinePlayers().forEach(player -> {
			boolean increment = true;
			//get time
			int time = Main.getTimePlayersonline().get(player.getName());
			
			//Detect if the config allow afk player to receive money and if essentials is detected
			if(!Main.CONFIG.isAllow_reward_afk() && Bukkit.getPluginManager().isPluginEnabled("essentials")) {
				
				if(EssentialsHook.checkEssentialsAFK(player)){
						//desactivate the +1 to the time online if the player is afk
						increment = false;
				} 
				
			}
			
			//if the time for the player is not desactivated, do +1 to the time online
			if(increment) {
				time++;
			}
			
			if(time >= Main.CONFIG.getTime_for_money()) {
				//reset and send money
				time = 0;
				player.sendMessage(Main.CONFIG.HEADER + " Tu as gagné " + String.valueOf(money_reward) + " " + Main.getEcon().currencyNamePlural());
				Main.getEcon().depositPlayer(player, money_reward);
			}
			//Save new time
			Main.getTimePlayersonline().put(player.getName(), time);
		});

	}
	
}

