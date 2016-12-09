package aero.inform.realtime.connector.core;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.BeanInject;
import org.apache.camel.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.netty4.NettyConfiguration;

/**
 * Created by Benjamin Schleinzer on 13.07.2015.
 * Handles incoming object server request either buffered or unbuffered
 */
public class OSClientRouteBuilder extends RouteBuilder
{
  @BeanInject
  private ObjectServerConfig objectServerConfig;
  
  private NettyConfiguration nettyConfiguration;

  private static final String BUFFERED_ROUTE_ID = "RT-OS_buffered";
  private static final String COMPONENT = "activemq:";
  private static final String DLQ_ROUTE_ID = "direct:DLQ-Handler";
  private static final String DLQ_DESTINATION_ID = BUFFERED_ROUTE_ID + ".DLQ";

  private final String logLocation = OSClientRouteBuilder.class.getPackage().getName();

  @Override
  public void configure() throws Exception
  {
    if (objectServerConfig == null)
      objectServerConfig = getContext().getRegistry().lookupByNameAndType("objectServerConfig", ObjectServerConfig.class);

    from(DLQ_ROUTE_ID).id("os-DLQ-connection")
      .log(LoggingLevel.ERROR, logLocation, "Exhausted OS delivery attempts. " +
                                              "Moved message ${header.breadcrumbId} to " + DLQ_DESTINATION_ID)
      .to("activemq:" + DLQ_DESTINATION_ID);
  
    ActiveMQComponent activeMQComponent = getActiveMQComponent();
    activeMQComponent.setCamelContext(getContext());
    activeMQComponent.setBrokerURL("tcp://localhost:61616");
    activeMQComponent.setConcurrentConsumers(5);
//    activeMQComponent.setAsyncConsumer(5);
    Endpoint endpoint = activeMQComponent.createEndpoint(getBufferedRoute());
    
    from(endpoint)
//    from(getConcurrentConsumer(getBufferedRoute()))
      .id("os-buffered-connection")
      .transacted("required")
      .convertBodyTo(String.class)
    .to("file://out.txt");
  }

  public String getBufferedRoute()
  {
    return COMPONENT + BUFFERED_ROUTE_ID;
  }

  public ActiveMQComponent getActiveMQComponent()
  {
    Component comp = getContext().getComponent("activemq");
    if (comp instanceof ActiveMQComponent)
      return (ActiveMQComponent) comp;
    else
      return getContext().getRegistry().lookupByNameAndType("activemq", ActiveMQComponent.class);
  }

  private String getConcurrentConsumer(String route)
  {
    String resultRoute = route;
    if (route.contains("?"))
      resultRoute += "&";
    else
      resultRoute += "?";
    
    return resultRoute + "concurrentConsumers={{activemq.concurrentConsumers}}&asyncConsumer={{activemq.asyncConsumer}}";
  }
}
