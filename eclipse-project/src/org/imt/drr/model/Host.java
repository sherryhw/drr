/**
 * 
 */
package org.imt.drr.model;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class Host implements Node {

  /**
   * Random number generator.
   */
  private RandomData random;
 
  /**
   * Mean of the size of the packet.
   */
  private int packetSizeMean; 

  /**
   * Mean of the arrival time.
   */
  private int arrivalTimeMean; 

  /**
   * Flows number lower bound.
   */
  private int flowsLower; 

  /**
   * Flows number upper bound.
   */
  private int flowsUpper; 
  
  /* (non-Javadoc)
   * @see org.imt.drr.model.Node#getNextPacket()
   */
  @Override
  public Packet getNextPacket() {
    int size = Math.round(random.nextPoisson(packetSizeMean));
    int arrivalTime = Math.round(random.nextPoisson(arrivalTimeMean));
    int flowId = (int) Math.round(random.nextUniform(flowsLower, flowsUpper));
    Packet packet = new Packet(flowId, size, arrivalTime);
    return packet;
  }

  @Override
  public void initialize() {
    random = new RandomDataImpl();
  }
  
  public void initialize(int packetSizeMean, int arrivalTimeMean, 
      int flowsLower, int flowsUpper) { 
    this.packetSizeMean = packetSizeMean;
    this.arrivalTimeMean = arrivalTimeMean;
    this.flowsLower = flowsLower;
    this.flowsUpper = flowsUpper;
    initialize();
  }
  
  /**
   * @return
   */
  public int getPacketSizeMean() {
    return packetSizeMean;
  }

  /**
   * 
   * @param packetSizeMean
   */
  public void setPacketSizeMean(int packetSizeMean) {
    this.packetSizeMean = packetSizeMean;
  }

  /**
   * 
   * @return
   */
  public int getArrivalTimeMean() {
    return arrivalTimeMean;
  }

  /**
   * 
   * @param arrivalTimeMean
   */
  public void setArrivalTimeMean(int arrivalTimeMean) {
    this.arrivalTimeMean = arrivalTimeMean;
  }

  /**
   * 
   * @return
   */
  public int getFlowsLower() {
    return flowsLower;
  }

  /**
   * 
   * @param flowsLower
   */
  public void setFlowsLower(int flowsLower) {
    this.flowsLower = flowsLower;
  }

  /**
   * 
   * @return
   */
  public int getFlowsUpper() {
    return flowsUpper;
  }

  /**
   * 
   * @param flowsUpper
   */
  public void setFlowsUpper(int flowsUpper) {
    this.flowsUpper = flowsUpper;
  }

}
