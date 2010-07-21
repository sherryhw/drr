package org.imt.drr.model;

/**
 * 
 * * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 */
public class Packet {

  private int idFlow, size, interarrivalTime, arrivalTimeInRouter, delayInQueue, cumulativeDelayInQueue;
  private int departureTime;

  private final int id;
  
  private static int idCounter = 0;
  
  public Packet(int idFlow, int size, int interarrivalTime) {
  	super();
  	this.idFlow = idFlow;
  	this.size = size;
  	this.interarrivalTime = interarrivalTime;
  	this.id = idCounter++;
  	this.departureTime = Integer.MIN_VALUE;
  	this.cumulativeDelayInQueue = 0;
  }
  
  /**
   * @return the departureTime
   */
  public int getDepartureTime() {
    return departureTime;
  }

  /**
   * @param departureTime the departureTime to set
   */
  public void setDepartureTime(int departureTime) {
    this.departureTime = departureTime;
  }
  
  /**
   * get the delay spent in the last router
   */
  public int getDelayInQueue() {
    return delayInQueue;
  }

  /**
   * set delay spent in the last router
   */
  public void setDelayInQueue(int delay) {
    this.delayInQueue = delay;
  }
  
  /**
   * get the delay spent in the last router
   */
  public int getCumulativeDelayInQueue() {
    return cumulativeDelayInQueue;
  }

  /**
   * add delay to the cumulative delay spent in the last router
   */
  public void addCumulativeDelayInQueue(int delay) {
    this.cumulativeDelayInQueue += delay;
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
  
  /** 
   * String representation.
   * 
   */
  public String toString(){
    return "{id = " + id + ", idFlow = " + idFlow + ", size = " + 
      size + ", interarrivalTime = " + 
      interarrivalTime + ", arrivalTimeInRouter = " + arrivalTimeInRouter + ", delayInQueue = " + delayInQueue + ", departureTimeFromRouter = " + departureTime + "}";
  }

}
