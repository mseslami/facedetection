package me.littlecheesecake.cropimagelayout;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    public static final String BASE_URL = "http://87.98.232.1:5001/api/faces/";

//public static final String BASE_URL = "http://192.168.43.216:3000/api/";

    private static Retrofit retrofit = null;
    private static String TAG = ApiClient.class.getSimpleName();


    public static Retrofit getClient() {


        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
