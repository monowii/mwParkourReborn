package me.monowii.mwParkourReborn.listeners;

import me.monowii.mwParkourReborn.Checkpoint;
import me.monowii.mwParkourReborn.ParkourPlayer;
import me.monowii.mwParkourReborn.Utils;
import me.monowii.mwParkourReborn.managers.ParkoursManager;
import me.monowii.mwParkourReborn.managers.PlayersManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener
{
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ())
			return;
		
		Checkpoint checkpoint = ParkoursManager.getCheckpoint(e.getTo()); 
		
		if (checkpoint != null)
		{
			Player p = e.getPlayer();
			ParkourPlayer parkourPlayer = PlayersManager.getPlayer(p.getUniqueId());
			
			if (ParkoursManager.getParkour(checkpoint.getParkourName()).getCheckpoints().size() < 2)
			{
				p.sendMessage("§cThis parkour has not enought checkpoints !");
				return;
			}
			
			if (checkpoint.getType() == Checkpoint.Type.START)
			{
				//If the player is not in a parkour
				if (parkourPlayer == null || checkpoint.getParkourName() != parkourPlayer.getParkourName())
				{
					PlayersManager.addPlayer(p.getUniqueId(), checkpoint.getParkourName());
					p.sendMessage("§a["+checkpoint.getParkourName()+"]Timer started !");
				}
				//If the player restart a parkour
				else
				{
					PlayersManager.restartPlayer(p.getUniqueId(), checkpoint.getParkourName());
					p.sendMessage("§a["+checkpoint.getParkourName()+"]Timer restarted !");
				}
				
			}
			else
			{
				if (parkourPlayer == null)
					return;
				
				//If the player walk on a checkpoint of another parkour
				if (!checkpoint.getParkourName().equals(parkourPlayer.getParkourName())) {
					p.sendMessage("§cYou are not in the good parkour !");
					return;
				}
				
				if (parkourPlayer.getCheckpointId() >= checkpoint.getId()) {
					p.sendMessage("§cCheckpoint already passed!");
					return;
				}
				
				if (parkourPlayer.getCheckpointId() + 1 < checkpoint.getId()) {
					p.sendMessage("§cLast checkpoint forgotten!");
					return;
				}
				
				if (checkpoint.getType() == Checkpoint.Type.CHECKPOINT)
				{
					PlayersManager.setCheckpointId(p.getUniqueId(), checkpoint.getId());
					p.sendMessage("§bCheckpoint passed!");

				}
				else if (checkpoint.getType() == Checkpoint.Type.END)
				{
					long time = System.currentTimeMillis() - parkourPlayer.getStartTime();
					p.sendMessage("§6You finished this parkour in " + Utils.formatTime(time));
					PlayersManager.deletePlayer(p.getUniqueId());
				}
			}

		}
	}
}
