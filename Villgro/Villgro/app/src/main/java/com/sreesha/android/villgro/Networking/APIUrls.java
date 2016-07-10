package com.sreesha.android.villgro.Networking;

import android.net.Uri;

/**
 * Created by Sreesha on 10-07-2016.
 */
public class APIUrls {
    private static final String AUTHORITY = "84.200.84.218:3001";

    public static Uri.Builder getAddQuestionsURL(long id, String question) {
        return (new Uri.Builder()
                .scheme("http")
                .authority(AUTHORITY)
                .appendPath("addQuestion")
                .appendQueryParameter("id", String.valueOf(id))
                .appendQueryParameter("question", question));
    }

    public static Uri.Builder getSignInURL(String email, String password, String type) {
        return (new Uri.Builder()
                .scheme("http")
                .authority(AUTHORITY)
                .appendPath("signin")
                .appendQueryParameter("email", email)
                .appendQueryParameter("password", password));
    }

    public static Uri.Builder getSignUpURL(String uname
            , String email
            , String password
            , String type) {
        return (new Uri.Builder()
                .appendQueryParameter("uname", uname)
                .appendQueryParameter("email", email)
                .appendQueryParameter("password", password)
                .appendQueryParameter("type", type));
    }


    public static Uri.Builder getQuizDataURL(String course_id) {
        return (new Uri.Builder()
                .appendQueryParameter("course_id", course_id)
        );
    }

    public static Uri.Builder getaddQuizDataURL(String course_id, String id, String result) {
        return (new Uri.Builder()
                .appendQueryParameter("course_id", course_id)
                .appendQueryParameter("id", id)
                .appendQueryParameter("result", course_id)
        );
    }
}
