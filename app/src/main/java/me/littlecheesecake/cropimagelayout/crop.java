package me.littlecheesecake.cropimagelayout;


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
import static me.littlecheesecake.cropimagelayout.MainActivity.imagetocrop2;


/**
 * A simple {@link Fragment} subclass.
 */
public class crop extends Fragment {


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


        final EditPhotoView imageView = (EditPhotoView) getView().findViewById(R.id.editable_image);
        final TextView boxText = (TextView) getView().findViewById(R.id.box_text);
//        final EditableImage image = new EditableImage(getActivity(), R.drawable.photo2);

        for (int i = 0; i < a.size(); i++) {
            Log.d("see response as a:", "onResponse: crop crop a is : +" + a.get(i).get(0) + +a.get(i).get(1) + a.get(i).get(2) + a.get(i).get(3) + " b type is :  ");
        }
        final EditableImage image = new EditableImage(bmp32);
//        ScalableBox box1 = new ScalableBox(25, 10, 640, 80);
//        ScalableBox box2 = new ScalableBox(2, 18, 680, 80);
//        ScalableBox box3 = new ScalableBox(250, 80, 400, 80);


        try{

//            ScalableBox box1 = new ScalableBox(a.get(0).get(0).intValue(), a.get(0).get(1).intValue(),
//                    a.get(0).get(0).intValue()+a.get(0).get(2).intValue(), a.get(0).get(1).intValue()+a.get(0).get(3).intValue());

//            Log.d("wwwwwwwwwww", "onViewCreated: "+a.get(0).get(0).intValue()+"         "+ a.get(0).get(1).intValue()+"         "+
//                    a.get(0).get(2).intValue()+a.get(0).get(0).intValue()+"         "+ a.get(0).get(1).intValue()+a.get(0).get(3).intValue());


            int y1 = a.get(0).get(0).intValue();
            int x2 = a.get(0).get(1).intValue();
            int y2 = a.get(0).get(2).intValue();
            int x1 = a.get(0).get(3).intValue();

            ScalableBox box1 = new ScalableBox(x1,y1,x2,y2);

            //            ScalableBox box2 = new ScalableBox(a.get(0).get(0).intValue(), a.get(0).get(1).intValue(),
//                    a.get(0).get(0).intValue()+a.get(0).get(2).intValue(), a.get(0).get(1).intValue()+a.get(0).get(3).intValue());
//            ScalableBox box3 = new ScalableBox(a.get(0).get(0).intValue(), a.get(0).get(1).intValue(),
//                    a.get(0).get(0).intValue()+a.get(0).get(2).intValue(), a.get(0).get(1).intValue()+a.get(0).get(3).intValue());


//            ScalableBox box1 = new ScalableBox(20, 30, 200, 100);
            ScalableBox box2 = new ScalableBox(0, 0, 0, 0);
            ScalableBox box3 = new ScalableBox(0, 0, 0, 0);

            List<ScalableBox> boxes = new ArrayList<>();
            boxes.add(box1);
            boxes.add(box2);
            boxes.add(box3);
            image.setBoxes(boxes);
            imageView.initView(getActivity(), image);
//
//            boxText.setText("box: ["+a.get(0).get(0).intValue()+"    "+ a.get(0).get(1).intValue()+"    "+
//                    (int)(a.get(0).get(0).intValue()+a.get(0).get(2).intValue())+"    "+(int)( a.get(0).get(1).intValue()+a.get(0).get(3).intValue())+"    ]");


            boxText.setText("box: ["+x1+"    "+ y1+"    "+
                    x2+"    "+y2+"    ]");

            imageView.setOnBoxChangedListener(new OnBoxChangedListener() {
                @Override
                public void onChanged(int x1, int y1, int x2, int y2) {
                    boxText.setText("box: [" + x1 + "," + y1 + "],[" + x2 + "," + y2 + "]");
                }
            });

        }catch (Exception e ){
            Toast.makeText(getActivity(), "no fase is found :(", Toast.LENGTH_SHORT).show();
        }


    }


}
