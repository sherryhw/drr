/**
 * 
 */
package org.imt.drr.simulators;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.imt.drr.model.Node;
import org.imt.drr.model.Router;
import org.imt.drr.model.Simulator;
import org.imt.drr.model.fifo.CombinedHost;
import org.imt.drr.model.fifo.FifoRouter;
import org.imt.drr.model.statistics.Statistics;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class SimpleFifoSimulator implements Simulator {

  /** Logger */
  static Logger logger = Logger.getLogger(SimpleFifoSimulator.class);

  /** An instance of fifo router. */
  private Router router;

  /** An instance of host. */
  private CombinedHost host;
  
  /** Statistics */
  private Statistics stats;
  
  /**
   * Duration of the simulation in ticks. 
   */
  public int duration;
  
  /* (non-Javadoc)
   * @see org.imt.drr.model.Simulator#execute()
   */
  @Override
  public void execute() {
    logger.warn("Execute simulation....");   
    int j = 0;
    while (router.getCurrentSimulationTime() < duration) {
    //for (int i = 0; i < duration; i++ ) {
      logger.info("#######################################################################");
      logger.info("#####################SIMULATION STEP = " + j);
      logger.info("#####################           TIME = " + router.getCurrentSimulationTime());
      logger.info("#######################################################################");
      host.proceedNextEvent();
      router.proceedNextEvent();
      j++;
      if (j > duration / 40) break;
    }
    logger.warn("End of simulation in " + router.getCurrentSimulationTime() + " ms.....");   
    for (int i = 0; i < stats.getFlowsStatistics().size(); i++) {
      float throughout = stats.getThroughput(i);
      logger.warn("############## " + i + " flow troughput is " + throughout + " ################");
    }
  }

  /* (non-Javadoc)
   * @see org.imt.drr.model.Simulator#initialize()
   */
  @Override
  public void initialize() {
    logger.info("Initialization of the FifoSimulator....");   
    host = new CombinedHost();
    host.initialize();
    Vector<Node> sources = new Vector<Node> (); 
    sources.add(host);
    stats = new Statistics();
    router = new FifoRouter(sources, Router.DEFAULT_BANDWIDTH, stats, "MainRouter", true);
    router.initialize();
    logger.info("End of the initialization of the FifoSimulator....");   
  }

  /**
   * Initialization. 
   * 
   * @param duration - of the simulation
   */
  public void initialize(int duration) {
    this.duration = duration;
    initialize();
  }
  
}
