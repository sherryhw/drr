/**
 * 
 */
package org.imt.drr.simulators;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.imt.drr.model.Node;
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
  private FifoRouter router;

  /** An instance of host. */
  private CombinedHost host;
  
  /** Statistics */
  private Statistics stats;
  
  /**
   * Number of loops to proceed. 
   */
  public int duration;
  
  /* (non-Javadoc)
   * @see org.imt.drr.model.Simulator#execute()
   */
  @Override
  public void execute() {
    logger.info("Execute simulation....");   
    for (int i = 0; i < duration; i++ ) {
      logger.info("#####################SIMULATION STEP i = " + i + "#####################");
      host.proceedNextEvent();
      router.proceedNextEvent();
    }
    logger.info("End of simulation....");   
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
    router = new FifoRouter(sources, 1);
    router.initialize();
    stats = new Statistics();
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
