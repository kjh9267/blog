package me.jun.guestbook.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class RelUtils {

    public static final String CREATE_POST = "create_post";

    public static final String GET_POST = "get_post";

    public static final String UPDATE_POST = "update_post";

    public static final String DELETE_POST = "delete_post";

    public static final String QUERY_POSTS = "query_posts";

    public static final String CREATE_COMMENT = "create_comment";

    public static final String GET_COMMENT = "get_comment";

    public static final String UPDATE_COMMENT = "update_comment";

    public static final String DELETE_COMMENT = "delete_comment";

    public static final String QUERY_COMMENTS_BY_POST = "query_comments_by_post_id";

    public static final String LOGIN = "login";

    public static final String REGISTER = "register";

    public static final String LEAVE = "leave";

    public static final String QUERY = "query";

    public static final String POST_ID = "post-id";

    public static final String HOME = "home";
}
