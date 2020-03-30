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

    /**
     * Says hello to the world.
     *
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(JedisConnection.class.getSimpleName())
                .output("benchmark/jedis-Throughput.log").forks(1).build();
        new Runner(options).run();
    }
}
