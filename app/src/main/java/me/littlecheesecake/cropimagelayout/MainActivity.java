package me.littlecheesecake.cropimagelayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //    EditPhotoView imageView;
//    TextView boxText;
//    EditableImage editableimage;
//    List<ScalableBox> boxes;
    public static ArrayList<ArrayList<Double>> a = new ArrayList();
    public static int w, h;
    public static Bitmap bmp32;
    private static final int Cam_Req = 1;
    private static final int RESULT_OK = 2;
    Bitmap cameraphoto;
    Bitmap selectedImage;
    public static Bitmap imagetocrop2;
    static String datatopost;
    static public ImageView image, next, back;
    TextView titletxt;
    EditText nameedittext;
    TextView nexttext, backtext;
    boolean photoselection = false;
    //    BitmapDrawable imagetocrop;
    public static Drawable imagetocrop;

    FragmentTransaction ft1;
    FragmentManager fm1;
    crop fragment1;
    public static Mat mat;
    static int counter;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i("OpenCV", "OpenCV loaded successfully");
                    mat = new Mat();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView nextface = (ImageView) findViewById(R.id.nextface);
        final ImageView backface2 = (ImageView) findViewById(R.id.backface2);

        final Button camerabtn = (Button) findViewById(R.id.camerabtn);
        final Button gallerybtn = (Button) findViewById(R.id.gallerybtn);
        final Button insertbtn = (Button) findViewById(R.id.insertbtn);
        final Button detectbtn = (Button) findViewById(R.id.detectbtn);
        nameedittext = (EditText) findViewById(R.id.nameedittext);
        nexttext = (TextView) findViewById(R.id.nexttxt);
        backtext = (TextView) findViewById(R.id.backtxt);
        image = (ImageView) findViewById(R.id.imageView);
        next = (ImageView) findViewById(R.id.next);
        back = (ImageView) findViewById(R.id.back);
        titletxt = (TextView) findViewById(R.id.titletxt);
        final String STEP_1, STEP_2, STEP_3_1, STEP_3_2;
        STEP_1 = "Please choose a photo";
        STEP_2 = "Edit and crop";
        STEP_3_1 = "Add to database";
        STEP_3_2 = "Recognize photo";
        titletxt.setText(STEP_1);
        nextface.setVisibility(View.VISIBLE);
        image.setImageDrawable(getResources().getDrawable(R.drawable.anonymous));
//        mat = new Mat();
//crop

//        cropit();

//    Log.d("doit", "onCreate: you are in do it true");
//            imageView = (EditPhotoView) findViewById(R.id.editable_image);
//            boxText = (TextView) findViewById(R.id.box_text);
//            image.setBackgroundResource((R.drawable.buttonbackground2));
//            ScalableBox box1 = new ScalableBox(25, 180, 640, 880);
//            ScalableBox box2 = new ScalableBox(2, 18, 680, 880);
//            ScalableBox box3 = new ScalableBox(250, 80, 400, 880);
//            boxes = new ArrayList<>();
//            boxes.add(box1);
//            boxes.add(box2);
//            boxes.add(box3);
//            editableimage = new EditableImage(getApplicationContext(), R.drawable.buttonbackground2);
//            editableimage.setBoxes(boxes);
//            imageView.initView(getApplicationContext(), editableimage);
//            boxText.setText("box: [" + 25 + "," + 180 + "],[" + 640 + "," + 880 + "]");
//
//            imageView.setOnBoxChangedListener(new OnBoxChangedListener() {
//                @Override
//                public void onChanged(int x1, int y1, int x2, int y2) {
//
//                    //x2 = x1 + w       &   y2 = y1 + h
//                    boxText.setText("box: [" + x1 + "," + y1 + "],[" + x2 + "," + y2 + "]");
//                }
//            });


        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Tada).duration(500).repeat(0).playOn(view);
