package me.littlecheesecake.cropimagelayout;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static me.littlecheesecake.cropimagelayout.ApiClient.BASE_URL;
import static me.littlecheesecake.cropimagelayout.Menuactivity.pixx1;
import static me.littlecheesecake.cropimagelayout.Menuactivity.pixx2;
import static me.littlecheesecake.cropimagelayout.Menuactivity.pixy1;
import static me.littlecheesecake.cropimagelayout.Menuactivity.pixy2;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mainfragment extends Fragment {

    RelativeLayout waitlayout;
    public static TextView boxText3;
    public static ArrayList<ArrayList<Double>> responsephoto = new ArrayList();
    public static int w, h;
    public static Bitmap bmp32;
    private static final int Cam_Req = 1;
    private static final int RESULT_OK = 2;
    Bitmap cameraphoto;
    //    Button searchsomeone;
    Bitmap selectedImage;
    public static Bitmap imagetocrop2;
    static String datatopost;
    static public ImageView image, next, back;
    TextView titletxt;
    EditText nameedittext;
    boolean photoselection = false;
    //    BitmapDrawable imagetocrop;
    public static Drawable imagetocrop;
    ListView suspectslistview;
    FrameLayout fragmentlayout;
    ImageView canclebtn;

    FragmentTransaction ft1;
    FragmentManager fm1;
    crop fragment1;
    public static Mat mat;
    static int counter;
    Button camerabtn, gallerybtn, insertbtn, detectbtn;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(getActivity()) {
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
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, getActivity(), mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }


    public Mainfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mainfragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView nextface = (ImageView) getView().findViewById(R.id.nextface);
        final ImageView backface2 = (ImageView) getView().findViewById(R.id.backface2);
        suspectslistview = (ListView) getView().findViewById(R.id.allnameslistview);
//        searchsomeone = (Button) getView().findViewById(R.id.searchsomeone);

        fragmentlayout = (FrameLayout) getView().findViewById(R.id.cropframelayout);
        camerabtn = (Button) getView().findViewById(R.id.camerabtn);
        gallerybtn = (Button) getView().findViewById(R.id.gallerybtn);
        insertbtn = (Button) getView().findViewById(R.id.insertbtn);
        detectbtn = (Button) getView().findViewById(R.id.detectbtn);
        nameedittext = (EditText) getView().findViewById(R.id.nameedittext);
        image = (ImageView) getView().findViewById(R.id.imageView);
        next = (ImageView) getView().findViewById(R.id.next);
        back = (ImageView) getView().findViewById(R.id.back);
        titletxt = (TextView) getView().findViewById(R.id.titletxt);
        boxText3 = (TextView) getView().findViewById(R.id.box_text3);
        canclebtn = (ImageView) getView().findViewById(R.id.canclebtn);

        waitlayout = (RelativeLayout) getView().findViewById(R.id.waitlayout);
        final String STEP_1, STEP_2, STEP_3_1, STEP_3_2, STEP_4;
        STEP_1 = "Please choose a photo";
        STEP_2 = "Edit and crop";
        STEP_3_1 = "Add to database";
        STEP_3_2 = "Recognize photo";
        STEP_4 = "Add to database";
        titletxt.setText(STEP_1);
//        nextface.setVisibility(View.VISIBLE);
        image.setImageDrawable(getResources().getDrawable(R.drawable.anonymous));

//        String[] SamsungPhones = new String[]{"Galaxy S", "Galaxy S2",
//                "Galaxy Note", "Galaxy Beam", "Galaxy Ace Plus", "Galaxy S3",
//                "Galaxy S Advance", "Galaxy Wave 3", "Galaxy Wave Y",
//                "Galaxy Nexus", "Galaxy W", "Galaxy Y", "Galaxy Mini",
//                "Galaxy Gio", "Galaxy Wave", "Galaxy Wave 2"};
//
//        // Bind array strings into an adapter
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1,
//                SamsungPhones);
//        suspectslistview.setAdapter(adapter);

//        ProgressBar progressBar = (ProgressBar)getView().findViewById(R.id.spin_kit);
//        DoubleBounce doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);


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


        //get all faces:
//        RequestInterface totalscoreservice2 = ApiClient.getClient().create(RequestInterface.class);
//        Call<List<String>> calltotalscore2 = totalscoreservice2.getallfaces();
//        Log.d("getallfaces", "getallfaces request is going to be sent ");
//
//        calltotalscore2.enqueue(new retrofit2.Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                Log.d("getallfaces", " getallfaces  onResponse: you are inside on response:  " + response.message()
//                        + " response body is :  " + response.body());
////                Log.d("sendingcropedimage", "sendingcropedimage222222222222222onResponse: response is being done");
//                Toast.makeText(MainActivity.this, "getallfaces the response " + response.body(), Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//                Log.d("getallfaces", "getallfaces \nonFailure: post wasn't successfully" + t);
//            }
//        });


//        searchsomeone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(getActivity(), Menuactivity.class);
//                startActivity(i);
//            }
//        });
        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waitlayout.setVisibility(View.INVISIBLE);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Tada).duration(500).repeat(0).playOn(view);
