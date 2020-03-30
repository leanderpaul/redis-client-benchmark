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

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 1)
@Threads(100)
@State(Scope.Benchmark)
@Measurement(iterations = 2, time = 600, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class LettuceConnection {
    private static final int LOOP = 1;
    private StatefulRedisConnection<String, String> connection;
    @Setup
    public void setup() {
        RedisClient client = RedisClient.create("redis://localhost");
        connection = client.connect();
    }
    @Benchmark
    public void get() throws ExecutionException, InterruptedException {
        RedisAsyncCommands<String, String> commands = connection.async();
        List<RedisFuture<String>> redisFutureList = new ArrayList<>();
        for (int i = 0; i < LOOP; ++i) {
            RedisFuture<String> future = commands.get("a");
            redisFutureList.add(future);
            future.get();
        }
        redisFutureList.forEach(f -> {
            try {
                f.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}