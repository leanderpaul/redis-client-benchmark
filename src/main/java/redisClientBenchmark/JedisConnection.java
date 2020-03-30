package redisClientBenchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Mode;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 1)
@Threads(100)
@State(Scope.Thread)
@Measurement(iterations = 10, time = 600, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JedisConnection {

    private static final int LOOP = 1;
    private Jedis jedis;

    @Setup
    public void setup() {
        jedis = new Jedis("127.0.0.1", 6379);
    }

    @Benchmark
    public void set() {
        for(int i =0;i<LOOP;i++) {
            jedis.set("key", "value");
        }
    }

    @Benchmark
    public void get() {
        for (int i = 0; i < LOOP; ++i) {
            jedis.get("key");
        }
    }
}