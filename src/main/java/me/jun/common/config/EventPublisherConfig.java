package me.jun.common.config;

import lombok.RequiredArgsConstructor;
import me.jun.common.event.EventPublisher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventPublisherConfig {

    private final ApplicationContext applicationContext;

    @Bean
    public InitializingBean eventPublisherInitialize() {
        return () -> new EventPublisher(applicationContext);
    }
}
