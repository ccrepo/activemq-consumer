package cc.tools.activemq.consumer;

import java.util.*;

/**
 * This encapsulates Parameter key values
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class ActiveMQConsumerValues {

  /**
   * Constructor for {@link ActiveMQConsumerValues}.
   */
  public ActiveMQConsumerValues() {
  }

  /**
   * Method loads parameter settings from command line arguments.
   * 
   * @param args String array containing command line arguments.
   */
  void load(String[] args) {

    Map<String, String> values = new HashMap<String, String>();

    _status = ActiveMQConsumerParameterKeys.load(args, values, _errors);

    if (_status != ActiveMQConsumerConfig.VALUES_STATUS.VALUES_STATUS_OK) {
      return;
    }

    if (!setValues(values)) {
      _status = ActiveMQConsumerConfig.VALUES_STATUS.VALUES_STATUS_ERROR;
      return;
    }

    _status = ActiveMQConsumerConfig.VALUES_STATUS.VALUES_STATUS_OK;
  }

  /**
   * Method returns the status of {@link ActiveMQConsumerConfig} object.
   * 
   * @return {@link Enum} {@link ActiveMQConsumerConfig.VALUES_STATUS} value
   *         indicating status.
   */
  public ActiveMQConsumerConfig.VALUES_STATUS getStatus() {
    return _status;
  }

  /**
   * Method loads configuration from file and returns boolean indicating result of
   * loading and processing command line arguments.
   * 
   * @param values {@code Map<String, String>} containing command line name/value
   *               pairs.
   * 
   * @return boolean indicating whether load was successful.
   */
  private boolean setValues(Map<String, String> values) {

      if (!setDebug(values) | !setTimeout(values) | !setLimit(values) | !setType(values) | !setChannel(values)) {

      _errors.add("setter failed");

      return false;
      
    }

    Set<String> inKeys = new HashSet<String>(values.keySet());
    Set<String> outKeys = new HashSet<String>(Arrays.asList(ActiveMQConsumerParameterKeys._KEYS));

    inKeys.removeAll(outKeys);
    outKeys.removeAll(values.keySet());

    if (inKeys.isEmpty() && outKeys.isEmpty()) {
      return true;
    }

    for (String key : inKeys) {
      _errors.add("parameter not recognized '" + key + "'");
    }

    for (String key : outKeys) {
      _errors.add("parameter not set '" + key + "'");
    }

    return false;
  }

  /**
   * Method to store 'mandatory parameter missing' error.
   * 
   * @param parameterName name of parameter to report.
   */
  @SuppressWarnings("unused")
  private void logMissingParameterError(String parameterName) {
    
    StringBuilder buffer = new StringBuilder();
    
    buffer.append("mandatory parameter -");
    buffer.append(parameterName);
    buffer.append(" missing");
    
    _errors.add(buffer.toString());
  }

  /**
   * Method returns {@link #_debug} configuration value.
   * 
   * @return boolean indicating whether debug mode is on or not.
   */
  protected boolean getDebug() {
    return _debug;
  }

  /**
   * Method returns the {@link #_timeout} configuration value.
   * 
   * @return int millisecond timeout between server calls..
   */
  protected int getTimeout() {
    return _timeout;
  }

  /**
   * Method returns the {@link #_limit} configuration value.
   * 
   * @return int max number of messages to read..
   */
  protected int getLimit() {
    return _limit;
  }

  /**
   * Method returns the {@link #_type} configuration value.
   * 
   * @return String containing target queue name to be read..
   */
  protected String getTYpe() {
    return _type;
  }

  /**
   * Method returns the {@link #_channel} configuration value.
   * 
   * @return String containing target queue name to be read..
   */
  protected String getChannel() {
    return _channel;
  }

  /**
   * Method sets optional parameter field {@link _debug} from
   * {@value ActiveMQConsumerParameterKeys#_KEY_DEBUG}.
   * 
   * @param values contains all loaded configuration parameter values._isValid
   * @return boolean indicating success or fail.
   */
  private boolean setDebug(Map<String, String> values) {

    if (values.containsKey(ActiveMQConsumerParameterKeys._KEY_DEBUG)) {

      String value = values.get(ActiveMQConsumerParameterKeys._KEY_DEBUG);

      if (!(value == null || value.isBlank() || value.isEmpty())) {

        StringBuilder buffer = new StringBuilder();

        buffer.append("flag parameter -");
        buffer.append(ActiveMQConsumerParameterKeys._KEY_DEBUG);
        buffer.append(" should not have a value");

        _errors.add(buffer.toString());

        return false;
      }

      _debug = true;

      return true;
    
    } else {
      
      values.put(ActiveMQConsumerParameterKeys._KEY_DEBUG, "");
      
    }

    _debug = false;

    return true;
  }

  /**
   * Method sets mandatory parameter field {@link _timeout} from
   * {@link ActiveMQConsumerParameterKeys#_KEY_TIMEOUT}
   *
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setTimeout(Map<String, String> values) {

    int timeout = -1;

    if (values.containsKey(ActiveMQConsumerParameterKeys._KEY_TIMEOUT)) {

      String token = values.get(ActiveMQConsumerParameterKeys._KEY_TIMEOUT).trim();

      try {

        if (!token.isBlank() || token.length() < 8) {
          timeout = Integer.parseInt(token);
        }

        if (timeout < 0) {
          timeout = 0;
        }

        if (timeout > 9999999) {
          timeout = 9999999;
        }

      } catch (NumberFormatException e) {

        _errors.add("-" + ActiveMQConsumerParameterKeys._KEY_TIMEOUT + " is invalid number");

        return false;
      }

    } else {
      timeout = 10000;

      values.put(ActiveMQConsumerParameterKeys._KEY_TIMEOUT, Integer.toString(timeout));
    }

    _timeout = timeout;

    return true;
  }
  
  /**
   * Method sets mandatory parameter field {@link _limit} from
   * {@link ActiveMQConsumerParameterKeys#_KEY_LIMIT}
   *
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setLimit(Map<String, String> values) {
  
    int limit = -1;

    if (values.containsKey(ActiveMQConsumerParameterKeys._KEY_LIMIT)) {

      String token = values.get(ActiveMQConsumerParameterKeys._KEY_LIMIT).trim();

      try {

        if (!token.isBlank() || token.length() < 8) {
          limit = Integer.parseInt(token);
        }

        if (limit < 0) {
          limit = 0;
        }

      } catch (NumberFormatException e) {

        _errors.add("-" + ActiveMQConsumerParameterKeys._KEY_LIMIT + " is invalid number");

        return false;
      }

    } else {
      limit = 0;

      values.put(ActiveMQConsumerParameterKeys._KEY_LIMIT, Integer.toString(limit));
    }

    _limit = limit;

    return true;
  }

  /**
   * Method sets mandatory parameter field {@link _type} from
   * {@link ActiveMQConsumerParameterKeys#_KEY_TYPE}
   *
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setType(Map<String, String> values) {
  
    String type = null;

    if (values.containsKey(ActiveMQConsumerParameterKeys._KEY_TYPE)) {
      
      type = values.get(ActiveMQConsumerParameterKeys._KEY_TYPE);

      if (type != null &&
          type.matches(_pattern)) {
      
        _type = type;
        
        return true;
      }

    } 

    _errors.add("-" + ActiveMQConsumerParameterKeys._KEY_TYPE + " parameter missing");
    
    return false;
  }
  
  /**
   * Method sets mandatory parameter field {@link _channel} from
   * {@link ActiveMQConsumerParameterKeys#_KEY_CHANNEL}
   *
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setChannel(Map<String, String> values) {
  
    String channel = null;

    if (values.containsKey(ActiveMQConsumerParameterKeys._KEY_CHANNEL)) {
      
      channel = values.get(ActiveMQConsumerParameterKeys._KEY_CHANNEL);

      if (channel != null &&
          channel.matches(_pattern)) {
      
        _channel = channel;
        
        return true;
      }

    } 

    _errors.add("-" + ActiveMQConsumerParameterKeys._KEY_CHANNEL + " parameter missing");
    
    return false;
  }
  
  /**
   * Processing status.
   */
  private ActiveMQConsumerConfig.VALUES_STATUS _status = ActiveMQConsumerConfig.VALUES_STATUS.VALUES_STATUS_ERROR;

  /**
   * {@code List<String>} containing a list of errors encountered during parameter
   * processing.
   */
  protected List<String> _errors = new ArrayList<String>();

  /**
   * Configured flag for additional debug output.
   */
  private boolean _debug = false;

  /**
   * Configured message timeout.
   */
  private int _timeout = -1;

  /**
   * Configured message limit.
   */
  private int _limit = 0;

  /**
   * Configured target type. Must be 'queue' or 'type'.
   */
  private String _type = "";

  /**
   * Configured target channel.
   */
  private String _channel = "";

  /**
   * Queue match pattern.
   */
  private String _pattern = "^[a-zA-Z0-9\\.]+$";
  
  /**
   * Local logger reference for logging operations.
   */
  @SuppressWarnings("unused")
  final private ActiveMQConsumerLogger _logger = new ActiveMQConsumerLogger(ActiveMQConsumerValues.class.getName());

}
