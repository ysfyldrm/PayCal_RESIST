package com.grapesoftware.paycal_resist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter (Context context){
        this.context=context;

    }
    //Arrays
    public int[] slider_images={
            R.drawable.imaj1,
            R.drawable.imaj2,
            R.drawable.imaj3
    };

    public String[] slider_headings={
            "Fast",
            "Useful",
            "Easy"
    };

    public String[] slider_descs={
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec iaculis ullamcorper tristique. Maecenas eget hendrerit augue, sit amet congue sapien.",
            "Vivamus id placerat augue. Cras luctus at justo non lacinia. Donec facilisis sit amet felis et maximus. Quisque in diam nisl. Sed aliquet venenatis viverra.",
            "Fusce ex nibh, mattis eu facilisis vel, consectetur ac neque. Curabitur sit amet facilisis dolor, at elementum lectus. Aenean at congue erat. Suspendisse eget velit id"
    };
    @Override
    public int getCount() {
        return slider_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout) object ;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView=view.findViewById(R.id.slide_image);
        TextView slideHeading=view.findViewById(R.id.slide_heading);
        TextView slideDescription=view.findViewById(R.id.slide_desc);


        slideImageView.setImageResource(slider_images[position]);
        slideHeading.setText(slider_headings[position]);
        slideDescription.setText(slider_descs[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);

    }
}
