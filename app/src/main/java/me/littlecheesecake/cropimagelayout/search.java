package me.littlecheesecake.cropimagelayout;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static me.littlecheesecake.cropimagelayout.ApiClient.BASE_URL;

public class search extends AppCompatActivity {
    EditText searchedittext;
    Button searchbtn;
    ListView searchlistview;
    public static String faceid;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchbtn = (Button) findViewById(R.id.searchbtn);
        searchedittext = (EditText) findViewById(R.id.searchsomeone);
        searchlistview = (ListView) findViewById(R.id.searchlistview);
        imageView = (ImageView) findViewById(R.id.searchimageview);
        imageView.setBackgroundColor(Color.YELLOW);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                searchlistview.setVisibility(View.VISIBLE);

                faceid = searchedittext.getText().toString().toLowerCase();

                RequestInterface totalscoreservice2 = ApiClient.getClient().create(RequestInterface.class);
                Log.d("searchid", "onClick: this is your real ulr:  " + BASE_URL + faceid);
                Call<List<searchid>> calltotalscore2 = totalscoreservice2.getfaces(BASE_URL + faceid);
                Log.d("searchid", "searchid request is going to be sent ");

                calltotalscore2.enqueue(new retrofit2.Callback<List<searchid>>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(Call<List<searchid>> call, Response<List<searchid>> response) {


                        imageView.setBackgroundColor(Color.GREEN);
                        Log.d("searchid ", "searchid onResponse: you are inside on response:  " + response.message()
                                + " response body is :  " + response.body());
//                        try {
                        int w = response.body().get(0).pixels.length;
                        int h = response.body().get(0).pixels[0].length;
                        double[][][] cols =new double[w][h][4];
//                        cols = response.body().get(0).getPixels();
                        Log.d("searchid", "onResponse: id is : " + response.body().get(0).getId() + "   1 is : " + cols[0].length +
                                "    2 is : " + cols[0][0].length + "    3 is " + cols.length + cols);

//                        Mat imgf = new Mat();

                            Mat imgf = new Mat(cols.length, cols[0].length, CvType.CV_8UC4);
//                            Bitmap bmp = Bitmap.createBitmap(cols, cols.length, cols[0].length, Bitmap.Config.ARGB_8888);

//                            image.setRGB(0, 0, cols[0].length, cols.length, pxlsr, 0, pxls[0].length);


                        for (int i = 0; i < cols.length/2; i++) {
                            for (int j = 0; j < cols.length/2; j++) {
                                cols[i][j][0] = 255;
                                cols[i][j][1] = response.body().get(0).getPixels()[i][j][0];
                                cols[i][j][2] = response.body().get(0).getPixels()[i][j][1];
                                cols[i][j][3] = response.body().get(0).getPixels()[i][j][2];
//                                Log.d("searchid", "onResponse: item is :" + cols[i][j][0] + "  " + cols[i][j][1] + "    " + cols[i][j][2]);
                                imgf.put(i, j, cols[i][j]);


                            }
                        }
                        Log.d("searchid", "onResponse: this is my imgg" + imgf+ cols);
                        Bitmap bmp = null;
//                        Mat tmp = new Mat(cols.length, cols[0].length, CvType.CV_8UC4);
//                            try {
                        //Imgproc.cvtColor(seedsImage, tmp, Imgproc.COLOR_RGB2BGRA);
//                        Imgproc.cvtColor(imgf, tmp, Imgproc.COLOR_RGB2RGBA, 4);
                        bmp = Bitmap.createBitmap(imgf.cols(), imgf.rows(), Bitmap.Config.RGB_565);
                        Utils.matToBitmap(imgf, bmp);
//                        imageView.setImageBitmap(bmp);
//                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.apa));
//                            BitmapDrawable vd = bmp;
//                                Drawable drawable = (Drawable) new BitmapDrawable(bmp);
//                                imageView.setBackground(drawable);


//                            } catch (CvException e) {
//                                Log.d("Exception", e.getMessage());
//                            }

//                        imageView.setBackgroundColor(Color.RED);

//                        imageView.setImageBitmap(bmp);
//                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.apa));
                        Drawable drawable = (Drawable) new BitmapDrawable(bmp);
                        imageView.setImageDrawable(drawable);
//                        SaveImage(bmp);
                        Log.d("searchid", "onResponse: file is blue :))");
//                        } catch (Exception e) {
//                        }


                    }

                    @Override
                    public void onFailure(Call<List<searchid>> call, Throwable t) {
                        Log.d("searchid", "searchid \nonFailure: post wasn't successfully" + t);
                    }
                });

            }
        });


    }


//    private static void SaveImage(Bitmap finalBitmap) {
//
//        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
//        File myDir = new File(root + "/saved_images");
//        Log.d("searchid", "SaveImage: root is :"+ root);
//        myDir.mkdirs();
//
//            String fname = "Image.jpg";
//        File file = new File (myDir, fname);
//        if (file.exists ()) file.delete ();
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}