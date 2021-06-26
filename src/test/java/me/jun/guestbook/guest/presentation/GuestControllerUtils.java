package me.jun.guestbook.guest.presentation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class GuestControllerUtils {
    public static final String LINKS_SELF_HREF = "_links.self.href";

    public static final String ACCESS_TOKEN = "access_token";

    public static final String LINKS_LOGIN_HREF = "_links.login.href";

    public static final String LINKS_REGISTER_HREF = "_links.register.href";

    public static final String LOGIN_SELF_URI = "http://localhost/api/login";

    public static final String REGISTER_SELF_URI = "http://localhost/api/register";

    public static final String LEAVE_SELF_URI = "http://localhost/api/leave";
}
