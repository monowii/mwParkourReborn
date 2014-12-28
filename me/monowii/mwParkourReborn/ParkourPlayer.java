package me.monowii.mwParkourReborn;

public class ParkourPlayer
{
	private String parkourName;
	private int checkpointId;
	private long startTime;
	
	public ParkourPlayer(String parkourName) {
		this.parkourName = parkourName;
		checkpointId = 0;
		resetStartTime();
	}
	
	public String getParkourName() {
		return parkourName;
	}
	
	public void setCheckpointId(int checkpointId) {
		this.checkpointId = checkpointId;
	}
	
	public int getCheckpointId() {
		return checkpointId;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public void resetStartTime() {
		startTime = System.currentTimeMillis();
	}
}