//                cropit();
                if (titletxt.getText().toString().equals(STEP_1)) {

//                    if (!(image.getBackground().getConstantState() == getResources().getDrawable(R.drawable.anonymous).getConstantState())) {
                    if (photoselection) {
                        waitlayout.setVisibility(View.VISIBLE);
                        camerabtn.setVisibility(View.INVISIBLE);
                        gallerybtn.setVisibility(View.INVISIBLE);
                        titletxt.setText(STEP_2);
//                        insertbtn.setVisibility(View.VISIBLE);
//                        detectbtn.setVisibility(View.VISIBLE);
                        next.setVisibility(View.INVISIBLE);


                        nextface.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                counter++;
//                                boxText3.setText("cropint " + (responsephoto.size() - counter + 1) + "th face");
                                if (counter < responsephoto.size() && responsephoto.size() != 1) {
                                    backface2.setVisibility(View.VISIBLE);
                                    Toast.makeText(getActivity(), "counter is :" + counter, Toast.LENGTH_SHORT).show();
                                    try {
                                        fragment1 = new crop();
                                        Bundle bundle = new Bundle();
                                        fragment1.setArguments(bundle);

                                        bundle.putInt("y1", responsephoto.get(counter).get(0).intValue());
                                        bundle.putInt("x2", responsephoto.get(counter).get(1).intValue());
                                        bundle.putInt("y2", responsephoto.get(counter).get(2).intValue());
                                        bundle.putInt("x1", responsephoto.get(counter).get(3).intValue());

                                        fm1 = getActivity().getSupportFragmentManager();
                                        ft1 = fm1.beginTransaction();
                                        ft1.replace(R.id.cropframelayout, fragment1);
                                        ft1.commit();
                                    } catch (Exception e) {
//                                    nextface.setVisibility(View.INVISIBLE);
                                    }

                                }
                                if (counter == responsephoto.size() - 1) {
                                    nextface.setVisibility(View.INVISIBLE);
                                }
                            }
                        });

//first test crop:
//                        try {
//                            fragment1 = new crop();
//                            Bundle bundle = new Bundle();
//                            fragment1.setArguments(bundle);
//
//                            bundle.putInt("y1", 4);
//                            bundle.putInt("x2", 44);
//                            bundle.putInt("y2", 40);
//                            bundle.putInt("x1", 5);
//
//                            fm1 = getActivity().getSupportFragmentManager();
//                            ft1 = fm1.beginTransaction();
//                            ft1.replace(R.id.cropframelayout, fragment1);
//                            ft1.commit();
//                        } catch (Exception e) {
////                                    nextface.setVisibility(View.INVISIBLE);
//                            }


                        backface2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                nextface.setVisibility(View.VISIBLE);
                                counter--;
