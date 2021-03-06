package edu.illinois.techservices.elmr.servlets;

/**
 * Values used by classes in this package.
 */
final class ServletConstants {

  private ServletConstants() {
    // empty constructor to prevent instantiation.
  }

  /**
   * Name of the context variable that the Apache config file contents can be accessed from.
   */
  static final String APACHE_CONFIG_CONTEXT_PARAM_NAME =
      ServletConstants.class.getPackageName() + ".ApacheConfig";

  /**
   * Name of the context variable with the configured Shibboleth attribute names.
   */
  static final String ATTRIBUTES_CONTEXT_PARAM_NAME =
      ServletConstants.class.getPackageName() + ".attributes";

  /**
   * Url to logout of Shibboleth from.
   */
  static final String LOGOUT_URL = ServletConstants.class.getPackageName() + ".logoutUrl";

  /**
   * Name of the cookie that has the Url back to the service after session data is stored.
   */
  static final String SERVICE_URL_COOKIE_NAME = "__edu.illinois.techservices.elmr.serviceUrl";

  /**
   * Name of the context variable that session data is stored in.
   */
  static final String SESSION_DATA_CONTEXT_PARAM_NAME =
      ServletConstants.class.getPackageName() + ".sessionData";

  /**
   * Name of the cookie that has the key for session data.
   */
  static final String SESSION_KEY_COOKIE_NAME =
      "__" + ServletConstants.class.getPackageName() + ".sessionKey";

  /**
   * Name of context parameter and system property to disable secure cookies for local testing.
   */
  static final String SESSION_KEY_DISABLE_SECURE =
      ServletConstants.class.getPackageName() + ".DisableSecureCookies";

  static final String UNIQUE_USER_ID_PARAM_NAME =
      ServletConstants.class.getPackageName() + ".UniqueUserIdentifier";

  static final String DEFAULT_UNIQUE_USER_ID = "Shib-Session-ID";

  static final String EMPTY_STRING = "";

  static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
}
