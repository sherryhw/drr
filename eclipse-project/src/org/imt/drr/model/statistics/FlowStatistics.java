/**
 * 
 */
package org.imt.drr.model.statistics;

import java.util.ArrayList;

import org.imt.drr.model.Constants;

/**
 * A simple entity showing the statistics of the flow.
 * 
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class FlowStatistics {

  /** packets counter. */
  private int packetsCounter; 

  /** packets counter. */
  private int sizeCounter; 

  /** total delay. */
  private int totalDelay; 
  
  /** average delays */
  private ArrayList<Float> averageDelays; 

  /** part packet counter */
  private int partPacketCounter;
  
  /** part delay*/
  private int partDelay;

  /** flow id. */
  private int flowId;

  /**
   * Default constructor. 
   * 
   * @param packetsCounter
   * @param flowId
   */
  public FlowStatistics(int flowId) {
    this(flowId, 0, 0, 0);
  }
  
  /**
   * Main constructor. 
   * 
   * @param packetsCounter
   * @param flowId
   */
  public FlowStatistics(int flowId, int packetsCounter, int sizeCounter, int totalDelay) {
    this.packetsCounter = packetsCounter;
    this.sizeCounter = sizeCounter;
    this.flowId = flowId;
    this.totalDelay = totalDelay;
    this.averageDelays = new ArrayList<Float>();
    this.partDelay = 0;
    this.partPacketCounter = 0;
  }
  
  /**
   * @return the totalDelay
   */
  public int getTotalDelay() {
    return totalDelay;
  }

  /**
   * @param totalDelay the totalDelay to set
   */
  public void incTotalDelay(int increase) {
    this.totalDelay += increase;
    this.partDelay += increase;
  }

  /**
   * Increase counter.
   */
  public void inc() {
    packetsCounter++;
    partPacketCounter++;
    if (partPacketCounter >= Constants.PART_AVERAGE) {
      averageDelays.add((float)partDelay/partPacketCounter);
      partPacketCounter = 0;
      partDelay = 0;
    }
  }

  /**
   * Increase size.
   */
  public void incSize(int size) {
    this.sizeCounter += size; 
    inc();
  }
  
  /**
   * @param packetsCounter the packetsCounter to set
   */
  public void setPacketsCounter(int packetsCounter) {
    this.packetsCounter = packetsCounter;
  }

  /**
   * @return the packetsCounter
   */
  public int getPacketsCounter() {
    return packetsCounter;
  }

  /**
   * @param flowId the flowId to set
   */
  public void setFlowId(int flowId) {
    this.flowId = flowId;
  }

  /**
   * @return the flowId
   */
  public int getFlowId() {
    return flowId;
  }

  /**
   * @return the sizeCounter
   */
  public int getSizeCounter() {
    return sizeCounter;
  }

  /**
   * @param sizeCounter the sizeCounter to set
   */
  public void setSizeCounter(int sizeCounter) {
    this.sizeCounter = sizeCounter;
  }

  /**
   * @return the averageDelays
   */
  public ArrayList<Float> getAverageDelays() {
    return averageDelays;
  }

}
