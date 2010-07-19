/**
 * 
 */
package org.imt.drr.model.drr;



import java.util.Collection;

import org.imt.drr.model.Node;
import org.imt.drr.model.Router;

import event.Event;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class DrrRouter extends Router {
  
  public DrrRouter(Collection<Node> sources, int bandwidth){
    super(sources,bandwidth);
  }

  @Override
  public void initialize() {
    //Do not initialize fields of the superclass
  }

  @Override
  protected void arrivalEventHandler(Event evt) {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void departureEventHandler(Event evt) {
    // TODO Auto-generated method stub
    
  }

}