//                cropit();
                if (titletxt.getText().toString().equals(STEP_1)) {

//                    if (!(image.getBackground().getConstantState() == getResources().getDrawable(R.drawable.anonymous).getConstantState())) {
                    if (photoselection) {
                        camerabtn.setVisibility(View.INVISIBLE);
                        gallerybtn.setVisibility(View.INVISIBLE);
                        titletxt.setText(STEP_2);
                        insertbtn.setVisibility(View.VISIBLE);
                        detectbtn.setVisibility(View.VISIBLE);
                        next.setVisibility(View.INVISIBLE);
                        nexttext.setVisibility(View.INVISIBLE);
//jump to response then run  croping:
//                        fragment1 = new crop();
//                        Bundle bundle = new Bundle();
//                        fragment1.setArguments(bundle);
////                        bundle.putInt("x1", 10);
////                        bundle.putInt("y1", 10);
////                        bundle.putInt("x2", 55);
////                        bundle.putInt("y2", 90);
//                        counter = 0;
//                        try {
//                            bundle.putInt("y1", a.get(counter).get(0).intValue());
//                            bundle.putInt("x2", a.get(counter).get(1).intValue());
//                            bundle.putInt("y2", a.get(counter).get(2).intValue());
//                            bundle.putInt("x1", a.get(counter).get(3).intValue());
//
//
//                            fm1 = getSupportFragmentManager();
//                            ft1 = fm1.beginTransaction();
//                            ft1.replace(R.id.cropframelayout, fragment1);
//                            ft1.commit();
//                        } catch (Exception e) {
//                        }
                        //getpixels:
//                        int height = imagetocrop2.getHeight();
//                        int width = imagetocrop2.getWidth();
//                        int[] pix = new int[width * height];
//                        imagetocrop2.getPixels(pix, 0, width, 0, 0, width, height);

//                        for (int i=0 ; i<pix.length; i++){
//                            Log.d("seepix", "onClick: pix is:"+pix[i]);
//                        }

//            [[[3, 4, 5],[3, 4, 7],[3, 4, 6]],
//             [[5, 6, 5],[5, 6, 7],[5, 6, 6]]]


//                      datatopost = "[";
//                        for (int i = 0; i < height; i++) {
//                            if (i != 0) {
//                                datatopost = datatopost + ",[";
//                            } else if (i == 0)
//                                datatopost = datatopost + "[";
//                            for (int j = 0; j < width; j++) {
//                                if (j != 0) {
//                                    datatopost =  datatopost + ",[";
//                                } else if (j == 0)
//                                    datatopost = datatopost + "[";
//
//                                int[] RGB = new int[3];
//                                int index = i * width + j;
//                                RGB[0] = (pix[index] >> 16) & 0xff;     //bitwise shifting
//                                RGB[1] = (pix[index] >> 8) & 0xff;
//                                RGB[2] = pix[index] & 0xff;
//                                Log.d("colors", "onActivityResult: rgb is:  " + RGB[0] + "   " + RGB[1] + "  " + RGB[2]);
//                                //R,G.B - Red, Green, Blue
//                                //to restore the values after RGB modification, use
//                                //next statement
//                                datatopost = datatopost + RGB[0] + ",";
//                                datatopost = datatopost + RGB[1] + ",";
//                                datatopost = datatopost + RGB[2] + "]";
//                                pix[index] = 0xff000000 | (RGB[0] << 16) | (RGB[1] << 8) | RGB[2];
//
//
//                            }
//                            datatopost = datatopost + "]";
//                            if (i == height-1){
//                                Log.d("see datatopost", "onClick: see datatopost" + datatopost);
//                            }
//                        }
//                        datatopost = datatopost + "]";
//
//                        Log.d("see datatopost", "onClick: see datatopost" + datatopost);

//                        ArrayList h = new ArrayList();
//                        for (int y = 0; y < height; y++) {
//                            ArrayList w = new ArrayList();
//                            for (int x = 0; x < width; x++) {
//                                int[] RGB = new int[3];
//                                int index = y * width + x;
//                                RGB[0] = (pix[index] >> 16) & 0xff;     //bitwise shifting
//                                RGB[1] = (pix[index] >> 8) & 0xff;
//                                RGB[2] = pix[index] & 0xff;
//                                Log.d("colors", "onActivityResult: rgb is:  " + RGB[0] + "   " + RGB[1] + "  " + RGB[2]);
//                                //R,G.B - Red, Green, Blue
//                                //to restore the values after RGB modification, use
//                                //next statement
//
//                                w.add(RGB);
//                                pix[index] = 0xff000000 | (RGB[0] << 16) | (RGB[1] << 8) | RGB[2];
//                            }
////
//                            h.add(w);
////                w.clear();
//                        }
//
//
//                        for (int q = 0; q < h.size(); q++) {
//                            Log.d("result of arrays are:", "onActivityResult: see h" + h.get(q).toString());
//                        }


                        //send request:

//        RequestModel gettotalscorelist = new RequestModel();
//        gettotalscorelist.setAction("GetTotalScoreList");
//        gettotalscorelist.setPar1("Day");
//                        counter = 1;
                        nextface.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                counter++;

                                Toast.makeText(MainActivity.this, "counnter is :" + counter, Toast.LENGTH_SHORT).show();
                                try {
                                    fragment1 = new crop();
                                    Bundle bundle = new Bundle();
                                    fragment1.setArguments(bundle);
//                                    bundle.putInt("x1", 30);
//                                    bundle.putInt("y1", 30);
//                                    bundle.putInt("x2", 115);
//                                    bundle.putInt("y2", 140);

                                    bundle.putInt("y1", a.get(counter).get(0).intValue());
                                    bundle.putInt("x2", a.get(counter).get(1).intValue());
                                    bundle.putInt("y2", a.get(counter).get(2).intValue());
                                    bundle.putInt("x1", a.get(counter).get(3).intValue());

                                    fm1 = getSupportFragmentManager();
                                    ft1 = fm1.beginTransaction();
                                    ft1.replace(R.id.cropframelayout, fragment1);
                                    ft1.commit();
                                } catch (Exception e) {
//                                    nextface.setVisibility(View.INVISIBLE);
                                }
//                                fragment1 = new crop();
//                                Bundle bundle = new Bundle();
//                                fragment1.setArguments(bundle);
//                                bundle.putInt("x1", 30);
//                                bundle.putInt("y1", 30);
//                                bundle.putInt("x2", 115);
//                                bundle.putInt("y2", 140);
//                                fm1 = getSupportFragmentManager();
//                                ft1 = fm1.beginTransaction();
//                                ft1.replace(R.id.cropframelayout, fragment1);
//                                ft1.commit();


                            }
                        });




                        backface2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                counter--;

                                Toast.makeText(MainActivity.this, "counnter is :" + counter, Toast.LENGTH_SHORT).show();
                                try {
                                    fragment1 = new crop();
                                    Bundle bundle = new Bundle();
                                    fragment1.setArguments(bundle);

                                    bundle.putInt("y1", a.get(counter).get(0).intValue());
                                    bundle.putInt("x2", a.get(counter).get(1).intValue());
                                    bundle.putInt("y2", a.get(counter).get(2).intValue());
                                    bundle.putInt("x1", a.get(counter).get(3).intValue());

                                    fm1 = getSupportFragmentManager();
                                    ft1 = fm1.beginTransaction();
                                    ft1.replace(R.id.cropframelayout, fragment1);
                                    ft1.commit();
                                } catch (Exception e) {
//                                    nextface.setVisibility(View.INVISIBLE);
                                }


                            }
                        });

                        bmp32 = cameraphoto.copy(Bitmap.Config.RGB_565, true);
                        Utils.bitmapToMat(bmp32, mat);
                        Log.d("wwwwwwwwwwwww", "onClick: mat.size is :" + mat.rows() + "       " + mat.cols());


                        ArrayList pixel = new ArrayList();
