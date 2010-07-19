package org.imt.drr.model;

/**
 * 
 * This class describes packet.
 * 
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class Packet {
  

  /**
   * idFlow - id of the flow
   * size - size of the packet
   * arrivalTime - planned time for packet's arrival
   */
  private int idFlow, size, arrivalTime;
  
  /**
   * Default constructor.
   * 
   * @param idFlow
   * @param size
   * @param arrivalTime
   */
  public Packet(int idFlow, int size, int arrivalTime) {
  	super();
  	this.idFlow = idFlow;
  	this.size = size;
  	this.arrivalTime = arrivalTime;
  }
  
  /**
   * 
   * @return
   */
  public int getArrivalTime() {
  	return arrivalTime;
  }
  
  /**
   * 
   * @param arrivalTime
   */
  public void setArrivalTime(int arrivalTime) {
  	this.arrivalTime = arrivalTime;
  }
  
  /**
   * 
   * @return
   */
  public int getIdFlow() {
  	return idFlow;
  }
  
  /**
   * 
   * @return
   */
  public int getSize() {
  	return size;
  }

}