//                                boxText3.setText(responsephoto.size()+" faces are found \n "+"croping " + (responsephoto.size() - counter + 1) + "th face");
                                if (counter > -1) {
                                    Toast.makeText(getActivity(), "counnter is :" + counter, Toast.LENGTH_SHORT).show();
                                    try {
                                        fragment1 = new crop();
                                        Bundle bundle = new Bundle();
                                        fragment1.setArguments(bundle);

                                        bundle.putInt("y1", responsephoto.get(counter).get(0).intValue());
                                        bundle.putInt("x2", responsephoto.get(counter).get(1).intValue());
                                        bundle.putInt("y2", responsephoto.get(counter).get(2).intValue());
                                        bundle.putInt("x1", responsephoto.get(counter).get(3).intValue());

                                        fm1 = getActivity().getSupportFragmentManager();
                                        ft1 = fm1.beginTransaction();
                                        ft1.replace(R.id.cropframelayout, fragment1);

                                        ft1.commit();
                                    } catch (Exception e) {
//                                    nextface.setVisibility(View.INVISIBLE);
                                    }
                                }
                                if (counter == 0) {
                                    backface2.setVisibility(View.INVISIBLE);
                                }


                            }
                        });

                        bmp32 = imagetocrop2.copy(Bitmap.Config.RGB_565, true);
                        Utils.bitmapToMat(bmp32, mat);
                        Log.d("wwwwwwwwwwwww", "onClick: mat.size is :" + mat.rows() + "       " + mat.cols());

                        double[][][] cols = new double[mat.rows()][mat.cols()][3];
                        h = mat.rows();
                        w = mat.cols();
                        Log.d("see it", "onClick: w and h is :" + w + "  " + h + mat);
                        for (int i = 0; i < mat.rows(); i++) {
//                            rows.clear();
                            for (int j = 0; j < mat.cols(); j++) {

                                cols[i][j][2] = mat.get(i, j)[0];
                                cols[i][j][1] = mat.get(i, j)[1];
                                cols[i][j][0] = mat.get(i, j)[2];

                            }
//                            cols.add(rows);
                        }

                        Log.d("here", "onClick: see cols" + cols);

                        waitlayout.setVisibility(View.VISIBLE);
                        RequestInterface totalscoreservice2 = ApiClient.getClient().create(RequestInterface.class);
                        //[[responsephoto,b,c,d]]
                        Call<Object> calltotalscore2 = totalscoreservice2.callcontent2(cols);
                        calltotalscore2.enqueue(new retrofit2.Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                if (titletxt.getText() == STEP_2){
                                    insertbtn.setVisibility(View.VISIBLE);
                                    detectbtn.setVisibility(View.VISIBLE);
                                    waitlayout.setVisibility(View.INVISIBLE);

                                    Log.d("wwwwwwwwwwwwwwwwww", "222222222222222onResponse: you are inside on response:  " + response.message()
                                            + " response body is :  " + response.body());
                                    Log.d("wwwwwwwwwwwwwwwwww", "222222222222222onResponse: response is being done");
                                    Toast.makeText(getActivity(), "the response " + response.body(), Toast.LENGTH_LONG).show();
//
//                                A[] responsephoto = new A[response.body().length];
//                                int i = 0;
//                                for (Object o : objArray) {
//                                    responsephoto[i++] = (A) o;
//                                }

//                              try{
                                    responsephoto = (ArrayList<ArrayList<Double>>) response.body();
                                    if (responsephoto.size() > 1) {
                                        nextface.setVisibility(View.VISIBLE);
                                    }
//                                  Log.d("see response as responsephoto:", "onResponse: responsephoto is : +" + responsephoto[0] + "  " + responsephoto[1] + "  " + responsephoto[2] + "  " + responsephoto[3] + "  ");
//                                for (int i = 0; i < responsephoto.size(); i++) {
////                                    ArrayList<Double> b = new ArrayList();
////                                    b.add(responsephoto.get(i));
//                                    Log.d("see response as responsephoto:", "onResponse: responsephoto is : +" + responsephoto.get(i).get(0) + +responsephoto.get(i).get(1) + responsephoto.get(i).get(2) + responsephoto.get(i).get(3) + " b type is :  ");
//                                }
//                                fragment1 = new crop();
//                                fm1 = getSupportFragmentManager();
//                                ft1 = fm1.beginTransaction();
//                                ft1.replace(R.id.cropframelayout, fragment1);
//                                ft1.commit();

//                              }catch (Exception e){}
                                    fragment1 = new crop();
                                    Bundle bundle = new Bundle();
                                    fragment1.setArguments(bundle);
                                    counter = 0;
                                    try {
                                        bundle.putInt("y1", responsephoto.get(counter).get(0).intValue());
                                        bundle.putInt("x2", responsephoto.get(counter).get(1).intValue());
                                        bundle.putInt("y2", responsephoto.get(counter).get(2).intValue());
                                        bundle.putInt("x1", responsephoto.get(counter).get(3).intValue());
                                        fm1 = getActivity().getSupportFragmentManager();
                                        ft1 = fm1.beginTransaction();
                                        ft1.replace(R.id.cropframelayout, fragment1);

                                        ft1.commit();
                                    } catch (Exception e) {
                                    }

                                }

                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                Log.d("wwwwwwwwwwwwwwwwww", "222222222222222userscoin\nonFailure: post wasn't successfully" + t);
                                waitlayout.setVisibility(View.INVISIBLE);
                                Toast.makeText(getActivity(), "request failed \n" + t, Toast.LENGTH_SHORT).show();
                            }
                        });



                    } else {
                        Toast.makeText(getActivity(), "Please choose a photo", Toast.LENGTH_SHORT).show();
                    }
                }
