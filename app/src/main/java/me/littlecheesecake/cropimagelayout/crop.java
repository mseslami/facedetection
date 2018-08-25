package me.littlecheesecake.cropimagelayout;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.littlecheesecake.croplayout.EditPhotoView;
import me.littlecheesecake.croplayout.EditableImage;
import me.littlecheesecake.croplayout.handler.OnBoxChangedListener;
import me.littlecheesecake.croplayout.model.ScalableBox;

import static me.littlecheesecake.cropimagelayout.MainActivity.a;
import static me.littlecheesecake.cropimagelayout.MainActivity.bmp32;


/**
 * A simple {@link Fragment} subclass.
 */
public class crop extends Fragment {

    boolean nextfaceb;
    static public int x1, x2, y1, y2;
    static ScalableBox box1, box2, box3;
    static List<ScalableBox> boxes;
    static EditableImage image;
    int facecount;
    EditPhotoView imageView;

    public crop() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crop, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        imageView = (EditPhotoView) getView().findViewById(R.id.editable_image);
        final TextView boxText = (TextView) getView().findViewById(R.id.box_text);
        final TextView boxText2 = (TextView) getView().findViewById(R.id.box_text2);
        final TextView boxText3 = (TextView) getView().findViewById(R.id.box_text3);
//        ImageView nextface = (ImageView) getView().findViewById(R.id.nextface);

        boxText2.setText(a.size() + " FACES ARE FOUND");
        //        final EditableImage image = new EditableImage(getActivity(), R.drawable.photo2);

        for (int i = 0; i < a.size(); i++) {
            Log.d("see response as a:", "onResponse: crop crop a is : +" + a.get(i).get(0) + +a.get(i).get(1) + a.get(i).get(2) + a.get(i).get(3) + " b type is :  ");
        }
        image = new EditableImage(bmp32);
//        ScalableBox box1 = new ScalableBox(25, 10, 640, 80);
//        ScalableBox box2 = new ScalableBox(2, 18, 680, 80);
//        ScalableBox box3 = new ScalableBox(250, 80, 400, 80);

        facecount = a.size();
        nextfaceb = true;


        boxText3.setText("cropint " + (a.size() - facecount + 1) + "th face");
//        String strtext = getArguments().getString("edttext");

        try {
//            y1 = a.get(facecount - 1).get(0).intValue();
//            x2 = a.get(facecount - 1).get(1).intValue();
//            y2 = a.get(facecount - 1).get(2).intValue();
//            x1 = a.get(facecount - 1).get(3).intValue();

            y1 = getArguments().getInt("y1");
            x2 = getArguments().getInt("x2");
            y2 = getArguments().getInt("y2");
            x1 = getArguments().getInt("x1");

//            y1 = 19;
//            x2 = 220;
//            y2 = 90;
//            x1 = 10;


            box1 = new ScalableBox(x1, y1, x2, y2);
            box2 = new ScalableBox(0, 0, 0, 0);
            box3 = new ScalableBox(0, 0, 0, 0);

            boxes = new ArrayList<>();
            boxes.add(box1);
            boxes.add(box2);
            boxes.add(box3);
            image.setBoxes(boxes);
            imageView.initView(getActivity(), image);

            boxText.setText("box: [" + x1 + "    " + y1 + "    " +
                    x2 + "    " + y2 + "    ]");


            //            doit();


            imageView.setOnBoxChangedListener(new OnBoxChangedListener() {
                @Override
                public void onChanged(int x1, int y1, int x2, int y2) {
                    boxText.setText("box: [" + x1 + "," + y1 + "],[" + x2 + "," + y2 + "]");
                    someMethod(x1, y1, x2, y2);
                }
            });

        } catch (Exception e) {
            Toast.makeText(getActivity(), "no fase is found :(", Toast.LENGTH_SHORT).show();
        }
//
//
//        nextface.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                doit();
////
////                y1 = 83;
////                x2 = 144;
////                y2 = 116;
////                x1 = 80;
////
////
////                box1 = new ScalableBox(x1, y1, x2, y2);
////                box2 = new ScalableBox(0, 0, 0, 0);
////                box3 = new ScalableBox(0, 0, 0, 0);
////
////                boxes = new ArrayList<>();
////                boxes.add(box1);
////                boxes.add(box2);
////                boxes.add(box3);
////                image.setBoxes(boxes);
//////                imageView.initView(getActivity(), image);
////                imageView.initView(getContext(),image);
////
////                boxText.setText("box: [" + x1 + "    " + y1 + "    " +
////                        x2 + "    " + y2 + "    ]");
//
//            }
//        });


//        for (int i =0 ; i<a.size(); i++){
//
//        }

//        try {
//            int y1 = a.get(0).get(0).intValue();
//            int x2 = a.get(0).get(1).intValue();
//            int y2 = a.get(0).get(2).intValue();
//            int x1 = a.get(0).get(3).intValue();
//
//            ScalableBox box1 = new ScalableBox(x1, y1, x2, y2);
//           ScalableBox box2 = new ScalableBox(0, 0, 0, 0);
//            ScalableBox box3 = new ScalableBox(0, 0, 0, 0);
//
//            List<ScalableBox> boxes = new ArrayList<>();
//            boxes.add(box1);
//            boxes.add(box2);
//            boxes.add(box3);
//            image.setBoxes(boxes);
//            imageView.initView(getActivity(), image);
//
//            boxText.setText("box: [" + x1 + "    " + y1 + "    " +
//                    x2 + "    " + y2 + "    ]");
//
//            imageView.setOnBoxChangedListener(new OnBoxChangedListener() {
//                @Override
//                public void onChanged(int x1, int y1, int x2, int y2) {
//                    boxText.setText("box: [" + x1 + "," + y1 + "],[" + x2 + "," + y2 + "]");
//                }
//            });
//
//        } catch (Exception e) {
//            Toast.makeText(getActivity(), "no fase is found :(", Toast.LENGTH_SHORT).show();
//        }


    }


//        public  void doit(){
//
//            y1 = 83;
//            x2 = 144;
//            y2 = 116;
//            x1 = 80;
//
//
//            box1 = new ScalableBox(x1, y1, x2, y2);
//            box2 = new ScalableBox(0, 0, 0, 0);
//            box3 = new ScalableBox(0, 0, 0, 0);
//
//            boxes = new ArrayList<>();
//            boxes.add(box1);
//            boxes.add(box2);
//            boxes.add(box3);
//            image.setBoxes(boxes);
////                imageView.initView(getActivity(), image);
//            imageView.initView(getActivity(),image);
//
////            boxText.setText("box: [" + x1 + "    " + y1 + "    " +
////                    x2 + "    " + y2 + "    ]");
//        }


    TextClicked mCallback;

    public interface TextClicked {
        public void sendText(int x1, int y1, int x2, int y2);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TextClicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TextClicked");
        }
    }

    public void someMethod(int x1, int y1, int x2, int y2) {
        mCallback.sendText(x1, y1, x2, y2);
    }

    @Override
    public void onDetach() {
//        someMethod();
        mCallback = null; // => avoid leaking, thanks @Deepscorn
        super.onDetach();
    }


}
