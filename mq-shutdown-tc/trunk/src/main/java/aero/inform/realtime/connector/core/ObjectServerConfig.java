package aero.inform.realtime.connector.core;

import org.apache.camel.LoggingLevel;
import org.apache.camel.PropertyInject;

/**
 * Created by jjansen on 20.05.2016.
 * Stores ObjectServer configuration
 */
public class ObjectServerConfig
{
  @PropertyInject(value = "host", defaultValue = "localhost")
  private String host = "localhost";

  @PropertyInject(value = "port", defaultValue = "30400")
  private Integer port;

  @PropertyInject(value = "listeningPort", defaultValue = "30250")
  private Integer listeningPort;

  @PropertyInject(value = "connectTimeout", defaultValue = "1000")
  private Integer connectionTimeout = 1000;

  @PropertyInject(value = "requestTimeout", defaultValue = "5000")
  private Integer requestTimeout = 5000;

  @PropertyInject(value = "keepaliveMsg", defaultValue = "KEEPALIVE")
  private String keepaliveMsg = "KEEPALIVE";

  @PropertyInject(value = "deadLetterRedeliveryDelay", defaultValue = "100")
  private Integer deadLetterRedeliveryDelay = 100;

  @PropertyInject(value = "deadLetterMaximumRedeliveries", defaultValue = "5")
  private Integer deadLetterMaximumRedeliveries = 5;

  @PropertyInject(value = "connectionTimeoutRedeliveryDelay", defaultValue = "1000")
  private Integer connectionTimeoutRedeliveryDelay = 100;

  @PropertyInject(value = "connectionTimeoutRedeliveries", defaultValue = "5")
  private Integer connectionTimeoutMaximumRedeliveries = 5;

  @PropertyInject(value = "osResultLoggingLevel", defaultValue = "TRACE")
  private LoggingLevel osResultLogLevel = LoggingLevel.TRACE;

  String getHost()
  {
    return host;
  }

  Integer getPort()
  {
    return port;
  }

  Integer getConnectionTimeout()
  {
    return connectionTimeout;
  }

  Integer getRequestTimeout() { return requestTimeout; }

  Integer getListeningPort() { return listeningPort; }

  String getKeepaliveMsg() { return keepaliveMsg; }

  public Integer getDeadLetterRedeliveryDelay()
  {
    return deadLetterRedeliveryDelay;
  }

  public Integer getDeadLetterMaximumRedeliveries()
  {
    return deadLetterMaximumRedeliveries;
  }

  public Integer getConnectionTimeoutRedeliveryDelay()
  {
    return connectionTimeoutRedeliveryDelay;
  }

  public Integer getConnectionTimeoutMaximumRedeliveries()
  {
    return connectionTimeoutMaximumRedeliveries;
  }

  public LoggingLevel getOsResultLogLevel()
  {
    return osResultLogLevel;
  }
}
