package me.jun.common.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.jun.support.event.Event;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class MemberLeaveEvent extends Event {

    private final Long id;
}
