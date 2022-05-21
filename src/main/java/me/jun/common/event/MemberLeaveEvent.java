package me.jun.common.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.jun.support.event.Event;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "email")
public class MemberLeaveEvent extends Event {

    private final String email;
}
