package com.epam.andrii_loievets.skipass;

public abstract class SkiPass {
	private int id;
	private static int nextID;
	private boolean blocked;
	
	public SkiPass() {
		id = nextID++;
	}
	
	public int getID() {
		return id;
	}
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public void block() {
		blocked = true;
	}
}