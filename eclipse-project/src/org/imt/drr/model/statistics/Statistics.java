/**
 * 
 */
package org.imt.drr.model.statistics;

import java.util.Vector;

/**
 * 
 * Gather statistics for all of the flows.
 * 
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class Statistics {
 
  /**
   * Default constructor.
   */
  public Statistics() {
    flowsStatistics = new Vector<FlowStatistics>();
    time = 0;
  }

  /** array of flow statistics  */
  private Vector<FlowStatistics> flowsStatistics;

  /** time */
  private int time; 
  
  /**
   * @return the flowsStatistics
   */
  public Vector<FlowStatistics> getFlowsStatistics() {
    return flowsStatistics;
  }

  /**
   * @param time the time to set
   */
  public void setTime(int time) {
    if (time > 0) this.time = time;
  }

  /**
   * @return the time
   */
  public int getTime() {
    return time;
  } 


}
