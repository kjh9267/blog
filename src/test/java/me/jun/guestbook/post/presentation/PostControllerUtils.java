package me.jun.guestbook.post.presentation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class PostControllerUtils {
    public static final String LINKS_SELF_HREF = "_links.self.href";

    public static final String LINKS_CREATE_POST_HREF = "_links.create_post.href";

    public static final String LINKS_GET_POST_HREF = "_links.get_post.href";

    public static final String LINKS_UPDATE_POST_HREF = "_links.update_post.href";

    public static final String LINKS_DELETE_POST_HREF = "_links.delete_post.href";

    public static final String POSTS_SELF_URI = "http://localhost/api/posts";

    public static final String LINKS_HOME_HREF = "_links.home.href";

    public static final String STATUS_CODE = "status_code";

    public static final String MESSAGE = "message";
}
