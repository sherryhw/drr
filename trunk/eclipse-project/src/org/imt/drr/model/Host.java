/**
 * 
 */
package org.imt.drr.model;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.log4j.Logger;

/**
 * This is just the simple host that generates flows with the same parameters. 
 * 
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class Host implements Node {

  /** Logger */
  static Logger logger = Logger.getLogger(Host.class);

  /** Random number generator for the size. */
  private static RandomData randomSize = new RandomDataImpl();

  /** Random number generator for the arrival times. */
  private static RandomData randomArrival = new RandomDataImpl();

  /** Random number generator for the flow Id. */
  private static RandomData randomFlowId = new RandomDataImpl();

  /** Some constants. */
  public static final int DEFAULT_PACKET_SIZE_MAX = Constants.DEFAULT_PACKET_SIZE_MAX;  
  public static final int DEFAULT_NUMBER_OF_FLOWS = Constants.DEFAULT_NUMBER_OF_FLOWS;
  public static final int DEFAULT_ARRIVAL_TIME_MEAN = Constants.DEFAULT_ARRIVAL_TIME_MEAN;
  
  /** Mean of the size of the packet. */
  private int packetSizeMax; 
  
  /** Mean of the arrival time. */
  private int arrivalTimeMean; 

  /** Flows number lower bound. */
  private int flowsLower; 

  /** Number of flows. */
  private int flowsCount; 
  
  /** type of host. */
  private HostType type;
  
  /* (non-Javadoc)
   * @see org.imt.drr.model.Node#getNextPacket()
   */
  @Override
  public Packet getNextPacket() {
    int size;
    if (type == HostType.RANDOM_SIZE) {
      size = (int) Math.round(randomSize.nextUniform(0, packetSizeMax));
    } else {
      size = packetSizeMax;
    }
    int flowId = (int) randomFlowId.nextUniform(flowsLower, flowsLower + flowsCount) ;
    int arrivalTime = (int) Math.round(randomArrival.nextExponential(arrivalTimeMean * flowsCount)) + 1;
    Packet packet = new Packet(flowId, size, arrivalTime);
    //logger.info("Getting next packet from the host " + packet);
    return packet;
  }

  @Override
  public void initialize() {
    //here I setup some constant default values
    flowsLower = 0;
    flowsCount = DEFAULT_NUMBER_OF_FLOWS;
    packetSizeMax = DEFAULT_PACKET_SIZE_MAX;
    arrivalTimeMean = DEFAULT_ARRIVAL_TIME_MEAN;
    //create random generators for all
//    logger.info("Create random generators");
//    randomArrival = new RandomDataImpl();
//    randomFlowId = new RandomDataImpl();
//    randomSize = new RandomDataImpl();
    type = HostType.RANDOM_SIZE;
  }
  
  /**
   * Initialize host with next parameters
   * 
   * @param packetSizeMax - packet size maximum
   * @param arrivalTimeMean - mean of arrival time 
   * @param flowsLower - flows lower bound
   * @param flowsCount - count of flows
   */
  public void initialize(int packetSizeMax, int arrivalTimeMean, 
      int flowsLower, int flowsCount, HostType type) { 
    initialize();
    this.packetSizeMax = packetSizeMax;
    this.arrivalTimeMean = arrivalTimeMean;
    this.flowsLower = flowsLower;
    this.flowsCount = flowsCount;
    this.type = type;
  }
  
  /**
   * @return
   */
  public int getPacketSizeMax() {
    return packetSizeMax;
  }

  /**
   * 
   * @param packetSizeMax
   */
  public void setPacketSizeMax(int packetSizeMax) {
    this.packetSizeMax = packetSizeMax;
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
   * @return the type
   */
  public HostType getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(HostType type) {
    this.type = type;
  }
  
  /**
   * Returns string.
   */
  public String toString() {
    return "Host";
  }
}
