package me.jun.common.event;

import lombok.RequiredArgsConstructor;
import me.jun.support.event.Event;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher publisher;

    public void raise(Event event) {
        publisher.publishEvent(event);
    }
}
