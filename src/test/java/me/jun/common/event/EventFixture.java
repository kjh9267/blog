package me.jun.common.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.support.event.Event;

import static me.jun.core.member.MemberFixture.MEMBER_ID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class EventFixture {

    public static Event memberLeaveEvent() {
        return new MemberLeaveEvent(MEMBER_ID);
    }
}
