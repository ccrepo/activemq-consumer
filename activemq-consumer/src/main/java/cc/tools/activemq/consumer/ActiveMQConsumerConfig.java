package cc.tools.activemq.consumer;

import java.util.*;

/**
 * This class manages program command line parameters and configuration values
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class ActiveMQConsumerConfig extends ActiveMQConsumerValues {

  /**
   * Enum VALUES_STATUS is used to indicate the status of the value object during
   * processing.
   */
  static public enum VALUES_STATUS {
    /**
     * Status value {@link VALUES_STATUS_OK} means 'Ok'
     */
    VALUES_STATUS_OK,
    /**
     * Status value {@link VALUES_STATUS_HELP} means 'Help'
     */
    VALUES_STATUS_HELP,
    /**
     * Status value {@link VALUES_STATUS_ERROR} means 'Error'
     */
    VALUES_STATUS_ERROR
  };

  /**
   * Constructor for {@link ActiveMQConsumerConfig}.
   * 
   * @param args command line array containing parameter values.
   */
  public ActiveMQConsumerConfig(String[] args) {

    super();

    super.load(args);

    if (getStatus() == ActiveMQConsumerConfig.VALUES_STATUS.VALUES_STATUS_OK) {
      _isValid = true;
    }

  }

  /**
   * Method displays table showing configured values.
   */
  public void doDump() {
    _logger.info("configuration:");
    _logger.info("-type:               " + getTYpe());
    _logger.info("-comment:            " + getChannel());
    _logger.info("-timeout:            " + getTimeout());
    _logger.info("-limit:              " + getLimit());
    _logger.info("-debug:              " + (getDebug() ? "true" : "false"));
  }

  /**
   * Method displays 'usage' help information showing command-line options.
   */
  public void doHelp() {
    _logger.info("usage:");
    _logger.info("-type (topic|queue) -channel <channel> [-timeout <timeout>] [-limit <limit>] [-debug]");
    _logger.info("-channel:            mandatory. activemq channel to be consumed.");
    _logger.info("-type:               mandatory. activemq channel type 'topic' or 'queue' to be consumed.");
    _logger.info("-timeout:            optional.  timeout (in ms). default is 10000 ms.");
    _logger.info("-limit:              optional.  number of messages to consume. default is unlimited.");
    _logger.info("-debug:              optional.  toggle to adjust debug mode. default false.");
  }

  /**
   * Method returns {@code List<String>} containing configuration errors.
   * 
   * @return {@code List<String>} containing configuration errors.
   */
  public List<String> getErrors() {
    return _errors;
  }

  /**
   * Method returns boolean indicating whether command line parameters include
   * help '-h' flag.
   * 
   * @return boolean indicating whether help command line option is present.
   */
  public boolean getIsHelp() {
    return getStatus() == ActiveMQConsumerConfig.VALUES_STATUS.VALUES_STATUS_HELP;
  }

  /**
   * Method returns {@link #_isValid} value.
   * 
   * @return boolean indicating whether configuration is valid or not.
   */
  public boolean isValid() {
    return _isValid;
  }

  /**
   * Configured flag indicating whether {@link ActiveMQConsumerConfig} is valid or
   * not.
   */
  private boolean _isValid = false;
  
  /**
   * Local logger reference for logging operations.
   */
  final private ActiveMQConsumerLogger _logger = new ActiveMQConsumerLogger(ActiveMQConsumerConfig.class.getName());

}
