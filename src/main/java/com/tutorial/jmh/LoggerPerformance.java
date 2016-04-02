package com.tutorial.jmh;

import org.apache.logging.log4j.LogManager;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gzge on 3/31/16.
 */
@State(Scope.Thread)
public class LoggerPerformance {

    org.slf4j.Logger slf4jLogger;
    org.apache.logging.log4j.Logger log4j2Logger;

    @Setup(Level.Trial)
    public void prepare() {
        slf4jLogger = LoggerFactory.getLogger(LoggerPerformance.class);
        log4j2Logger = LogManager.getLogger(LoggerPerformance.class);
    }

    @TearDown
    public void cleanUp() {

    }

    @Benchmark
    public void testSlf4j() {
        slf4jLogger.info("hello, world");
    }

    @Benchmark
    public void testSlf4jCheckLevel() {
        if (slf4jLogger.isInfoEnabled()) {
            slf4jLogger.info("hello, world");
        }
    }

    @Benchmark
    public void testLog4j2() {
        log4j2Logger.info("hello, world");
    }

    @Benchmark
    public void testLog4j2CheckLevel() {
        if (log4j2Logger.isInfoEnabled()) {
            log4j2Logger.info("hello, world");
        }
    }

    /**
     * To run this benchmark class:
     *
     * java -cp target/benchmarks.jar com.tutorial.jmh.LoggerPerformance
     *
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(LoggerPerformance.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(options).run();
    }
}
