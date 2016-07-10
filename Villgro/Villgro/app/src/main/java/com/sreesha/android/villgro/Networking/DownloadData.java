package com.sreesha.android.villgro.Networking;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Sreesha on 09-07-2016.
 */
public class DownloadData extends AsyncTask<String, Void, String> {

    private String ERROR_STRING = null;
    private String RESULT_STATUS = null;

    private String resultString = "defaultResult";

    private static String SUCCESS_STATUS = "parseDone";
    private static String ERROR_STATUS = "parsingError";

    private static String NETWORK_ERROR = "networkError";
    private static String JSON_PARSE_ERROR = "jsonParseError";

    private static String SQLITE_CAST_INSERT_ERROR = "castDataInsertError";
    private static String SQLITE_CREW_INSERT_ERROR = "crewDataInsertError";
    Uri.Builder builder;
    String Response = "";
    AsyncResult mCallBck;
    String resultData;
    String requestParam;

    public DownloadData(Context mContext, Uri.Builder builder, AsyncResult mCallBck) {
        this.builder = builder;
        this.mCallBck = mCallBck;
    }

    @Override
    protected String doInBackground(String... params) {
        for (String param : params) {
            requestParam = param;
            if (param.contains("signup")) {
                try {
                    resultData = performPostCall("http://84.200.84.218:3001/signup", builder);
                    //parseSignUpJSON(data);
                } catch (IOException e) {
                    e.printStackTrace();
                    ERROR_STRING = NETWORK_ERROR;
                    ERROR_STATUS = e.getMessage();
                    return ERROR_STATUS;
                }
            }
            if (param.contains("signin")) {
                try {
                    resultData = performPostCall("http://84.200.84.218:3001/signin", builder);
                    //parseSignInJSON(data);
                } catch (IOException e) {
                    e.printStackTrace();
                    ERROR_STRING = NETWORK_ERROR;
                    ERROR_STATUS = e.getMessage();
                    return ERROR_STATUS;
                }
            }
            if (param.contains("getQuize")) {
                try {
                    resultData = performPostCall("http://84.200.84.218:3001/getQuize", builder);
                    //parsegetQuizeJSON(data);
                } catch (IOException e) {
                    e.printStackTrace();
                    ERROR_STRING = NETWORK_ERROR;
                    ERROR_STATUS = e.getMessage();
                    return ERROR_STATUS;
                }
            }
            if (param.contains("addResults")) {
                try {
                    resultData = performPostCall("http://84.200.84.218:3001/addResults", builder);
                    //parsegetQuizeJSON(data);
                } catch (IOException e) {
                    e.printStackTrace();
                    ERROR_STRING = NETWORK_ERROR;
                    ERROR_STATUS = e.getMessage();
                    return ERROR_STATUS;
                }
            }
            if (requestParam.contains("emailPHPPost")) {
                try {
                    resultData = performPostCall("http://84.200.84.218/7/mail.php", builder);
                    Log.d("PHPPost", resultData);
                } catch (IOException e) {
                    e.printStackTrace();
                    ERROR_STRING = NETWORK_ERROR;
                    ERROR_STATUS = e.getMessage();
                    return ERROR_STATUS;
                }
            }
            RESULT_STATUS = SUCCESS_STATUS;
            return SUCCESS_STATUS;
        }
        return ERROR_STATUS;
    }

    void parsegetQuizeJSON(String data) {

    }

    void parseSignInJSON(String data) {
    }

    void parseSignUpJSON(String data) {

    }

    public String performPostCall(String sUrl, Uri.Builder builder) throws IOException {

        BufferedReader mBufferedInputStream;

        try {
            URL url = new URL(sUrl/*"http://84.200.84.218:3001/signup"*/);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            String query = builder.build().getEncodedQuery();

            OutputStream os = httpURLConnection.getOutputStream();

            BufferedWriter mBufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            mBufferedWriter.write(query);
            mBufferedWriter.flush();
            mBufferedWriter.close();
            os.close();

            httpURLConnection.connect();

            Log.d("Sree", "response code " + httpURLConnection.getResponseCode());

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                mBufferedInputStream = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inline;
                while ((inline = mBufferedInputStream.readLine()) != null) {
                    Response += inline;
                }
                mBufferedInputStream.close();
                Log.d("Sree", "sent the msg successfully");

                Log.d("Sree", Response);

            } else {
                Log.d("Sree", "something wrong");

            }
            return Response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equals(SUCCESS_STATUS) && ERROR_STRING == null && resultData != null) {
            if (requestParam.equals("signup")) {
                try {
                    mCallBck.onResultSignUpData(new JSONObject(resultData));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (requestParam.equals("signin")) {
                try {
                    mCallBck.onResultSignInData(new JSONObject(resultData));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (requestParam.equals("getQuize")) {
                try {
                    mCallBck.onResultQuizData(new JSONArray(resultData));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (requestParam.contains("addResults")) {
                try {
                    mCallBck.onResultQuizData(new JSONArray(resultData));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (ERROR_STRING != null) {
            Log.e("Error", ERROR_STRING);
        }
    }

    private String downloadUrl(String urlString) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int responseCode = conn.getResponseCode();
            Log.i("HTTP-RESPONSE-CODE", "" + responseCode);
            is = conn.getInputStream();

            return convertStreamToString(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
