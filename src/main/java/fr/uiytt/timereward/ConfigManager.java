package fr.uiytt.timereward;

import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import net.md_5.bungee.api.ChatColor;

public class ConfigManager {

	public static final String ConfigLink = "http://random";
	private Yaml yaml;
	private int time_for_money;
	private double money_reward;
	public final String HEADER = ChatColor.DARK_GRAY  + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "TimeReward" + ChatColor.DARK_GRAY  + "" + ChatColor.BOLD + "] " + ChatColor.GRAY;
	
	
	public ConfigManager() {
		this.yaml = new Config("config","plugins/TimeReward");
	}
	
	public void load() {
		time_for_money = yaml.getOrSetDefault("time_for_money", 60);
		money_reward = yaml.getOrSetDefault("money_reward", 20.0);
	}

	public int getTime_for_money() {
		return time_for_money;
	}

	public double getMoney_reward() {
		return money_reward;
	}
	
	
}
