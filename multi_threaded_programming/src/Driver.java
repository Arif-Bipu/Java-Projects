/*
Name: Arif Bipu
Course: CNT 4714 Fall 2020
Assignment title: Project 2 – Multi-threaded programming in Java
Date: October 4, 2020
Class: CNT 4714
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Driver{
	
	static String fileName = "config.txt";
	static int numberOfStations;
	
	static int[] workLoads;
	static syncCon[] conveyers;
	static Station[] stations;
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println();
		System.out.println("* * * SIMULATION BEGINS * * *");
		System.out.println();
		
		
		
		//Reading file
		File file = new File(fileName);
		Scanner read = new Scanner(file);
		
		//gets input from config
		numberOfStations = read.nextInt();
		workLoads = new int[numberOfStations];
		
		//Conveyers 
		conveyers = new syncCon[numberOfStations];
		
		for (int i = 0; i < conveyers.length; i++) {
			conveyers[i] = new syncCon(i);
		}
		
		//Creating station
		stations = new Station[numberOfStations];
		
		//executing the simulation
		ExecutorService ConSimulation = Executors.newFixedThreadPool(numberOfStations);
		
		for (int i = 0; i < workLoads.length && i < conveyers.length && i < stations.length; i++)
		{
			workLoads[i] = read.nextInt();
			
			stations[i] = new Station(workLoads[i], i, numberOfStations);
			
			stations[i].setInput(conveyers[stations[i].getInputConNum()]);
			stations[i].setOutput(conveyers[stations[i].getOutputConNum()]);
			
			
			
			try {
				ConSimulation.execute(stations[i]);
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		
		read.close();
		
		ConSimulation.shutdown();
	
		
	
	
	}
	
	
	
	
	
	
}