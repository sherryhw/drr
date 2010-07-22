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
import org.imt.drr.model.Node;
import org.imt.drr.model.Router;
import org.imt.drr.model.Simulator;
import org.imt.drr.model.drr.DrrRouter;
import org.imt.drr.model.drr.NewDrrRouter;
import org.imt.drr.model.fifo.CombinedHost;
import org.imt.drr.model.fifo.FifoRouter;
import org.imt.drr.model.sfq.SFQRouter;
import org.imt.drr.model.statistics.Statistics;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class OneRouterSimulator implements Simulator {

  /** Logger */
  static Logger logger = Logger.getLogger(OneRouterSimulator.class);

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

  private int numberOfExperiment;
  
  /* (non-Javadoc)
   * @see org.imt.drr.model.Simulator#execute()
   */
  @Override
  public void execute() {
    logger.warn("Execute simulation....");
    logger.warn((new Date()));
    int j = 0;
    while (router.getCurrentSimulationTime() < duration) {
    //for (int i = 0; i < duration; i++ ) {
      if (Constants.USE_LOG) {
        logger.info("#######################################################################");
        logger.info("#####################SIMULATION STEP = " + j);
        logger.info("#####################           TIME = " + router.getCurrentSimulationTime());
        logger.info("#######################################################################");
      }
      host.proceedNextEvent();
      router.proceedNextEvent();
      j++;
      if (j == Integer.MAX_VALUE - 1) break;
    }
    logger.warn("End of simulation in " + stats.getTime() + " ms.....");   
    logger.warn((new Date()));
    double[] through = new double[Host.DEFAULT_NUMBER_OF_FLOWS];
    for (Integer key : stats.getFlowsStatistics().keySet()) {
      double throughout = stats.getThroughput(key);
      through[key] = throughout * 1000;
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
    initialize(2000000, RouterType.FIFO, 0);
  }

  /**
   * Initialization. 
   * 
   * @param duration - of the simulation
   * @param type - type of router
   */
  public void initialize(int duration, RouterType type, int numberOfExperiment) {
    this.numberOfExperiment = numberOfExperiment;
    logger.info("Initialization of the OneRouterSimulator....");   
    this.duration = duration;
    host = new CombinedHost();
    host.initialize();
    Vector<Node> sources = new Vector<Node> (); 
    sources.add(host);
    stats = new Statistics();
    switch (type) {
      case FIFO: 
        router = new FifoRouter(sources, Router.DEFAULT_BANDWIDTH, stats, "FifoRouter", false);
        break;
      case DRR:
        router = new DrrRouter(sources, Router.DEFAULT_BANDWIDTH, Host.DEFAULT_NUMBER_OF_FLOWS, stats, "DrrRouter", false);
        break;
      case NEWDRR:
        router = new NewDrrRouter(sources, Router.DEFAULT_BANDWIDTH, Host.DEFAULT_NUMBER_OF_FLOWS, stats, "NEWDrrRouter", false);
        break;
      case SFQ:
        router = new SFQRouter(sources, Router.DEFAULT_BANDWIDTH, Host.DEFAULT_NUMBER_OF_FLOWS, stats, "SFQRouter", false);
        break;
    }
    router.initialize();
    logger.info("End of the initialization of the OneRouterSimulator....");   
  }
  
}
