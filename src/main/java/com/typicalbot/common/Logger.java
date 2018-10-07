package com.typicalbot.common;

import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ImmutableList;
import com.typicalbot.common.facade.LoggerFacade;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Logger extends ForwardingCollection<Logger> {
    /**
     * A collection of loggers.
     */
    private static final Map<String, Logger> LOGGERS = new HashMap<>();

    /**
     * Initialize the loggers.
     *
     * @param level The log level.
     */
    public static void init(Level level) {
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout("%d{dd MMM HH:mm} [%p] %m%n"));
        console.setThreshold(level);
        console.activateOptions();

        org.apache.log4j.Logger.getRootLogger().addAppender(console);
    }

    /**
     * The logger implementation.
     */
    private final LoggerFacade logger;

    /**
     * Default constructor.
     *
     * @param name The name of the logger.
     */
    private Logger(String name) {
        this.logger = new LoggerFacade(LoggerFactory.getLogger(name));
    }

    /**
     * Get the logger implementation.
     *
     * @return The logger implementation.
     */
    public LoggerFacade internal() {
        return this.logger;
    }

    @Override
    protected Collection<Logger> delegate() {
        return ImmutableList.copyOf(LOGGERS.values());
    }

    /**
     * Get a specific logger by a name, if it doesn't exist, create one.
     *
     * @param name The name of the logger.
     * @return The logger.
     */
    public static Logger get(String name) {
        return LOGGERS.computeIfAbsent(name, k -> new Logger(name));
    }

    /**
     * Log a message with the level of INFO.
     *
     * @param message The message to log.
     */
    public void log(String message) {
        this.internal().info(message);
    }

    /**
     * Log a message with the level of DEBUG.
     *
     * @param message The message to log.
     */
    public void debug(String message) {
        this.internal().debug(message);
    }

    /**
     * Log a message with the level of ERROR.
     *
     * @param message The message to log.
     */
    public void error(String message) {
        this.internal().error(message);
    }

    /**
     * Log a message with the level of WARN.
     *
     * @param message The message to log.
     */
    public void warn(String message) {
        this.internal().warn(message);
    }
}
