package org.imt.drr.model;


public class Packet {
private int idFlow, size, arrivalTime;
private final int id;

private static int idCounter=0;

public Packet(int iDFlow, int size, int arrivalTime) {
	super();
	idFlow = iDFlow;
	this.size = size;
	this.arrivalTime = arrivalTime;
	this.id=idCounter++;
}

public int getId(){
  return id;
}

public int getArrivalTime() {
	return arrivalTime;
}

public void setArrivalTime(int arrivalTime) {
	this.arrivalTime = arrivalTime;
}

public int getIdFlow() {
	return idFlow;
}

public int getSize() {
	return size;
}


}
