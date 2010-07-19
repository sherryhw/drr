package event;


import java.util.*;


/**
 * 
 * * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 */
public class EventComparator implements Comparator<Event>
{
  
  /**
   * This method is utilized to compare to events. Thanks to this method, the event will be sorted based on their time
   */
  public int compare(Event event1, Event event2)
  {
    int order = event1.getTime() - event2.getTime();
    if(order==0){
      if(event1.getId() < event2.getId())
        order=-1;
      else
        order=1;
    }
    return order;
  }
  
}
