package me.monowii.mwParkourReborn.managers;

import java.util.HashMap;
import java.util.UUID;

import me.monowii.mwParkourReborn.ParkourPlayer;

public class PlayersManager
{
	private static HashMap<UUID, ParkourPlayer> players = new HashMap<UUID, ParkourPlayer>();
	
	public static HashMap<UUID, ParkourPlayer> getPlayers()
	{
		return players;
	}
	
	public static ParkourPlayer getPlayer(UUID player)
	{
		return players.get(player);
	}
	
	public static void addPlayer(UUID player, String parkour)
	{
		players.put(player, new ParkourPlayer(parkour));
	}
	
	public static void restartPlayer(UUID player, String parkour)
	{
		players.get(player).resetStartTime();
		players.get(player).setCheckpointId(0);
	}
	
	public static void setCheckpointId(UUID player, int checkpointId)
	{
		players.get(player).setCheckpointId(checkpointId);
	}
	
	public static void deletePlayer(UUID player)
	{
		players.remove(player);
	}
}
