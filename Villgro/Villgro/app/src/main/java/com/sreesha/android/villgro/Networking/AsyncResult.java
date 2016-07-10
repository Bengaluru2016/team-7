package com.sreesha.android.villgro.Networking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sreesha on 10-07-2016.
 */
public interface AsyncResult {
    void onResultJSON(JSONObject object) throws JSONException;

    void onResultString(String stringObject, String errorString, String parseStatus);
    void onResultQuizData(JSONArray object);
    void onResultSignInData(JSONObject object);
    void onResultSignUpData(JSONObject object);
}