//                java.lang.IllegalStateException: Expected responsephoto string but was BEGIN_ARRAY at line 1 column 2 path $
                if (titletxt.getText() == STEP_4) {
                    Log.d("inserting", "sendText:  " + pixx1 + "    " + pixy1 + "    " + pixx2 + "    " + pixy2);
//                    if (pixx1 == 0 && pixx2 == 0 && pixy1 == 0 && pixy2 == 0) {

//                        if (responsephoto != null) {
                            pixy1 = responsephoto.get(counter).get(0).intValue();
                            pixx2 = responsephoto.get(counter).get(1).intValue();
                            pixy2 = responsephoto.get(counter).get(2).intValue();
                            pixx1 = responsephoto.get(counter).get(3).intValue();
//                        } else {
//
//                            Toast.makeText(getActivity(), "no face detected", Toast.LENGTH_SHORT).show();
//                        }


//                    }
                    Log.d("inserting", "sendText:  " + pixx1 + "    " + pixy1 + "    " + pixx2 + "    " + pixy2);


                    bmp32 = imagetocrop2.copy(Bitmap.Config.RGB_565, true);
                    Utils.bitmapToMat(bmp32, mat);
                    Log.d("inserting", "sendText: mat array is :" + mat.rows() + "   " + mat.cols());

                    double[][][] colstoinsert = new double[pixy2 - pixy1][pixx2 - pixx1][3];
                    for (int i = pixy1, o = 0; o < pixy2 - pixy1 && i < pixy2; o++, i++) {
//                            rows.clear();
                        for (int j = pixx1, p = 0; p < pixx2 - pixx1 && j < pixx2; p++, j++) {

                            colstoinsert[o][p][2] = mat.get(i, j)[0];
                            colstoinsert[o][p][1] = mat.get(i, j)[1];
                            colstoinsert[o][p][0] = mat.get(i, j)[2];

                        }
//                            cols.add(rows);
                    }


                    Log.d("inserting", "onClick: this is insertcols:"+colstoinsert);
                    waitlayout.setVisibility(View.VISIBLE);
                    RequestInterface insertrequest = ApiClient.getClient().create(RequestInterface.class);
                    //[[responsephoto,b,c,d]]
                    Call<Object> insertrequestcall = insertrequest.insertface(BASE_URL + nameedittext.getText().toString().toLowerCase(), colstoinsert);
                    Log.d("inserting", "onClick: the url is " + BASE_URL + nameedittext.getText().toString().toLowerCase());
                    insertrequestcall.enqueue(new retrofit2.Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            waitlayout.setVisibility(View.INVISIBLE);

                            Log.d("inserting", "inserting: you are inside on response:  " + response.message()
                                    + " response body is :  " + response.body());
                            Log.d("inserting", "inserting: response is being done");
                            Toast.makeText(getActivity(), "the response " + response.body() +"\n"+ response.message(), Toast.LENGTH_LONG).show();
//
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Log.d("inserting", "inserting\nonFailure: post wasn't successfully" + t);
                            waitlayout.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(), "request failed \n" + t, Toast.LENGTH_SHORT).show();
                        }
                    });


                }
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
                    next.setVisibility(View.VISIBLE);
                } else if (titletxt.getText().toString().equals(STEP_2)) {
                    titletxt.setText(STEP_1);
                    camerabtn.setVisibility(View.VISIBLE);
                    gallerybtn.setVisibility(View.VISIBLE);
                    insertbtn.setVisibility(View.INVISIBLE);
                    detectbtn.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.VISIBLE);
                    nextface.setVisibility(View.INVISIBLE);
                    backface2.setVisibility(View.INVISIBLE);
