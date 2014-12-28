package me.monowii.mwParkourReborn;

import me.monowii.mwParkourReborn.commands.ParkourCommand;
import me.monowii.mwParkourReborn.listeners.PlayerMoveListener;
import me.monowii.mwParkourReborn.listeners.PlayerQuitListener;
import me.monowii.mwParkourReborn.listeners.SignChangeListener;
import me.monowii.mwParkourReborn.managers.ParkoursManager;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ParkourReborn extends JavaPlugin
{
	private static JavaPlugin plugin;
	
	public void onEnable() {
		plugin = this;
		
		ParkoursManager.loadParkours();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerMoveListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		pm.registerEvents(new SignChangeListener(), this);
		
		getCommand("parkour").setExecutor(new ParkourCommand());
	}
	
	public void onDisable() {
		
	}
	
	public static JavaPlugin getInstance() {
		return plugin;
	}
}
