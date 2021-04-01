package com.management.student.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.DefaultHttpLogFormatter;
import org.zalando.logbook.DefaultSink;
import org.zalando.logbook.Logbook;

import static org.zalando.logbook.Conditions.contentType;
import static org.zalando.logbook.Conditions.exclude;
import static org.zalando.logbook.Conditions.requestTo;
import static org.zalando.logbook.HeaderFilters.removeHeaders;
import static org.zalando.logbook.json.JsonBodyFilters.accessToken;


@Configuration
public class LogBookFilterConfig {

    @Bean
    public Logbook logBook() {
        return Logbook.builder ()
                .condition (exclude (
                        requestTo ("/student-management/V1.0/"),
                        requestTo ("/student-management/V1.0/webjars/**"),
                        requestTo ("/student-management/V1.0/csrf"),
                        requestTo ("/student-management/V1.0/v2/**"),
                        requestTo ("/student-management/V1.0/swagger**"),
                        contentType ("application/octet-stream")))
                .headerFilter (
                        removeHeaders ("cookie", "content-length", "accept-encoding", "accept-language", "connection", "Accept", "dnt", "Transfer-Encoding"))
                .bodyFilter (accessToken ())
                .sink (
                        new DefaultSink (
                                new DefaultHttpLogFormatter (),
                                new SimpleHttpLogWriter ()
                        )
                )
                .build ();
    }
}
