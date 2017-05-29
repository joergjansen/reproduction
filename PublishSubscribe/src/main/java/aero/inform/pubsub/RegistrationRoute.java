package aero.inform.pubsub;

import aero.inform.pubsub.policies.EnableListenerPolicy;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class RegistrationRoute extends RouteBuilder {
    
    final String netty4Target;
    public final static String routeId = "REGISTRATION_ROUTE";
    
    private String registrationBody = "<SelectServiceModels>\n" +
                                        "  <Header>\n" +
                                        "    <CommandHeader>\n" +
                                        "      <ResultLanguage>en</ResultLanguage>\n" +
                                        "    </CommandHeader>\n" +
                                        "  </Header>\n" +
                                        "  <ServiceDomains>\n" +
                                        "    <Elem>HubControl</Elem>\n" +
                                        "  </ServiceDomains>\n" +
                                        "  <ServiceModelType>ServiceMovement</ServiceModelType>\n" +
                                        "  <Filter>\n" +
                                        "    <AllFilter/>\n" +
                                        "  </Filter>\n" +
                                        "  <SubscriptionId>GSLISA</SubscriptionId>\n" +
                                        "</SelectServiceModels>";
    
    
    public RegistrationRoute(String netty4Target)
    {
        super();
        this.netty4Target = netty4Target;
    }
    
    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {
        from("quartz://gs/provider?trigger.repeatInterval=10000&trigger.repeatCount=-1")
          .id(routeId).routeId(routeId)
          .routePolicy(new EnableListenerPolicy())
          
          .setBody(constant(registrationBody))
          .to(ExchangePattern.InOnly, netty4Target)
          .process(exchange -> System.out.println("Timer: " + LocalDateTime.now()))
          .process(exchange -> getContext().stopRoute(RegistrationRoute.routeId, 1, TimeUnit.SECONDS))
        ;
    }

}
