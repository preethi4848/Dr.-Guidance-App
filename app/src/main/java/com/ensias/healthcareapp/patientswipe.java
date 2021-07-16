package com.ensias.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
public class patientswipe extends AppCompatActivity {
    ViewPager viewPagerp;
    Adapterp adapterp;
    List<Modelp> modelp;
    Integer[] colors=null;
    ArgbEvaluator argbEvaluatorp= new ArgbEvaluator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_patientswipe);
        modelp=new ArrayList<>();
        modelp.add(new Modelp(R.drawable.searchh, "Search"));
        modelp.add(new Modelp(R.drawable.folder, "Medical Folder"));
        modelp.add(new Modelp(R.drawable.doct, "My Doctors"));
        modelp.add(new Modelp(R.drawable.profile, "Profile"));
        modelp.add(new Modelp(R.drawable.appointment, "Appointments"));
        modelp.add(new Modelp(R.drawable.logout,"Sign out"));
        adapterp= new Adapterp(modelp,this);
        viewPagerp=findViewById(R.id.viewPagerp);
        viewPagerp.setAdapter(adapterp);
        viewPagerp.setPadding(130,0,130,0);
        Integer[] colors_temp={
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color5)
        };
        colors=colors_temp;
        viewPagerp.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                if (position <(adapterp.getCount()-1)&&position<(colors.length-1)){
                    viewPagerp.setBackgroundColor(
                            (Integer)argbEvaluatorp.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position+1]
                            )
                    );
                }else
                {
                    viewPagerp.setBackgroundColor(colors[colors.length-1]);
                }
            }
            @Override
            public void onPageSelected(int position){

            }
            @Override
            public void onPageScrollStateChanged(int state){

            }
        });
    }
}