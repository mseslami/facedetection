package me.littlecheesecake.cropimagelayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

import static me.littlecheesecake.cropimagelayout.search.faceid;

public interface RequestInterface {

//    @GET("learn/list_of_songs.php")
//    Call<List<User>> getprofile();


//    @GET("learn/list_of_songs.php")
//    Call<List<Song>> getSongList();

    @Headers({
            "content-type: application/json",
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
//    Call<ArrayList> callcontent2(@Body ArrayList arrayList);
    Call<Object> callcontent2(@Body double[][][] doubles);

    @POST("recognize")
    Call<Object> callrecognize(@Body double[][][] doubles);


//    @GET()
//    Call<Object> getfaces(@Url String faceid);


//    @GET("{user_id}")
//    Call<Object> getfaces(@Path(value = "user_id", encoded = true) String userId);


    @GET
    Call<List<searchid>> getfaces(@Url String url);

//    @GET(faceid)
//    Call<Object> getfaces();

//returns: []

    //@GET("users/list?sort=desc")?
}