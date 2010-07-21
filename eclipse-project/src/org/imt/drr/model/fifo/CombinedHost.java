/**
 * 
 */
package org.imt.drr.model.fifo;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.imt.drr.model.ActiveNode;
import org.imt.drr.model.Host;
import org.imt.drr.model.HostType;
import org.imt.drr.model.Node;
import org.imt.drr.model.Packet;

/**
 * Combined flow to produce flows with different rates.
 * 
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class CombinedHost implements ActiveNode {
  
  /** Logger */
  static Logger logger = Logger.getLogger(CombinedHost.class);

  /** Array of flows to be plugged to the combined host. */
  private Vector<Node> flows;

  /** Fifo router. */
  private FifoRouter router;
  
  /** Default initialization. */
  public void initialize() {
    initialize(Host.DEFAULT_PACKET_SIZE_MAX, Host.DEFAULT_ARRIVAL_TIME_MEAN, 0, Host.DEFAULT_NUMBER_OF_FLOWS, HostType.RANDOM_SIZE, false);
  }
  
  /**
   * Initialization.
   * 
   * @param packetSizeMax
   * @param arrivalTimeMean
   * @param flowsLower
   * @param flowsCount
   * @param type
   * @param includeIllBehaved
   */
  public void initialize(int packetSizeMax, int arrivalTimeMean, 
      int flowsLower, int flowsCount, HostType type, boolean includeIllBehaved) { 
    logger.info("Initializing combined host");
    flows = new Vector<Node>();
    for (int i = 0; i < flowsCount; i++) {
      Host flow = new Host();
//      if (includeIllBehaved && (i == 0)) {
//        flow.initialize(packetSizeMax, arrivalTimeMean / 3, i, 1, type);
//      } else {
        flow.initialize(packetSizeMax, arrivalTimeMean, i, 1, type);
//      }
      flows.add(flow);
    }
    logger.info("Size of flows in combined host " + flows.size());
    router = new FifoRouter(flows, Integer.MAX_VALUE, null, "Host", false);
    router.initialize();
  }

  /**
   * 
   */
  @Override 
  public Packet getNextPacket() {
    logger.debug("Getting next packet from the fifo router....");
    return router.getNextPacket();
  }

  @Override
  public void proceedNextEvent() {
    router.proceedNextEvent();
  }
  
  /**
   * 
   */
  public String toString() {
    return "CombinedHost";
  }
}