//                        double[] pixel = new double[3];
//                        ArrayList pixel = new ArrayList();

//                        ArrayList rows = new ArrayList();
//                        ArrayList cols = new ArrayList();
//                        ArrayList<Double> [][] cols = new ArrayList[mat.rows()][mat.cols()];
//                        ArrayList[][] u = new ArrayList[mat.rows()][mat.cols()];
                        double[][][] cols = new double[mat.rows()][mat.cols()][3];
                        h = mat.rows();
                        w = mat.cols();
                        Log.d("see it", "onClick: w and h is :" + w + "  " + h);
                        for (int i = 0; i < mat.rows(); i++) {
//                            rows.clear();
                            for (int j = 0; j < mat.cols(); j++) {
//                                Log.d("seemat", "onClick: mat i is :" + mat.get(i, j)[0] + "  " + mat.get(i, j)[1] + "  " + mat.get(i, j)[2] + "  "
//                                        + mat.get(i, j)[3]);
//                                pixel = null;
//                                pixel = null;

//                                ArrayList<double[]> temp = null;
//                                double[] temp =  new double[3]; // use double[] instead of byte[]
//                                mat.get(i, j, temp);
//                                temp = (mat.get(i, j));
//                                Arrays.copyOf(original, original.length - 1)

//                                temp = Arrays.copyOf(mat.get(i, j), mat.get(i,j).length-1);

//                                rows.add(temp);
//                                temp = mat.col
//                                temp[4] = null;


//                                if (temp[0]!= 0.0){
//                                    Log.d("see temp", "onClick: see tem:"+temp[0]+temp[1]+temp[2]);
//                                }
//                                double[] temp = mat.get(i,j);
//                              try{
//                                  pixel.clear();
//                                  pixel.add((int)mat.get(i,j)[0]);
//                                  pixel.add((int)mat.get(i,j)[1]);
//                                  pixel.add((int)mat.get(i,j)[2]);
//                                  try{
//                                      rows.add(pixel);
//                                  }catch (Exception e){}
//                              }catch (Exception e){}

//                                pixel.clear();
//                                pixel.add((int)mat.get(i,j)[0]);
//                                pixel.add((int)mat.get(i,j)[1]);
//                                pixel.add((int)mat.get(i,j)[2]);
//                                double[] a = new double[3];
//                                a[0]=(int) mat.get(i,j)[0];
//                                a[1]=(int) mat.get(i,j)[1];
//                                a[2]=(int) mat.get(i,j)[2];
//                                rows.add(a);

//                                pixel.remove(3);


//                                pixel.add(temp[0]);
//                                pixel.add(temp[1]);
//                                pixel.add(temp[2]);

//                                pixel.add(temp );
//                                pixel[0]=temp[0];
//                                pixel[1]=temp[1];
//                                pixel[2]=temp[2];
//                                int a = (int) mat.get(i,j)[0];
//                                int a2 = (int) mat.get(i,j)[1];
//                                int a3 = (int) mat.get(i,j)[2];
//
//                                pixel.add(a);
//                                pixel.add(a2);
//                                pixel.add(a3);
//                                pixel.add ((int)mat.get(i, j)[0]);
//                                pixel.add((int)mat.get(i, j)[1]);
//                                pixel.add((int)mat.get(i, j)[2]);
//                                u[i][j].add(pixel);
//
//                                rows.add(pixel);

//                                rows.add(mat.get(i, j));
                                cols[i][j][0] = mat.get(i, j)[0];
                                cols[i][j][1] = mat.get(i, j)[1];
                                cols[i][j][2] = mat.get(i, j)[2];

                            }
//                            cols.add(rows);
                        }

