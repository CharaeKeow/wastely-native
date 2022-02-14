package my.edu.utem.ftmk.bitp3453.achifapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class LoginLoader extends AsyncTaskLoader<Bundle>
{
    private final String email, password;

    public LoginLoader(@NonNull Context context, String username, String password)
    {
        super(context);

        this.email = username;
        this.password = password;
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Nullable
    @Override
    public Bundle loadInBackground()
    {
        Bundle response = null;

        try
        {
            JSONObject request = new JSONObject();

            request.put("email", email);
            request.put("password", password);
            HttpURLConnection connection = (HttpURLConnection) new URL(" ").openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.getOutputStream().write(request.toString().getBytes());

            if (connection.getResponseCode() == 200)
            {
                response = new Bundle();
                JSONObject resp = new JSONObject(new BufferedReader(new InputStreamReader(connection.getInputStream())).lines().collect(Collectors.joining())); //Convert stream to string and later to JSON Object

                System.out.println(resp);

                response.putString("accountID", resp.getString("accountID"));
                response.putString("token", resp.getString("token"));
                response.putString("name", resp.getString("name"));
                response.putInt("role", resp.getInt("role"));
            }

            connection.disconnect();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e("LoginLoader", ex.getMessage(), ex);
        }

        return response;
    }
}
