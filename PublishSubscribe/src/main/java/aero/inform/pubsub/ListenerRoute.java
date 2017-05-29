package aero.inform.pubsub;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by jjansen on 29.05.2017.
 */
public class ListenerRoute extends RouteBuilder
{
  final String netty4Target;
  public final static String routeId = "LISTENER_ROUTE";
  
  public ListenerRoute(String netty4Target)
  {
    super();
    this.netty4Target = netty4Target;
  }
  
  @Override
  public void configure() throws Exception
  {
    from(netty4Target)
      .id(routeId).routeId(routeId)
      .noAutoStartup()
      .process(exchange -> System.out.println("Incoming message!"))
    ;
  }
}