//                        double[] a = mat.get(90, 90);
//                        pixel.add(2);
//                        pixel.add(a[0]);
//                        pixel.add((int) a[1]);
                        Log.d("here", "onClick: see cols" + cols);
//                        Bitmap bitmap;
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, cols);

//                        Log.d("see cols", "onClick: cols is here" + cols);
//                        Log.d("seemat", "onClick: mat is here" + mat.toString());
//                        for (int i =0; i<cols.size(); i++){}
//                            for (int j =0 ; j< rows.size(); j++){
//                                Log.d("k", "onClick: just just hrer"+ cols.get(i));
//                            }
//                        }

//                        datatopost = "[";
//                        for (int i = 0; i < mat.rows(); i++) {
//                            if (i != 0) {
//                                datatopost = datatopost + ",[";
//                            } else if (i == 0)
//                                datatopost = datatopost + "[";
//                            for (int j = 0; j < mat.cols(); j++) {
//                                if (j != 0) {
//                                    datatopost = datatopost + ",[";
//                                } else if (j == 0)
//                                    datatopost = datatopost + "[";
//
//                                datatopost = datatopost + mat.get(i, j)[0] + ",";
//                                datatopost = datatopost + mat.get(i, j)[0] + ",";
//                                datatopost = datatopost + mat.get(i, j)[0] + "]";
////                                pix[index] = 0xff000000 | (RGB[0] << 16) | (RGB[1] << 8) | RGB[2];
//
//
//                            }
//                            datatopost = datatopost + "]";
//                            if (i == mat.rows() - 1) {
//                                Log.d("see datatopost", "onClick: see datatopost" + datatopost);
//                            }
//                        }
//                        datatopost = datatopost + "]";
//
//                        Log.d("datatopost", "onClick: see completed datatopost" + datatopost);

