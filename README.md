# jmh-tutorial

a sample project to show how to use jmh. Currently, it includes two benchmarks:

* *logger performance comparison*: compares slf4j with log4j binding and log4j2 performance

## how to run the sample

To run all the benchmarks:

```bash
mvn clean install
java -jar target/benchmarks.jar #default behavior -wi 20 -i 20 -f 10
java -jar target/benchmarks.jar -wi 5 -i 5 -f 1
```

To run individual benchmarks:

```bash
mvn clean install
java -cp target/benchmark.jar com.tutorial.jmh.LoggerPerformance #runs logger performance benchmark
java -cp target/benchmark.jar com.tutorial.jmh.Multiplication #runs multiplication benchmark
```

## sample benchmark results

Here is some sample benchmark results. It shows that log4j2 logger has x50 throughput compared with slf4j with log4j binding. It also shows that using a primitive long value has more than 3000x throughput than using BigInteger when doing multiplication.

```bash
# Run complete. Total time: 00:02:50

Benchmark                                 Mode    Cnt          Score          Error   Units
LoggerPerformance.testLog4j2             thrpt      5  497266495.488 ± 37639153.467   ops/s
LoggerPerformance.testLog4j2CheckLevel   thrpt      5  493116489.550 ± 27628495.284   ops/s
LoggerPerformance.testSlf4j              thrpt      5   12103070.008 ±  1392945.915   ops/s
LoggerPerformance.testSlf4jCheckLevel    thrpt      5   11807391.240 ±  2244919.154   ops/s
Multiplication.test10                    thrpt      5       3152.392 ±      406.268  ops/us
Multiplication.test10BigInteger          thrpt      5          1.105 ±        0.054  ops/us
Multiplication.test20                    thrpt      5       3202.390 ±      401.265  ops/us
Multiplication.test20BigInteger          thrpt      5          0.514 ±        0.029  ops/us
Multiplication.test10                     avgt      5         ≈ 10⁻³                  us/op
Multiplication.test10BigInteger           avgt      5          0.903 ±        0.055   us/op
Multiplication.test20                     avgt      5         ≈ 10⁻⁴                  us/op
Multiplication.test20BigInteger           avgt      5          1.959 ±        0.113   us/op
Multiplication.test10                   sample  63727          0.044 ±        0.002   us/op
Multiplication.test10BigInteger         sample  86209          0.953 ±        0.055   us/op
Multiplication.test20                   sample  64363          0.042 ±        0.001   us/op
Multiplication.test20BigInteger         sample  79731          1.992 ±        0.036   us/op
Multiplication.test10                       ss      5          1.434 ±        0.919   us/op
Multiplication.test10BigInteger             ss      5         71.377 ±       51.099   us/op
Multiplication.test20                       ss      5          1.404 ±        1.085   us/op
Multiplication.test20BigInteger             ss      5        176.121 ±      100.931   us/op
```

## `jmh` command line options

