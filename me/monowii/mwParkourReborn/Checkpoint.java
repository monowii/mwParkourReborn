package me.monowii.mwParkourReborn;

import me.monowii.mwParkourReborn.managers.ParkoursManager;

public class Checkpoint
{
	public enum Type {
		START,
		CHECKPOINT,
		END
	};
	
	private String parkourName;
	private int id;
	
	public Checkpoint(String parkourName, int id)
	{
		this.parkourName = parkourName;
		this.id = id;
	}
	
	public String getParkourName() {
		return parkourName;
	}
	
	public int getId() {
		return id;
	}
	
	public Checkpoint.Type getType() {
		if (id == 0)
			return Type.START;
		else if (ParkoursManager.getParkour(parkourName).getCheckpoints().size()-1 == id)
			return Type.END;
		else
			return Type.CHECKPOINT;
	}
}