//                        RequestInterface totalscoreservice = ApiClient.getClient().create(RequestInterface.class);
//                        Call<String> calltotalscore = totalscoreservice.callcontent(mat);
//                        Log.d("mat ", "onClick: mat is sent  " + mat.get(0, 0)[0]);
//                        calltotalscore.enqueue(new retrofit2.Callback<String>() {
//                            @Override
//                            public void onResponse(Call<String> call, Response<String> response) {
//                                Log.d("wwwwwwwwwwwwwwwwww", "onResponse: you are inside on response:  " + response.message()
//                                        + "  " + response.body());
//                                Log.d("wwwwwwwwwwwwwwwwww", "onResponse: response is being done");
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<String> call, Throwable t) {
//                                Log.d("wwwwwwwwwwwwwwwwww", "userscoin\nonFailure: post wasn't successfully" + t);
//                            }
//                        });


                        //second request:
                        RequestInterface totalscoreservice2 = ApiClient.getClient().create(RequestInterface.class);
                        //[[a,b,c,d]]
                        Call<Object> calltotalscore2 = totalscoreservice2.callcontent2(cols);
                        calltotalscore2.enqueue(new retrofit2.Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                Log.d("wwwwwwwwwwwwwwwwww", "222222222222222onResponse: you are inside on response:  " + response.message()
                                        + " response body is :  " + response.body());
                                Log.d("wwwwwwwwwwwwwwwwww", "222222222222222onResponse: response is being done");
                                Toast.makeText(MainActivity.this, "the response " + response.body(), Toast.LENGTH_LONG).show();
//
//                                A[] a = new A[response.body().length];
//                                int i = 0;
//                                for (Object o : objArray) {
//                                    a[i++] = (A) o;
//                                }

//                              try{
                                a = (ArrayList<ArrayList<Double>>) response.body();
//                                  Log.d("see response as a:", "onResponse: a is : +" + a[0] + "  " + a[1] + "  " + a[2] + "  " + a[3] + "  ");
                                for (int i = 0; i < a.size(); i++) {
//                                    ArrayList<Double> b = new ArrayList();
//                                    b.add(a.get(i));
                                    Log.d("see response as a:", "onResponse: a is : +" + a.get(i).get(0) + +a.get(i).get(1) + a.get(i).get(2) + a.get(i).get(3) + " b type is :  ");
                                }
//                                fragment1 = new crop();
//                                fm1 = getSupportFragmentManager();
//                                ft1 = fm1.beginTransaction();
//                                ft1.replace(R.id.cropframelayout, fragment1);
//                                ft1.commit();

//                              }catch (Exception e){}
                                fragment1 = new crop();
                                Bundle bundle = new Bundle();
                                fragment1.setArguments(bundle);
//                        bundle.putInt("x1", 10);
//                        bundle.putInt("y1", 10);
//                        bundle.putInt("x2", 55);
//                        bundle.putInt("y2", 90);
                                counter = 0;
                                try {
                                    bundle.putInt("y1", a.get(counter).get(0).intValue());
                                    bundle.putInt("x2", a.get(counter).get(1).intValue());
                                    bundle.putInt("y2", a.get(counter).get(2).intValue());
                                    bundle.putInt("x1", a.get(counter).get(3).intValue());
                                    fm1 = getSupportFragmentManager();
                                    ft1 = fm1.beginTransaction();
                                    ft1.replace(R.id.cropframelayout, fragment1);
                                    ft1.commit();
                                } catch (Exception e) {
                                }

                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                Log.d("wwwwwwwwwwwwwwwwww", "222222222222222userscoin\nonFailure: post wasn't successfully" + t);
                            }
                        });


                    } else {
                        Toast.makeText(MainActivity.this, "Please choose a photo", Toast.LENGTH_SHORT).show();
                    }
                }
