package me.littlecheesecake.cropimagelayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RequestInterface {

//    @GET("learn/list_of_songs.php")
//    Call<List<User>> getprofile();


//    @GET("learn/list_of_songs.php")
//    Call<List<Song>> getSongList();


    @Headers({
            "Content-Type: application/json"

    })


//    @GET("test.php?page={page}")
//    Callback<User> getposts(@Path("page") int page);
//
//    @GET("test.php?page={page}")
//    Callback<User> getusers(@Path("page") int page);
//    @GET("test.php")
//    Callback<User> getusers();
//
//    @Headers({
//            "content-type: application/x-www-form-urlencoded"
//    })
//
//    @FormUrlEncoded //for put and post
//    @POST("new_post.php")
//    Call<MyResponseBody> newuser(@Field("getuserdata") String  getuserdata);

    @POST("detect")
//    Call<getstringresponse> callcontent(@Header("Authorization") String authorization, @Body RequestModel requestModel);
    Call<String> callcontent(@Body ArrayList pixelaaray);



    //@GET("users/list?sort=desc")?
}