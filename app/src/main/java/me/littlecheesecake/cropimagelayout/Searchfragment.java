package me.littlecheesecake.cropimagelayout;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ocnyang.contourview.ContourView;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static me.littlecheesecake.cropimagelayout.ApiClient.BASE_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Searchfragment extends Fragment {

    EditText searchedittext;
    Button searchbtn, searchall;
    ListView searchlistview, allnameslistview;
    public static String faceid;
    ImageView imageView;
    Bitmap bmp;
    RelativeLayout waitlayout;

    ImageView canclebtn;

    public Searchfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searchfragment, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        searchbtn = (Button) getView().findViewById(R.id.searchbtn);
        searchedittext = (EditText) getView().findViewById(R.id.searchsomeone);
        searchlistview = (ListView) getView().findViewById(R.id.searchlistview);
//        imageView = (ImageView) getView().findViewById(R.id.searchimageview);
        searchall = (Button) getView().findViewById(R.id.getallfaces);
        allnameslistview = (ListView) getView().findViewById(R.id.allnameslistview);
        waitlayout = (RelativeLayout) getView().findViewById(R.id.waitlayout);
        canclebtn = (ImageView) getView().findViewById(R.id.canclebtn);
        searchall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                waitlayout.setVisibility(View.VISIBLE);
                RequestInterface totalscoreservice2 = ApiClient.getClient().create(RequestInterface.class);
                Call<List<String>> calltotalscore2 = totalscoreservice2.getnames(BASE_URL);
                Log.d("sendingcropedimage", "sendingcropedimage request is going to be sent ");

                calltotalscore2.enqueue(new retrofit2.Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        waitlayout.setVisibility(View.INVISIBLE);
                        Log.d("allfaces", "sendingcropedimage222222222222222onResponse: you are inside on response:  " + response.message()
                                + " response body is :  " + response.body());

                        if (response.body() != null) {
                            String[] allnames = new String[response.body().size()];
                            for (int o = 0; o < response.body().size(); o++) {
                                allnames[o] = response.body().get(o);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_list_item_1, android.R.id.text1,
                                    allnames);
                            allnameslistview.setAdapter(adapter);
                            allnameslistview.setVisibility(View.VISIBLE);
                            searchlistview.setVisibility(View.INVISIBLE);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        waitlayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "request failed \n" + t, Toast.LENGTH_SHORT).show();

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
            }
        });


        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waitlayout.setVisibility(View.INVISIBLE);
            }
        });
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                faceid = searchedittext.getText().toString().toLowerCase();

                waitlayout.setVisibility(View.VISIBLE);
                RequestInterface totalscoreservice2 = ApiClient.getClient().create(RequestInterface.class);
                Log.d("searchid", "onClick: this is your real ulr:  " + BASE_URL + faceid);
                Call<List<searchid>> calltotalscore2 = totalscoreservice2.getfaces(BASE_URL + faceid);
                Log.d("searchid", "searchid request is going to be sent ");

                calltotalscore2.enqueue(new retrofit2.Callback<List<searchid>>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(Call<List<searchid>> call, Response<List<searchid>> response) {
                        waitlayout.setVisibility(View.INVISIBLE);
                        if (response.body() != null) {

                            Log.d("searchid ", "searchid onResponse: you are inside on response:  " + response.message()
                                    + " response body is :  " + response.body());
//                        try {
                            Drawable[] drawable = new Drawable[response.body().size()];
                            String[] searchids = new String[response.body().size()];


                            for (int t = 0; t < response.body().size(); t++) {
                                try {
                                    int h = response.body().get(t).pixels.length;
                                    int w = response.body().get(t).pixels[0].length;
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

                            try {
                                CustomList adapter = new CustomList(getActivity(), searchids, drawable);
                                searchlistview.setAdapter(adapter);

                                allnameslistview.setVisibility(View.INVISIBLE);
                                searchlistview.setVisibility(View.VISIBLE);


                            } catch (Exception e) {
                            }

                        } else {
                            Toast.makeText(getActivity(), "no result \n" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<searchid>> call, Throwable t) {
                        waitlayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "request failed \n" + t, Toast.LENGTH_SHORT).show();
                        Log.d("searchid", "searchid \nonFailure: post wasn't successfully" + t);
                    }
                });
            }
        });


    }

//    private void initCustomContourView() {
//        ContourView contourViewCustom = (ContourView) getView().findViewById(R.id.backview);
//        int width = 400;//获取屏幕的宽度
//        int hight = 400;
//        int[] ints = {width / 2, 0, width, hight / 2, width / 2, hight, 0, hight / 2};
//        int[] intArr = new int[]{width / 2, hight / 4, width / 4 * 3, hight / 2, width / 2, hight / 4 * 3, width / 4, hight / 2};
//        contourViewCustom.setPoints(ints, intArr);
//        contourViewCustom.setShaderStartColor(getResources().getColor(R.color.background));
//        contourViewCustom.setShaderEndColor(getResources().getColor(R.color.colorAccent));
//        contourViewCustom.setShaderMode(ContourView.SHADER_MODE_RADIAL);
//    }

}

