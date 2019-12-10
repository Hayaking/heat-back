package com.example.botheat.config;

import com.example.botheat.entity.Operator;
import com.example.botheat.net.SocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author haya
 */
@Configuration
public class GlobalConfig {

    @Bean(initMethod = "start",destroyMethod = "stop")
    public SocketServer socketServer() {
        return new SocketServer();
    }

    @Bean
    public Map<UUID, Operator> uuidPoll() {
        return new ConcurrentHashMap<>();
    }
}
