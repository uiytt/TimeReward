package fr.uiytt.timereward;

import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import net.md_5.bungee.api.ChatColor;

public class ConfigManager {

	public static final String ConfigLink = "https://raw.githubusercontent.com/uiytt/TimeReward/master/config.yml";
	private Yaml yaml;
	private int time_for_money;
	private double money_reward;
	private boolean allow_reward_afk;
	public final String HEADER = ChatColor.DARK_GRAY  + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "TimeReward" + ChatColor.DARK_GRAY  + "" + ChatColor.BOLD + "] " + ChatColor.GRAY;
	
	
	public ConfigManager() {
		
	}
	
	public void load() {
		this.yaml = new Config("config","plugins/TimeReward");
		time_for_money = yaml.getOrSetDefault("time_for_money", 60);
		money_reward = yaml.getOrSetDefault("money_reward", 20.0);
		allow_reward_afk = yaml.getOrSetDefault("allow_reward_afk", false);
	}

	public int getTime_for_money() {
		return time_for_money;
	}

	public double getMoney_reward() {
		return money_reward;
	}

	public boolean isAllow_reward_afk() {
		return allow_reward_afk;
	}
	
	
}