//                    ft1.remove(fragment1);

                    try {
                        if (!ft1.isEmpty()) {
                            ft1.remove(fragment1);
                            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
                            getFragmentManager().beginTransaction().remove(fragment1).commitAllowingStateLoss();
//                        ft1.transaction.addToBackStack(null);
                            getActivity().getSupportFragmentManager().popBackStack();


                            // Create new fragment and transaction
                            Fragment newFragment = new crop();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
                            transaction.replace(R.id.cropframelayout, newFragment);
                            transaction.addToBackStack(null);

// Commit the transaction
                            transaction.commit();


                            FrameLayout layout = (FrameLayout) getView().findViewById(R.id.cropframelayout);
                            layout.removeAllViewsInLayout();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();


                        }


                    } catch (Exception e) {
                    }//                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment1).commit();


                } else if (titletxt.getText().toString().equals(STEP_3_1)) {
                    nameedittext.setVisibility(View.GONE);
                    titletxt.setText(STEP_2);
                    insertbtn.setVisibility(View.VISIBLE);
                    detectbtn.setVisibility(View.VISIBLE);
                    next.setVisibility(View.INVISIBLE);


                }

            }
        });


        detectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Tada).duration(500).repeat(0).playOn(view);
                Log.d("sendingcropedimage", "sendText:  " + pixx1 + "    " + pixy1 + "    " + pixx2 + "    " + pixy2);
                if (pixx1 == 0 && pixx2 == 0 && pixy1 == 0 && pixy2 == 0) {

                    if (responsephoto != null) {
                        pixy1 = responsephoto.get(counter).get(0).intValue();
                        pixx2 = responsephoto.get(counter).get(1).intValue();
                        pixy2 = responsephoto.get(counter).get(2).intValue();
                        pixx1 = responsephoto.get(counter).get(3).intValue();
                    } else {

                        Toast.makeText(getActivity(), "no face detected", Toast.LENGTH_SHORT).show();
                    }


                }
                Log.d("sendingcropedimage", "sendText:  " + pixx1 + "    " + pixy1 + "    " + pixx2 + "    " + pixy2);


                bmp32 = imagetocrop2.copy(Bitmap.Config.RGB_565, true);
                Utils.bitmapToMat(bmp32, mat);
                Log.d("sendingcropedimage", "sendText: mat array is :" + mat.rows() + "   " + mat.cols());

                double[][][] cols = new double[pixy2 - pixy1][pixx2 - pixx1][3];
                for (int i = pixy1, o = 0; o < pixy2 - pixy1 && i < pixy2; o++, i++) {
//                            rows.clear();
                    for (int j = pixx1, p = 0; p < pixx2 - pixx1 && j < pixx2; p++, j++) {

                        cols[o][p][2] = mat.get(i, j)[0];
                        cols[o][p][1] = mat.get(i, j)[1];
                        cols[o][p][0] = mat.get(i, j)[2];

                    }
//                            cols.add(rows);
                }


                waitlayout.setVisibility(View.VISIBLE);
                RequestInterface totalscoreservice2 = ApiClient.getClient().create(RequestInterface.class);
                Call<Object> calltotalscore2 = totalscoreservice2.callrecognize(cols);
                Log.d("sendingcropedimage", "sendingcropedimage request is going to be sent ");

                calltotalscore2.enqueue(new retrofit2.Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        waitlayout.setVisibility(View.INVISIBLE);
                        Log.d("sendingcropedimage", "sendingcropedimage222222222222222onResponse: you are inside on response:  " + response.message()
                                + " response body is :  " + response.body());

                        if (response.body() != null) {
                            List<List> suspects = new ArrayList();
                            suspects = (List<List>) response.body();
//                Log.d("sendingcropedimage", "onResponse: " + suspects.get(0).get(0));

                            Log.d("sendingcropedimage", "sendingcropedimage222222222222222onResponse: response is being done");
//                            Toast.makeText(getActivity(), "the response " + response.body(), Toast.LENGTH_LONG).show();

                            String[] values = new String[suspects.size()];
//                values[0] = String.valueOf(suspects.get(0));
                            for (int i = 0; i < suspects.size(); i++) {
                                values[i] = String.valueOf(suspects.get(i).get(0)) + "\n" + String.valueOf(suspects.get(i).get(1));

                            }
//                                suspectslistview = (ListView) getView().findViewById(R.id.listviewmain);
//
//                                // Bind array strings into an adapter
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_list_item_1, android.R.id.text1,
                                    values);
//                                suspectslistview.setAdapter(adapter);
//                                suspectslistview.setVisibility(View.VISIBLE);

//                            image.setVisibility(View.INVISIBLE);


                            //removing fragment:
                            //                                ft1.remove(fragment1).commit();
//                                fragmentlayout.setVisibility(View.INVISIBLE);
//                                ft1.hide(fragment1);

//                            ft1.remove(fragment1);
//                            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
//                            getFragmentManager().beginTransaction().remove(fragment1).commitAllowingStateLoss();
////                        ft1.transaction.addToBackStack(null);
//                            getActivity().getSupportFragmentManager().popBackStack();
//
//
//                            // Create new fragment and transaction
//                            Fragment newFragment = new crop();
//                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//// Replace whatever is in the fragment_container view with this fragment,
//// and add the transaction to the back stack
//                            transaction.replace(R.id.cropframelayout, newFragment);
//                            transaction.addToBackStack(null);
//
//// Commit the transaction
//                            transaction.commit();
//
//
//                            FrameLayout layout = (FrameLayout) getView().findViewById(R.id.cropframelayout);
//                            layout.removeAllViewsInLayout();
//                            FragmentTransaction ft = getFragmentManager().beginTransaction();


                            //dialog:
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
// ...Irrelevant code for customizing the buttons and title
                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
                            dialogBuilder.setView(dialogView);

                            ListView suspectslistview = (ListView) dialogView.findViewById(R.id.listviewmain);
                            suspectslistview.setAdapter(adapter);
                            suspectslistview.setVisibility(View.VISIBLE);
                            AlertDialog alertDialog = dialogBuilder.create();
                            alertDialog.show();
                            Log.d("here :|", "onResponse: adapter is set and dialog should be showing  some data eeeeeeeeeewwwwwwwwwwwww");


//                            } catch (Exception e) {
//                            }
                        } else {
                            Toast.makeText(getActivity(), "try another photo \n" + response.message(), Toast.LENGTH_SHORT).show();
                        }
