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
    private static final Map<String, Logger> LOGGERS = new HashMap<>();

    public static void init(Level level) {
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout("%d{dd MMM HH:mm} [%p] %m%n"));
        console.setThreshold(level);
        console.activateOptions();

        org.apache.log4j.Logger.getRootLogger().addAppender(console);
    }

    private final LoggerFacade logger;

    private Logger(String name) {
        this.logger = new LoggerFacade(LoggerFactory.getLogger(name));
    }

    public LoggerFacade internal() {
        return this.logger;
    }

    @Override
    protected Collection<Logger> delegate() {
        return ImmutableList.copyOf(LOGGERS.values());
    }

    public static Logger get(String name) {
        return LOGGERS.computeIfAbsent(name, k -> new Logger(name));
    }

    public void log(String item) {
        this.internal().info(item);
    }

    public void debug(String message) {
        this.internal().debug(message);
    }

    public void error(String message) {
        this.internal().error(message);
    }

    public void warn(String message) {
        this.internal().warn(message);
    }
}
