package edu.illinois.techservices.elmr.servlets;

import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import edu.illinois.techservices.elmr.SessionData;
import edu.illinois.techservices.elmr.SessionDataImpl;

/**
 * Instantiates a {@link SessionData} object and caches it in a context parameter.
 * 
 * <p>
 * This listener assumes that the SessionData implementation has to connect to an external server.
 * Thus, a host name and port can be specified for this connection. Both the host name and the port
 * can be set 3 ways and are searched in this order:
 * 
 * <h3>Host name</h3>
 * <ol>
 * <li>The value of the system property {@code edu.illinois.techservices.elmr.SessionData.hostname}.
 * <li>The value of the context parameter
 * {@code edu.illinois.techservices.elmr.SessionData.hostname}.
 * <li>The default value {@code localhost} (if neither of the the above are set).
 * </ol>
 * 
 * <h3>Port</h3>
 * <ol>
 * <li>The value of the system property {@code edu.illinois.techservices.elmr.SessionData.port}.
 * <li>The value of the context parameter {@code edu.illinois.techservices.elmr.SessionData.port}.
 * <li>The default value that depends on the implementation (if neither of the the above are set).
 * </ol>
 * 
 * <p>
 * When set, a connection to the external datasource is established. If the connection fails, a
 * default in-memory cache is created and used instead.
 */
@WebListener
public class SessionDataContextListener implements ServletContextListener {

  private static final Logger LOGGER = Logger.getLogger(SessionDataContextListener.class.getName());

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    LOGGER.config("Initializing session data interface...");

    var hostname = ElmrParameters.getString(sce.getServletContext(),
        SessionData.SESSION_DATA_HOSTNAME_SYSPROP, SessionDataImpl.DEFAULT_HOSTNAME);

    var port = ElmrParameters.getInteger(sce.getServletContext(),
        SessionData.SESSION_DATA_PORT_SYSPROP, SessionDataImpl.DEFAULT_PORT);

    var minConnections = ElmrParameters.getInteger(sce.getServletContext(),
        SessionDataImpl.MIN_CONNECTIONS_SYSPROP, SessionDataImpl.DEFAULT_MIN_CONNECTIONS);

    var maxConnections = ElmrParameters.getInteger(sce.getServletContext(),
        SessionDataImpl.MAX_CONNECTIONS_SYSPROP, SessionDataImpl.DEFAULT_MAX_CONNECTIONS);

    SessionData sd = new SessionDataImpl(hostname, port, minConnections, maxConnections);

    if (sd.isConnected()) {
      sce.getServletContext().setAttribute(ServletConstants.SESSION_DATA_CONTEXT_PARAM_NAME, sd);
      LOGGER.config("SessionData object configured; access with context property "
          + ServletConstants.SESSION_DATA_CONTEXT_PARAM_NAME);
    } else {
      LOGGER.severe(
          "Unable to connect to session data store at " + SessionData.SESSION_DATA_HOSTNAME_SYSPROP
              + " on port " + SessionData.SESSION_DATA_PORT_SYSPROP + "!");
      throw new RuntimeException("Failed to establish connection to session data store!");
    }
  }
}
