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

  /**
   * Default constructor. 
   * 
   * @param packetsCounter
   * @param flowId
   */
  public FlowStatistics(int flowId) {
    this(0, flowId);
  }
  
  /**
   * Main constructor. 
   * 
   * @param packetsCounter
   * @param flowId
   */
  public FlowStatistics(int packetsCounter, int flowId) {
    this.packetsCounter = packetsCounter;
    this.flowId = flowId;
  }
  
  /**
   * Increase counter.
   */
  public void inc() {
    
  }
  
  /** packets counter. */
  private int packetsCounter; 
  
  /** flow id. */
  private int flowId;

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
  
}
