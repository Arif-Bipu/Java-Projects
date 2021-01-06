/*
Name: Arif Bipu
Course: CNT 4714 Fall 2020
Assignment title: Project 2 – Multi-threaded programming in Java
Date: October 4, 2020
Class: CNT 4714
*/

import java.util.Random;

public class Station implements Runnable {
	
	private static int maxNumStations;
	
	private int workLoad = 0;
	private int inputConNum;
	private int outputConNum;
	private int stationNum;
	
	private syncCon input;
	private syncCon output;
	
	private static Random random = new Random();
	
	//random integer for the sleep simulation
	public static int getRandomInt(int min, int max) {
		return random.nextInt(max-min+1) +min;
	}
	
	
	//Constructor creation
	
	public Station(int workLoad, int stationNum, int maxNumStations) {
		
		Station.maxNumStations = maxNumStations;
		this.workLoad = workLoad;
		this.stationNum = stationNum;
		
		this.setInputConNum();
		this.setOutputConNum();
		
		System.out.println("Routing Station " + stationNum + ": Workload set. Station " + this.stationNum + " has a total of " + this.workLoad + " package groups to move.");
		
	}
	
	
	@Override
	public void run() {
		
		//running until the workload is 0
		while(this.workLoad != 0) 
		{
			if(input.accessLock.tryLock()) 
			{
				System.out.println("Station " + this.stationNum + ": holds lock on input conveyer " + this.inputConNum + ".");
				
				
				if(output.accessLock.tryLock()) 
				{
					System.out.println("Station " + this.stationNum + ": holds lock on output conveyer " + this.outputConNum + ".");
					doWork();
					System.out.println();
				}
				else 
				{
					System.out.println("Station " + this.stationNum + ": unable to lock output conveyer - releasing lock on input conveyer " + this.inputConNum + ".");
					input.accessLock.unlock();

					try 
					{
						Thread.sleep(getRandomInt(1000,1500));
					}
					catch(InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				
				//releases locks
				if (input.accessLock.isHeldByCurrentThread())
				{
					System.out.println("Station " + this.stationNum + ": unlocks input conveyer " + this.inputConNum + ".");
					input.accessLock.unlock();
				}
				
				if (output.accessLock.isHeldByCurrentThread())
				{
					System.out.println("Station " + this.stationNum + ": unlocks output conveyer " + this.inputConNum + ".");
					output.accessLock.unlock();
				}
				
				try {
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		System.out.println();
		System.out.println("* * Station " + stationNum + ": Workload successfully completed. * *");
		System.out.println();
		System.out.println();
		
		Station.maxNumStations--;
		
		//If done with all stations
		if(Station.maxNumStations == 0)
		{
			System.out.println();
			System.out.println("* * * * ALL WORKLOADS COMPLETE * * * SIMULATION ENDS * * * *");
			
		}
	}
	
	//actual do work function that sets the proper packages
	public void doWork() 
	{
		this.input.inputConnection(this.stationNum);
		this.output.outputConnection(this.stationNum);
		
		this.workLoad--;
		
		System.out.println("Station " + this.stationNum + ": has " + this.workLoad + " package groups left to move.");
	}
	
	
	public void setInputConNum() 
	{
		
		if(stationNum == 0) 
		{
			this.inputConNum = 0;
		}
		else 
		{
			this.inputConNum = this.stationNum - 1;
		}
		
		System.out.println("Station " + stationNum + ": input connection is set to conveyer number " + this.inputConNum + ".");
	}
	
	public int getInputConNum() {
		return inputConNum;
	}
	
	public int getOutputConNum() {
		return outputConNum;
	}
	
	public void setOutputConNum() 
	{
		if(this.stationNum == 0) 
		{
			this.outputConNum = Station.maxNumStations - 1;
		}
		else 
		{
			this.outputConNum = this.stationNum;
		}
		
		System.out.println("Station " + stationNum + ": output connection is set to conveyer number " + this.inputConNum + ".");
		
	}
	
	public syncCon getInput() 
	{
		return input;
	}
	
	public void setInput(syncCon input) 
	{
		this.input = input;
	}
	
	public syncCon getOutput() {
		return output;
	}
	
	public void setOutput(syncCon output) {
		this.output = output;
	}
	
	
	
}