//                a = (ArrayList<ArrayList<Double>>) response.body();
////                                  Log.d("see response as a:", "onResponse: a is : +" + a[0] + "  " + a[1] + "  " + a[2] + "  " + a[3] + "  ");
//                for (int i = 0; i < a.size(); i++) {
////                                    ArrayList<Double> b = new ArrayList();
////                                    b.add(a.get(i));
//                    Log.d("see response as a:", "onResponse: a is : +" + a.get(i).get(0) + +a.get(i).get(1) + a.get(i).get(2) + a.get(i).get(3) + " b type is :  ");
//                }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        waitlayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "request failed \n" + t, Toast.LENGTH_SHORT).show();
                        Log.d("sendingcropedimage", "sendingcropedimage222222222222222userscoin\nonFailure: post wasn't successfully" + t);
                    }
                });


            }
        });
        insertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Tada).duration(500).repeat(0).playOn(view);
                insertbtn.setVisibility(View.INVISIBLE);
                detectbtn.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
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
            }
        });


    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == Cam_Req) {

            try {
                cameraphoto = (Bitmap) data.getExtras().get("data");

                imagetocrop2 = cameraphoto;
                image.setVisibility(View.VISIBLE);
                image.setImageBitmap(cameraphoto);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.imageView);
                params.addRule(RelativeLayout.LEFT_OF, R.id.centerdot);
                camerabtn.setLayoutParams(params);
                camerabtn.getLayoutParams().height = 160;
                camerabtn.getLayoutParams().width = 120;


                RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params2.addRule(RelativeLayout.BELOW, R.id.imageView);
                params2.addRule(RelativeLayout.RIGHT_OF, R.id.centerdot);
                gallerybtn.setLayoutParams(params2);
                gallerybtn.getLayoutParams().height = 160;
                gallerybtn.getLayoutParams().width = 120;
                imagetocrop = new BitmapDrawable(getResources(), cameraphoto);

            } catch (Exception e) {
            }


        } else if (reqCode == RESULT_OK) {
            try {
                if (data != null) {

                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    selectedImage = BitmapFactory.decodeStream(imageStream);
                    image.setImageBitmap(selectedImage);
                    image.setVisibility(View.VISIBLE);
                    imagetocrop2 = (Bitmap) selectedImage;
//                imagetocrop2 = getResizedBitmap(selectedImage,selectedImage.getWidth(),selectedImage.getHeight());
                    imagetocrop2 = Bitmap.createScaledBitmap(selectedImage, selectedImage.getWidth() / 10, selectedImage.getHeight() / 10, false);
                    image.setImageBitmap(imagetocrop2);
                    imagetocrop = new BitmapDrawable(getResources(), selectedImage);

//                image.setBackgroundDrawable(ob);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.BELOW, R.id.imageView);
                    params.addRule(RelativeLayout.LEFT_OF, R.id.centerdot);
                    camerabtn.setLayoutParams(params);
                    camerabtn.getLayoutParams().height = 180;
                    camerabtn.getLayoutParams().width = 110;


                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params2.addRule(RelativeLayout.BELOW, R.id.imageView);
                    params2.addRule(RelativeLayout.RIGHT_OF, R.id.centerdot);
                    gallerybtn.setLayoutParams(params2);
                    gallerybtn.getLayoutParams().height = 180;
                    gallerybtn.getLayoutParams().width = 110;
                    imagetocrop = new BitmapDrawable(getResources(), cameraphoto);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }

        }
    }

}
