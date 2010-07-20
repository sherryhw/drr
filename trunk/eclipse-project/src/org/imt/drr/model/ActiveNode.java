/**
 * 
 */
package org.imt.drr.model;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public interface ActiveNode extends Node {
  
  /**
   * Proceed next event for active node.
   */
  public void proceedNextEvent();

}
