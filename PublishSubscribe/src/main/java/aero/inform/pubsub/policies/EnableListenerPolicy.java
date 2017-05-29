package aero.inform.pubsub.policies;

import aero.inform.pubsub.ListenerRoute;
import aero.inform.pubsub.RegistrationRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.impl.EventDrivenConsumerRoute;
import org.apache.camel.support.RoutePolicySupport;

/**
 * Created by jjansen on 29.05.2017.
 */
public class EnableListenerPolicy extends RoutePolicySupport
{
  @Override
  public void onExchangeDone(Route route, Exchange exchange)
  {
    if (!route.getId().equals(RegistrationRoute.routeId))
      return;
    
    CamelContext camelContext = exchange.getContext();
    
    Route listenerRoute = camelContext.getRoute(ListenerRoute.routeId);
    
    if (((EventDrivenConsumerRoute) listenerRoute).isStarted())
      return;
    
    camelContext.getInflightRepository().remove(exchange);
    try
    {
      camelContext.startRoute(ListenerRoute.routeId);
    } catch (Exception e)
    {
      getExceptionHandler().handleException(e);
    }
  }
}
