package me.littlecheesecake.cropimagelayout;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static me.littlecheesecake.cropimagelayout.ApiClient.BASE_URL;

public class search extends AppCompatActivity {
    EditText searchedittext;
    Button searchbtn, searchall;
    ListView searchlistview, allnameslistview;
    public static String faceid;
    ImageView imageView;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchbtn = (Button) findViewById(R.id.searchbtn);
        searchedittext = (EditText) findViewById(R.id.searchsomeone);
        searchlistview = (ListView) findViewById(R.id.searchlistview);
        imageView = (ImageView) findViewById(R.id.searchimageview);
        searchall = (Button) findViewById(R.id.getallfaces);
        allnameslistview = (ListView) findViewById(R.id.allnameslistview);

        searchall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                RequestInterface totalscoreservice2 = ApiClient.getClient().create(RequestInterface.class);
                Call<List<String>> calltotalscore2 = totalscoreservice2.getnames(BASE_URL);
                Log.d("sendingcropedimage", "sendingcropedimage request is going to be sent ");

                calltotalscore2.enqueue(new retrofit2.Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        Log.d("allfaces", "sendingcropedimage222222222222222onResponse: you are inside on response:  " + response.message()
                                + " response body is :  " + response.body());

                        if (response.body() != null) {
                            String [] allnames = new String[response.body().size()];
                            for (int o =0 ; o< response.body().size(); o++){
                                allnames[o]= response.body().get(o);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                    android.R.layout.simple_list_item_1, android.R.id.text1,
                                    allnames);
                            allnameslistview.setAdapter(adapter);
                            allnameslistview.setVisibility(View.VISIBLE);
                            searchlistview.setVisibility(View.INVISIBLE);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        Log.d("sendingcropedimage", "sendingcropedimage222222222222222userscoin\nonFailure: post wasn't successfully" + t);
                    }
                });

            }
        });
        allnameslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView c = (TextView) view.findViewById(android.R.id.text1);

                Log.d("allnames", "onItemClick: thi isxxcvvb " + c.getText());
                searchedittext.setText(c.getText());
            }});


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                faceid = searchedittext.getText().toString().toLowerCase();

                RequestInterface totalscoreservice2 = ApiClient.getClient().create(RequestInterface.class);
                Log.d("searchid", "onClick: this is your real ulr:  " + BASE_URL + faceid);
                Call<List<searchid>> calltotalscore2 = totalscoreservice2.getfaces(BASE_URL + faceid);
                Log.d("searchid", "searchid request is going to be sent ");

                calltotalscore2.enqueue(new retrofit2.Callback<List<searchid>>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(Call<List<searchid>> call, Response<List<searchid>> response) {


                        Log.d("searchid ", "searchid onResponse: you are inside on response:  " + response.message()
                                + " response body is :  " + response.body());
//                        try {
                        Drawable[] drawable = new Drawable[response.body().size()];
                        String[] searchids = new String[response.body().size()];


                        for (int t = 0; t < response.body().size(); t++) {
                            try {
                                int h = response.body().get(0).pixels.length;
                                int w = response.body().get(0).pixels[0].length;
                                double[][][] cols = new double[h][w][4];
//                        cols = response.body().get(0).getPixels();
//                                Log.d("searchid", "onResponse: id is : " + response.body().get(0).getId() + "   1 is : " + cols[0].length +
//                                        "    2 is : " + cols[0][0].length + "    3 is " + cols.length + cols);


                                Mat imgf = new Mat(cols.length, cols[0].length, CvType.CV_8UC4);

                                for (int i = 0; i < cols.length; i++) {
                                    for (int j = 0; j < cols[0].length; j++) {
                                        cols[i][j][3] = 255;
                                        cols[i][j][2] = response.body().get(t).getPixels()[i][j][0];
                                        cols[i][j][1] = response.body().get(t).getPixels()[i][j][1];
                                        cols[i][j][0] = response.body().get(t).getPixels()[i][j][2];
//                                Log.d("searchid", "onResponse: item is :" + cols[i][j][0] + "  " + cols[i][j][1] + "    " + cols[i][j][2]);
                                        imgf.put(i, j, cols[i][j]);
                                    }
                                }
                                Log.d("searchid", "onResponse: this is my imgg" + imgf + cols);
                                bmp = Bitmap.createBitmap(imgf.cols(), imgf.rows(), Bitmap.Config.RGB_565);
                                Utils.matToBitmap(imgf, bmp);
                                drawable[t] = (Drawable) new BitmapDrawable(bmp);
                                searchids[t] = response.body().get(t).getId();
                                Log.d("searchid", "onResponse: this is my searchid and drawable array:" + searchids + drawable);


                            } catch (Exception e) {
                            }
                        }


//                        drawable[0] = (Drawable) new BitmapDrawable(bmp);
//                        imageView.setImageDrawable(drawable[0]);
                        Log.d("searchid", "onResponse: file is blue :))");

                        try {                        // Bind array strings into an adapter
//                            ArrayAdapter<Drawable> adapter = new ArrayAdapter<Drawable>(getApplicationContext(),
//                                    R.layout.imagelistview, R.id.imageview,
//                                    drawable);
//                            searchlistview.setAdapter(adapter);

//                            String[] name = {"Android","Android Ui","Java",	"Php",	"Sqllite","Html","c#"};
//                            Drawable[] img = {R.drawable.ic_launcher , R.drawable.ic_launcher , R.drawable.ic_launcher ,
//                                    R.drawable.ic_launcher , R.drawable.ic_launcher , R.drawable.ic_launcher, R.drawable.ic_launcher};


                            CustomList adapter = new CustomList(search.this, searchids, drawable);
                            searchlistview.setAdapter(adapter);

                            allnameslistview.setVisibility(View.INVISIBLE);
                            searchlistview.setVisibility(View.VISIBLE);


                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<searchid>> call, Throwable t) {
                        Log.d("searchid", "searchid \nonFailure: post wasn't successfully" + t);
                    }
                });
            }
        });


    }

}
