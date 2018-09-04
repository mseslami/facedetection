package me.littlecheesecake.cropimagelayout;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> {

    private final  Activity context;

    private final String[] name;
    private final Drawable[] img ;

    public CustomList(Activity context, String[] name ,  Drawable[] img) {
        super(context, R.layout.imagelistview, name);

        this.context = context;
        this.name = name ;
        this.img = img ;


    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater infalter = context.getLayoutInflater();
        View v = infalter.inflate(R.layout.imagelistview	, null , true);

        TextView txtName = (TextView) v.findViewById(R.id.name);
        ImageView image = (ImageView) v.findViewById(R.id.imageView1);

        txtName.setText(name[position]+"");
//        image.setImageResource(img[position]);
        image.setImageDrawable(img[position]);

        return v ;
    }

}