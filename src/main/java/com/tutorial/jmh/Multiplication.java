package com.tutorial.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class Multiplication {

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void test10() {
        testMethod(10);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void test20() {
        testMethod(20);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void test10BigInteger() {
        testMethodBigInteger(10);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void test20BigInteger() {
        testMethodBigInteger(20);
    }

    public long testMethod(int n) {
        long product = 1;
        for (int i = 2; i < n + 1; i++) {
            product *= i;
        }
        return product;
    }

    public BigInteger testMethodBigInteger(int n) {
        BigInteger product = new BigInteger("1");
        for (int i = 2; i < n + 1; i++) {
            product = product.multiply(new BigInteger(String.valueOf(i)));
        }
        return product;
    }

    /**
     * To run this benchmark class:
     *
     * java -cp target/benchmarks.jar com.tutorial.jmh.Multiplication
     *
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(Multiplication.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(options).run();
    }
}
