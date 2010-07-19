package org.imt.drr.model;

/**
 * 
 * * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 */
public class Packet {
private int idFlow, size, interarrivalTime, arrivalTimeInRouter;

private final int id;

private static int idCounter=0;

public Packet(int iDFlow, int size, int interarrivalTime) {
	super();
	idFlow = iDFlow;
	this.size = size;
	this.interarrivalTime = interarrivalTime;
	this.id=idCounter++;
}

public int getId(){
  return id;
}

public int getInterarrivalTime() {
	return interarrivalTime;
}

public void setInterarrivalTime(int interarrivalTime) {
	this.interarrivalTime = interarrivalTime;
}

public int getIdFlow() {
	return idFlow;
}

public int getSize() {
	return size;
}

public int getArrivalTimeInRouter() {
  return arrivalTimeInRouter;
}

public void setArrivalTimeInRouter(int arrivalTimeInRouter) {
  this.arrivalTimeInRouter = arrivalTimeInRouter;
}
}
