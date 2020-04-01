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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 1)
@Threads(100)
@State(Scope.Benchmark)
@Measurement(iterations = 2, time = 600, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class LettuceConnection {

    private static final int LOOP = 1;
    private StatefulRedisConnection<String, String> connection;
    private RedisAsyncCommands<String,String> asyncCommands = null;
    private RedisCommands<String,String> syncCommands = null;

    @Setup
    public void setup() {
        RedisClient client = RedisClient.create("redis://localhost");
        connection = client.connect();
        asyncCommands = connection.async();
        syncCommands = connection.sync();
    }

    @Benchmark
    public void asyncGet() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; ++i) {
            asyncCommands.get("key");
        }
    }

    @Benchmark
    public void asyncSet() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; ++i) {
            asyncCommands.set("key", "value");
        }
    }

    @Benchmark
    public void syncGet() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; ++i) {
            syncCommands.get("key");
        }
    }

    @Benchmark
    public void syncSet() throws ExecutionException, InterruptedException {
        for (int i = 0; i < LOOP; ++i) {
            syncCommands.set("key", "value");
        }
    }
}