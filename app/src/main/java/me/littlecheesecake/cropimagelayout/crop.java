package me.littlecheesecake.cropimagelayout;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.littlecheesecake.croplayout.EditPhotoView;
import me.littlecheesecake.croplayout.EditableImage;
import me.littlecheesecake.croplayout.handler.OnBoxChangedListener;
import me.littlecheesecake.croplayout.model.ScalableBox;

import static me.littlecheesecake.cropimagelayout.MainActivity.imagetocrop2;


/**
 * A simple {@link Fragment} subclass.
 */
public class crop extends Fragment {


    public crop( ) {
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

            final EditableImage image = new EditableImage(imagetocrop2);
        ScalableBox box1 = new ScalableBox(25,10,640,80);
        ScalableBox box2 = new ScalableBox(2,18,680,80);
        ScalableBox box3 =  new ScalableBox(250,80,400,80);
        List<ScalableBox> boxes = new ArrayList<>();
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        image.setBoxes(boxes);
        imageView.initView(getActivity(), image);

        boxText.setText("box: [" + 25 + "," + 180 +"],[" + 640 + "," + 880 + "]");
        imageView.setOnBoxChangedListener(new OnBoxChangedListener() {
            @Override
            public void onChanged(int x1, int y1, int x2, int y2) {
                boxText.setText("box: [" + x1 + "," + y1 +"],[" + x2 + "," + y2 + "]");
            }
        });

    }



    }
