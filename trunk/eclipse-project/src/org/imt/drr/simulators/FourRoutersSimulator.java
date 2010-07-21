/**
 * 
 */
package org.imt.drr.simulators;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.imt.drr.model.Host;
import org.imt.drr.model.Node;
import org.imt.drr.model.Router;
import org.imt.drr.model.Simulator;
import org.imt.drr.model.drr.DrrRouter;
import org.imt.drr.model.fifo.CombinedHost;
import org.imt.drr.model.fifo.FifoRouter;
import org.imt.drr.model.statistics.Statistics;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class FourRoutersSimulator implements Simulator {

  /** Logger */
  static Logger logger = Logger.getLogger(FourRoutersSimulator.class);

  /** Array of routers. */
  private Vector<Router> routers;
  
  /** Final router. */
  private Router finalRouter;

  /** Array of hosts. */
  private Vector<CombinedHost> hosts;
  
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
    while (finalRouter.getCurrentSimulationTime() < duration) {
    //for (int i = 0; i < duration; i++ ) {
      logger.info("#######################################################################");
      logger.info("#####################SIMULATION STEP = " + j);
      logger.info("#####################           TIME = " + finalRouter.getCurrentSimulationTime());
      logger.info("#######################################################################");
      //host.proceedNextEvent();
      //router.proceedNextEvent();
      j++;
      if (j > duration / 40) break;
    }
    logger.warn("End of simulation in " + finalRouter.getCurrentSimulationTime() + " ms.....");   
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
    initialize(2000000, RouterType.FIFO, 1);
  }

  /**
   * Initialization. 
   * 
   * @param duration - of the simulation
   * @param type - type of router
   * @param countRouters - number of routers
   */
  public void initialize(int duration, RouterType type, int countRouters) {
    logger.info("Initialization of the FourRouterSimulator....");   
    this.duration = duration;
    hosts = new Vector<CombinedHost>();
    routers = new Vector<Router>();
    CombinedHost host;
    Router router = null;
    Vector<Node> sources; 
    for (int i = 0; i < countRouters; i++) {
      host = new CombinedHost();
      hosts.add(host);
      sources = new Vector<Node> (); 
      sources.add(host);
      if (router != null) {
        sources.add(router);
      }
      switch (type) {
      case FIFO: 
        router = new FifoRouter(sources, Router.DEFAULT_BANDWIDTH, null, "FifoRouter", true);
        break;
      case DRR:
        router = new DrrRouter(sources, Router.DEFAULT_BANDWIDTH, Host.DEFAULT_NUMBER_OF_FLOWS * countRouters, null, "DrrRouter", false);
        break;
      }
      if (i == countRouters - 1) {
        finalRouter = router;
      }
      router.initialize();
      routers.add(router);
    }
    logger.info("End of the initialization of the OneRouterSimulator....");   
  }
  
}
