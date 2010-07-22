/**
 * 
 */
package org.imt.drr.simulators;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.imt.drr.model.Constants;
import org.imt.drr.model.Host;
import org.imt.drr.model.HostType;
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
  
  /** Array of hosts. */
  private Vector<CombinedHost> hosts;
  
  /** Statistics */
  private Statistics stats;

  /** Router */
  private Router finalRouter;

  /**
   * Duration of the simulation in ticks. 
   */
  public int duration;

  private int numberOfExperiment;
  
  /* (non-Javadoc)
   * @see org.imt.drr.model.Simulator#execute()
   */
  @Override
  public void execute() {
    logger.warn("Execute simulation....");   
    int j = 0;
    while (finalRouter.getCurrentSimulationTime() < duration) {
      if (Constants.USE_LOG) {
        logger.info("#######################################################################");
        logger.info("#####################SIMULATION STEP = " + j);
        logger.info("#####################           TIME = " + finalRouter.getCurrentSimulationTime());
        logger.info("#######################################################################");
      }
      for (int i = 0; i < routers.size(); i++) {
        hosts.get(i).proceedNextEvent();
        routers.get(i).proceedNextEvent();
      }
      j++;
      if (j == Integer.MAX_VALUE - 1) break;
    }
    logger.warn("End of simulation in " + finalRouter.getCurrentSimulationTime() + " ms.....");   
    logger.warn((new Date()));
    double[] through = new double[Host.DEFAULT_NUMBER_OF_FLOWS * routers.size()];
    for (Integer key : stats.getFlowsStatistics().keySet()) {
      double throughout = stats.getThroughput(key);
      through[key] = throughout;
      long numberOfPackets = stats.getFlowsStatistics().get(key).getPacketsCounter();
      double averageDelay = stats.getAverageDelay(key) < 0.001 ? 0 : stats.getAverageDelay(key);
      logger.warn("####### " + key + " flow troughput = " + throughout 
          + " averageDelay = " + averageDelay  
          + " numberOfPackets = " + numberOfPackets + " #######");
      ArrayList<Double> averages = stats.getFlowsStatistics().get(key).getAverageDelays();
      String log = "Averages[";
      for (Double average : averages) {
        log += average + ", ";
      }
      log += "]";
      logger.warn(log);
    }
    StatsWriter.writeResults(Constants.OUTPUT_FILE_NAME, through, numberOfExperiment);
  }

  /* (non-Javadoc)
   * @see org.imt.drr.model.Simulator#initialize()
   */
  @Override
  public void initialize() {
    initialize(2000000, RouterType.FIFO, 1, 0);
  }

  /**
   * Initialization. 
   * 
   * @param duration - of the simulation
   * @param type - type of router
   * @param countRouters - number of routers
   */
  public void initialize(int duration, RouterType type, int countRouters, int numberOfExperiment) {
    logger.info("Initialization of the FourRouterSimulator....");   
    this.numberOfExperiment = numberOfExperiment;
    this.duration = duration;
    hosts = new Vector<CombinedHost>();
    routers = new Vector<Router>();
    CombinedHost host;
    Router router = null;
    Vector<Node> sources; 
    Statistics mainStats = null;
    stats = null;
    for (int i = 0; i < countRouters; i++) {
      host = new CombinedHost();
      host.initialize(Host.DEFAULT_PACKET_SIZE_MAX, Host.DEFAULT_ARRIVAL_TIME_MEAN, 
          i * Host.DEFAULT_NUMBER_OF_FLOWS, Host.DEFAULT_NUMBER_OF_FLOWS, HostType.RANDOM_SIZE, Constants.USE_BASTARD_FLOW);
      hosts.add(host);
      sources = new Vector<Node> (); 
      sources.add(host);
      if (router != null) {
        sources.add(router);
      }
      if (i == countRouters - 1) {
        mainStats = new Statistics();
        stats = mainStats;
      } else {
        mainStats = null;
      }
      switch (type) {
        case FIFO: 
          router = new FifoRouter(sources, Router.DEFAULT_BANDWIDTH, mainStats, "FifoRouter#" + i, false);
          break;
        case DRR:
          router = new DrrRouter(sources, Router.DEFAULT_BANDWIDTH, Host.DEFAULT_NUMBER_OF_FLOWS * countRouters, mainStats, "DrrRouter#" + i, false);
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
