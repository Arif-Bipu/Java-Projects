/*
Name: Arif Bipu
Course: CNT 4714 Fall 2020
Assignment title: Project 2 – Multi-threaded programming in Java
Date: October 4, 2020
Class: CNT 4714
*/


import java.util.concurrent.locks.ReentrantLock;

public class syncCon {
	
	private int conveyerNum;
	
	public ReentrantLock accessLock = new ReentrantLock();
	
	public syncCon(int conveyerNum) {
		this.conveyerNum = conveyerNum;
	}
	
	public void inputConnection(int stationNum) {
		System.out.println("Station " + stationNum + ": successfully moves pacakages into station on input conveyer" + this.conveyerNum + ".");
	}
	
	public void outputConnection(int stationNum) {
		System.out.println("Station " + stationNum + ": successfully moves packages out of station on output conveyer " + this.conveyerNum + ".");
	}
}