/**
 * 
 */
package org.imt.drr.model.statistics;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.imt.drr.Runner;
import org.imt.drr.model.Packet;

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
   * Logger
   */
  static Logger logger = Logger.getLogger(Runner.class);

  /**
   * Default constructor.
   */
  public Statistics() {
    flowsStatistics = new HashMap<Integer, FlowStatistics>();
    time = 0;
  }
  
  /** array of flow statistics  */
  private HashMap<Integer, FlowStatistics> flowsStatistics;

  /** time */
  private int time; 

  /** 
   * Returns throughput value for flow id. 
   * 
   * @param flowId
   * @return
   */
  public float getThroughput(int flowId) {
    if (flowsStatistics.get(new Integer(flowId)) != null) {
        return ((float)flowsStatistics.get(new Integer(flowId)).getSizeCounter()) / time; }
      else return 0;
  }

  /** 
   * Returns average delay value for flow id. 
   * 
   * @param flowId
   * @return
   */
  public float getAverageDelay(int flowId) {
    if (flowsStatistics.get(new Integer(flowId)) != null) {
        return ((float)flowsStatistics.get(new Integer(flowId)).getTotalDelay()) / (float)flowsStatistics.get(new Integer(flowId)).getPacketsCounter(); }
      else return 0;
  }

  /**
   * Count the packet. 
   * 
   * @param packet
   */
  public void countPacket(Packet packet) {
    FlowStatistics flow = flowsStatistics.get(packet.getIdFlow());
    if (flow == null) {
      flow = new FlowStatistics(packet.getIdFlow());
      flowsStatistics.put(packet.getIdFlow(), flow);
    }
    flow.incSize(packet.getSize());
    flow.incTotalDelay(packet.getCumulativeDelayInQueue());
    logger.debug(packet.getCumulativeDelayInQueue());
  }
  
  /**
   * @return the flowsStatistics
   */
  public HashMap<Integer, FlowStatistics> getFlowsStatistics() {
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
