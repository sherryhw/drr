/**
 * 
 */
package org.imt.drr.model.statistics;

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


  /** flow id. */
  private int flowId;

  /**
   * Default constructor. 
   * 
   * @param packetsCounter
   * @param flowId
   */
  public FlowStatistics(int flowId) {
    this(flowId, 0, 0);
  }
  
  /**
   * Main constructor. 
   * 
   * @param packetsCounter
   * @param flowId
   */
  public FlowStatistics(int flowId, int packetsCounter, int sizeCounter) {
    this.packetsCounter = packetsCounter;
    this.sizeCounter = sizeCounter;
    this.flowId = flowId;
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
  }

  /**
   * Increase counter.
   */
  public void inc() {
    packetsCounter++;
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

}
