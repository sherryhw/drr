package org.imt.drr.model;

/**
 *
 * The main simulator class.
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public interface Simulator {
  
  /**
   * Initialize the model. 
   */
  public void initialize();
  
  /**
   * Execute the simulation.
   */
  public void execute();

}
