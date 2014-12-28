package me.monowii.mwParkourReborn.listeners;

import me.monowii.mwParkourReborn.managers.PlayersManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener
{
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		if (PlayersManager.getPlayers().containsKey(e.getPlayer().getUniqueId()))
		{
			PlayersManager.deletePlayer(e.getPlayer().getUniqueId());
		}
	}
}
