package com.ensias.healthcareapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ensias.healthcareapp.Common.Common;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class Adapter extends PagerAdapter implements DatePickerDialog.OnDateSetListener{
    private List<com.ensias.healthcareapp.Model> models;
    private LayoutInflater layoutInflater;
    private Context context;
    static String doc;
    Unbinder unbinder;

    public  Adapter(List<com.ensias.healthcareapp.Model> models, Context context) {
        this.models = models;
        this.context = context;
    }
    public void showDatePickerDialog(Context wf){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                wf,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "month_day_year: " + month + "_" + dayOfMonth + "_" + year;
        openPage(view.getContext(),doc,date);
    }

    private void openPage(Context wf, String d,String day){
        Intent i = new Intent(wf, AppointementActivity.class);
        i.putExtra("key1",d+"");
        i.putExtra("key2",day);
        i.putExtra("key3","doctor");
        wf.startActivity(i);
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        Common.CurreentDoctor = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        Common.CurrentUserType = "doctor";
        layoutInflater=LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item,container,false);
        ImageView imageView;
        TextView title;

        imageView=view.findViewById(R.id.image);
        title=view.findViewById(R.id.title);
        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(position == 0){
                    Intent intent = new Intent(context,MyPatientsActivity.class);
                    context.startActivity(intent);
                }
                else if(position == 1){
                    Intent i=new Intent(context,ConfirmedAppointmensActivity.class);
                    context.startActivity(i);
                }
                if(position==2){
                    Intent i1=new Intent(context,ProfileDoctorActivity.class);
                    context.startActivity(i1);
                }

                else if(position==3){
                    Intent iy=new Intent(context,DoctorAppointementActivity.class);
                    context.startActivity(iy);
                }
                else if(position==4){
                    Intent iy=new Intent(context,MyCalendarDoctorActivity.class);
                    context.startActivity(iy);
                }
                else if(position==5)
                {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }



            }
        });
        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}


