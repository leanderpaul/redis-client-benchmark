package redisClientBenchmark;

import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    public static void main(String[] args) throws RunnerException {
        /**
         * For Benchmarking Jedis redis client.
         */
        Options jedisOptions = new OptionsBuilder().include(JedisConnection.class.getSimpleName())
                .output("benchmark/jedis-Throughput.log").forks(1).build();
        new Runner(jedisOptions).run();

        /**
         * For benchmarking Lettuce redis client.
         */
        Options lettuceOptions = new OptionsBuilder().include(LettuceConnection.class.getSimpleName())
                .output("benchmark/lettuce-Throughput.log").forks(1).build();
        new Runner(lettuceOptions).run();
    }
}
