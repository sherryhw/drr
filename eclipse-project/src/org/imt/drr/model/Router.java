/**
 * 
 */
package org.imt.drr.model;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public abstract class Router implements Node {

  /**
   * The main process of router. 
   */
  abstract public void proceedNextEvent();

}
