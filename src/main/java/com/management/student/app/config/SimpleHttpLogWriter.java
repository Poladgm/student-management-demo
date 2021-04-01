package com.management.student.app.config;

import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

public final class SimpleHttpLogWriter implements HttpLogWriter {

    private static final Logger logger = LogManager.getLogger (SimpleHttpLogWriter.class);


    public void write(final Precorrelation precorrelation, final String request) {
        CloseableThreadContext.put ("correlationId", precorrelation.getId ());
        logger.info (request);
    }

    public void write(final Correlation correlation, final String response) {
        CloseableThreadContext.put ("correlationId", correlation.getId ());
        logger.info (response);
    }
}
