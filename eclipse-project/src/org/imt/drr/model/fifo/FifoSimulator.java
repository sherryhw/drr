/**
 * 
 */
package org.imt.drr.model.fifo;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.imt.drr.model.Host;
import org.imt.drr.model.Node;
import org.imt.drr.model.Simulator;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class FifoSimulator implements Simulator {

  /**
   * Logger
   */
  static Logger logger = Logger.getLogger(FifoSimulator.class);

  /** An instance of fifo router. */
  public FifoRouter router;

  /** An instance of host. */
  public Host host;

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
    host = new Host();
    host.initialize();
    Vector<Node> sources = new Vector<Node> (); 
    sources.add(host);
    router = new FifoRouter(sources, 1);
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
