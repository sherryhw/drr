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
  private long packetsCounter; 

  /** packets counter. */
  private long sizeCounter; 

  /** total delay. */
  private long totalDelay; 
  
  /** average delays */
  private ArrayList<Double> averageDelays; 

  /** part packet counter */
  private long partPacketCounter;
  
  /** part delay*/
  private long partDelay;

  /** flow id. */
  private long flowId;

  /**
   * Default constructor. 
   * 
   * @param packetsCounter
   * @param flowId
   */
  public FlowStatistics(long flowId) {
    this(flowId, 0, 0, 0);
  }
  
  /**
   * Main constructor. 
   * 
   * @param packetsCounter
   * @param flowId
   */
  public FlowStatistics(long flowId, long packetsCounter, long sizeCounter, long totalDelay) {
    this.packetsCounter = packetsCounter;
    this.sizeCounter = sizeCounter;
    this.flowId = flowId;
    this.totalDelay = totalDelay;
    this.averageDelays = new ArrayList<Double>();
    this.partDelay = 0;
    this.partPacketCounter = 0;
  }
  
  /**
   * @return the totalDelay
   */
  public long getTotalDelay() {
    return totalDelay;
  }

  /**
   * @param totalDelay the totalDelay to set
   */
  public void incTotalDelay(long increase) {
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
      averageDelays.add((double)partDelay/partPacketCounter);
      partPacketCounter = 0;
      partDelay = 0;
    }
  }

  /**
   * Increase size.
   */
  public void incSize(long size) {
    this.sizeCounter += size; 
    inc();
  }
  
  /**
   * @param packetsCounter the packetsCounter to set
   */
  public void setPacketsCounter(long packetsCounter) {
    this.packetsCounter = packetsCounter;
  }

  /**
   * @return the packetsCounter
   */
  public long getPacketsCounter() {
    return packetsCounter;
  }

  /**
   * @param flowId the flowId to set
   */
  public void setFlowId(long flowId) {
    this.flowId = flowId;
  }

  /**
   * @return the flowId
   */
  public long getFlowId() {
    return flowId;
  }

  /**
   * @return the sizeCounter
   */
  public long getSizeCounter() {
    return sizeCounter;
  }

  /**
   * @param sizeCounter the sizeCounter to set
   */
  public void setSizeCounter(long sizeCounter) {
    this.sizeCounter = sizeCounter;
  }

  /**
   * @return the averageDelays
   */
  public ArrayList<Double> getAverageDelays() {
    return averageDelays;
  }

}