//                java.lang.IllegalStateException: Expected a string but was BEGIN_ARRAY at line 1 column 2 path $

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Tada).duration(500).repeat(0).playOn(view);
                if (titletxt.getText().toString().equals(STEP_1) && photoselection) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.anonymous));
                    photoselection = false;
                    back.setVisibility(View.INVISIBLE);
                    backtext.setVisibility(View.INVISIBLE);
                    nextface.setVisibility(View.VISIBLE);

                } else if (titletxt.getText().toString().equals(STEP_2)) {
                    titletxt.setText(STEP_1);
                    camerabtn.setVisibility(View.VISIBLE);
                    gallerybtn.setVisibility(View.VISIBLE);
                    insertbtn.setVisibility(View.INVISIBLE);
                    detectbtn.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.VISIBLE);
                    nexttext.setVisibility(View.VISIBLE);
//                    ft1.remove(fragment1);
                    if (fragment1 != null)
                        getSupportFragmentManager().beginTransaction().remove(fragment1).commit();


                } else if (titletxt.getText().toString().equals(STEP_3_1)) {
                    nameedittext.setVisibility(View.GONE);
                    titletxt.setText(STEP_2);
                    insertbtn.setVisibility(View.VISIBLE);
                    detectbtn.setVisibility(View.VISIBLE);
                    next.setVisibility(View.INVISIBLE);
                    nexttext.setVisibility(View.INVISIBLE);

                }

            }
        });


        detectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Tada).duration(500).repeat(0).playOn(view);

            }
        });
        insertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Tada).duration(500).repeat(0).playOn(view);
                insertbtn.setVisibility(View.INVISIBLE);
                detectbtn.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
                nexttext.setVisibility(View.VISIBLE);
                nameedittext.setVisibility(View.VISIBLE);
                titletxt.setText(STEP_3_1);


            }
        });

        camerabtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Tada).duration(500).repeat(0).playOn(view);

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Cam_Req);
                photoselection = true;
                back.setVisibility(View.VISIBLE);
                backtext.setVisibility(View.VISIBLE);

            }
        });

        gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Tada).duration(500).repeat(0).playOn(view);

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_OK);
                photoselection = true;
                back.setVisibility(View.VISIBLE);
                backtext.setVisibility(View.VISIBLE);
            }
        });

    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == Cam_Req) {

//            Mat m = Highgui.imread("/media/path_to_image");

            cameraphoto = (Bitmap) data.getExtras().get("data");
//            Mat mat = cameraphoto;
//            Mat img = imread(data.getExtras().get("data"));
//            Bitmap bmp32 = cameraphoto.copy(Bitmap.Config.ARGB_8888, true);
//            Utils.bitmapToMat(bmp32, mat);

            imagetocrop2 = cameraphoto;
            image.setImageBitmap(cameraphoto);

            imagetocrop = new BitmapDrawable(getResources(), cameraphoto);
//            List<double[]> cols = new List<double[]>();
//            List<double[]> rows = new List<double[]>();
            ArrayList cols = new ArrayList();
            ArrayList rows = new ArrayList();

//            double[] rgb;
//            for (int i = 0; i < mat.rows(); i++) {
//                for (int j = 0; j < mat.cols(); j++) {
//                    rgb = mat.get(i, j);
//                    Log.d("rgb", "onActivityResult: see rgb mat  " + rgb[0] + "  " + rgb[1] + "  " + rgb[2]+ "  " + rgb[3]);
//                    cols.add(rgb);
////                    Log.d("seemat", "onActivityResult: mat item is "+ mat.get(i,j).hashCode());
//                }
//                rows.add(cols);
//            }
//
//            for (int i =0; i<cols.size(); i++){
////                for (int j =0 ; j<rows.size(); j++){
//                    Log.d("see cols", "onActivityResult: see data  " + cols.get(i) );
////                }
//            }
            //            double[] colors = imagetocrop.get(123, 123);
//            Log.v(tag, "-->"+ colors[0] +";"+ colors[1] +";"+ colors[2] +"");
//            Canvas canvas = new Canvas(cameraphoto);
//            canvas.drawColor(Color.RED); // now all bitmap pixels became red
//            image.setTag(imagetocrop);
//            cropit();

//            image.setImageDrawable(ob);

//            for (int i=0 ; i< imagetocrop2.length; i++)
//                Log.d("bitmap", "onActivityResult: imagecrop2:"+ imagetocrop2.getNinePatchChunk()[i]);
////            imagetocrop2.getColorSpace();
//                imagetocrop2.getPixels();


//            int height = imagetocrop2.getHeight();
//            int width = imagetocrop2.getWidth();
//            int[] pix = new int[width * height];
//            imagetocrop2.getPixels(pix, 0, width, 0, 0, width, height);

//            int R, G, B, Y;

//            [[[3, 4, 5],[3, 4, 7],[3, 4, 6]],
//             [[5, 6, 5],[5, 6, 7],[5, 6, 6]]]


//            ArrayList h = new ArrayList();
//
//            for (int y = 0; y < height; y++) {
//                ArrayList w = new ArrayList();
//                for (int x = 0; x < width; x++) {
//                    double[] colors = imagetocrop.get(123, 123);
//                    Log.v(tag, "-->"+ colors[0] +";"+ colors[1] +";"+ colors[2] +"");
//                    Log.d("just here", "onActivityResult:just here"+ y +"  x = " + x);
//                    int[] RGB = new int[3];
//                    int index = y * width + x;
//                    RGB[0] = (pix[index] >> 16) & 0xff;     //bitwise shifting
//                    RGB[1] = (pix[index] >> 8) & 0xff;
//                    RGB[2] = pix[index] & 0xff;
//                    Log.d("colors", "onActivityResult: rgb is:  " + RGB[0] + "   " + RGB[1] + "  " + RGB[2]);
//                    //R,G.B - Red, Green, Blue
//                    //to restore the values after RGB modification, use
//                    //next statement
//
//                    w.add(RGB);
//                    pix[index] = 0xff000000 | (RGB[0] << 16) | (RGB[1] << 8) | RGB[2];
//                }
////
//                h.add(w);
////                w.clear();
//            }
//
//
//            for (int q = 0; q < h.size(); q++) {
////                for (int e=0; e<w.size(); e++){
//                Log.d("result of arrays are:", "onActivityResult: see h" + h.get(q).toString());
//
//
////                }
//            }

//            for (int q = 0; q < w.size(); q++) {
////                for (int e=0; e<w.size(); e++){
//                Log.d("result of arrays are:", "onActivityResult: see w" + w.get(q));
//
//
////                }
//            }


        }
        if (reqCode == RESULT_OK)

        {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                image.setImageBitmap(selectedImage);
                imagetocrop2 = selectedImage;
                imagetocrop = new BitmapDrawable(getResources(), selectedImage);

//                image.setBackgroundDrawable(ob);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else

        {
            Toast.makeText(MainActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }


}