```bash
$ java -jar target/benchmarks.jar -h

Usage: java -jar ... [regexp*] [options]
 [opt] means optional argument.
 <opt> means required argument.
 "+" means comma-separated list of values.
 "time" arguments accept time suffixes, like "100ms".

  [arguments]                 Benchmarks to run (regexp+).

  -bm <mode>                  Benchmark mode. Available modes are: [Throughput/thrpt,
                              AverageTime/avgt, SampleTime/sample, SingleShotTime/ss,
                              All/all]

  -bs <int>                   Batch size: number of benchmark method calls per
                              operation. Some benchmark modes may ignore this
                              setting, please check this separately.

  -e <regexp+>                Benchmarks to exclude from the run.

  -f <int>                    How many times to fork a single benchmark. Use 0 to
                              disable forking altogether. Warning: disabling
                              forking may have detrimental impact on benchmark
                              and infrastructure reliability, you might want
                              to use different warmup mode instead.

  -foe <bool>                 Should JMH fail immediately if any benchmark had
                              experienced an unrecoverable error? This helps
                              to make quick sanity tests for benchmark suites,
                              as well as make the automated runs with checking error
                              codes.

  -gc <bool>                  Should JMH force GC between iterations? Forcing
                              the GC may help to lower the noise in GC-heavy benchmarks,
                              at the expense of jeopardizing GC ergonomics decisions.
                              Use with care.

  -h                          Display help.

  -i <int>                    Number of measurement iterations to do. Measurement
                              iterations are counted towards the benchmark score.

  -jvm <string>               Use given JVM for runs. This option only affects forked
                              runs.

  -jvmArgs <string>           Use given JVM arguments. Most options are inherited
                              from the host VM options, but in some cases you want
                              to pass the options only to a forked VM. Either single
                              space-separated option line, or multiple options
                              are accepted. This option only affects forked runs.

  -jvmArgsAppend <string>     Same as jvmArgs, but append these options before
                              the already given JVM args.

  -jvmArgsPrepend <string>    Same as jvmArgs, but prepend these options before
                              the already given JVM arg.

  -l                          List the benchmarks that match a filter, and exit.

  -lp                         List the benchmarks that match a filter, along with
                              parameters, and exit.

  -lprof                      List profilers.

  -lrf                        List machine-readable result formats.

  -o <filename>               Redirect human-readable output to a given file.

  -opi <int>                  Override operations per invocation, see @OperationsPerInvocation
                              Javadoc for details.

  -p <param={v,}*>            Benchmark parameters. This option is expected to
                              be used once per parameter. Parameter name and parameter
                              values should be separated with equals sign. Parameter
                              values should be separated with commas.

  -prof <profiler>            Use profilers to collect additional benchmark data.
                              Some profilers are not available on all JVMs and/or
                              all OSes. Please see the list of available profilers
                              with -lprof.

  -r <time>                   Minimum time to spend at each measurement iteration.
                              Benchmarks may generally run longer than iteration
                              duration.

  -rf <type>                  Format type for machine-readable results. These
                              results are written to a separate file (see -rff).
                              See the list of available result formats with -lrf.

  -rff <filename>             Write machine-readable results to a given file.
                              The file format is controlled by -rf option. Please
                              see the list of result formats for available formats.

  -si <bool>                  Should JMH synchronize iterations? This would significantly
                              lower the noise in multithreaded tests, by making
                              sure the measured part happens only when all workers
                              are running.

  -t <int>                    Number of worker threads to run with. 'max' means
                              the maximum number of hardware threads available
                              on the machine, figured out by JMH itself.

  -tg <int+>                  Override thread group distribution for asymmetric
                              benchmarks. This option expects a comma-separated
                              list of thread counts within the group. See @Group/@GroupThreads
                              Javadoc for more information.

  -to <time>                  Timeout for benchmark iteration. After reaching
                              this timeout, JMH will try to interrupt the running
                              tasks. Non-cooperating benchmarks may ignore this
                              timeout.

  -tu <TU>                    Override time unit in benchmark results. Available
                              time units are: [m, s, ms, us, ns].

  -v <mode>                   Verbosity mode. Available modes are: [SILENT, NORMAL,
                              EXTRA]

  -w <time>                   Minimum time to spend at each warmup iteration. Benchmarks
                              may generally run longer than iteration duration.

  -wbs <int>                  Warmup batch size: number of benchmark method calls
                              per operation. Some benchmark modes may ignore this
                              setting.

  -wf <int>                   How many warmup forks to make for a single benchmark.
                              All iterations within the warmup fork are not counted
                              towards the benchmark score. Use 0 to disable warmup
                              forks.

  -wi <int>                   Number of warmup iterations to do. Warmup iterations
                              are not counted towards the benchmark score.

  -wm <mode>                  Warmup mode for warming up selected benchmarks.
                              Warmup modes are: INDI = Warmup each benchmark individually,
                              then measure it. BULK = Warmup all benchmarks first,
                              then do all the measurements. BULK_INDI = Warmup
                              all benchmarks first, then re-warmup each benchmark
                              individually, then measure it.

  -wmb <regexp+>              Warmup benchmarks to include in the run in addition
                              to already selected by the primary filters. Harness
                              will not measure these benchmarks, but only use them
                              for the warmup.
```

# references
   1. [jmh official site](http://openjdk.java.net/projects/code-tools/jmh/)
   1. [jmh sample benchmarks](http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/)
   1. [Introduction to JMH by Mikhail Vorontsov (java-performance.info)](http://java-performance.info/jmh/)
   1. [Comparison logger performance](http://antoniogoncalves.org/2015/01/15/micro-benchmarking-with-jmh-measure-dont-guess/)
   1. [Caliper microbenchmark tool from Google](https://github.com/google/caliper/wiki/ProjectHome)