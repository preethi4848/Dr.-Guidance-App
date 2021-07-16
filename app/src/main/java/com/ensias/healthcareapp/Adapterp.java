package com.ensias.healthcareapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ensias.healthcareapp.Common.Common;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.List;
import java.util.Calendar;
import butterknife.Unbinder;

public class Adapterp extends PagerAdapter implements DatePickerDialog.OnDateSetListener {
    private List<com.ensias.healthcareapp.Modelp> modelp;
    private LayoutInflater layoutInflaterp;
    private Context contextp;
    static String doc;
    Unbinder unbinder;

    public Adapterp(List<com.ensias.healthcareapp.Modelp> modelp, Context contextp) {
        this.modelp = modelp;
        this.contextp = contextp;
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
        i.putExtra("key3","patient");
        wf.startActivity(i);
    }
    @Override
    public int getCount() {
        return modelp.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Common.CurreentDoctor = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        Common.CurrentUserType = "patient";
        layoutInflaterp=LayoutInflater.from(contextp);
        Common.CurrentUserid= FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        FirebaseFirestore.getInstance().collection("User").document(Common.CurrentUserid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Common.CurrentUserName = documentSnapshot.getString("name");
            }
        });

        layoutInflaterp=LayoutInflater.from(contextp);
        View view = layoutInflaterp.inflate(R.layout.itemp,container,false);
        ImageView imageView;
        TextView title;

        imageView=view.findViewById(R.id.imagep);
        title=view.findViewById(R.id.titlep);
        imageView.setImageResource(modelp.get(position).getImage());
        title.setText(modelp.get(position).getTitle());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    Intent intent = new Intent(contextp,SearchPatActivity.class);
                    contextp.startActivity(intent);
                }
                else if(position == 1){
                    Intent intent = new Intent(contextp, DossierMedical.class);
                    intent.putExtra("patient_email",FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
                    contextp.startActivity(intent);
                }
                else if(position==2){
                    Intent i1=new Intent(contextp,MyDoctorsAvtivity.class);
                    contextp.startActivity(i1);
                }

                else if(position==3){
                    Intent iy=new Intent(contextp,ProfilePatientActivity.class);
                    contextp.startActivity(iy);
                }
                else if(position==4){
                    Intent iy=new Intent(contextp,PatientAppointementsActivity.class);
                    contextp.startActivity(iy);
                }
                else if(position==5){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(contextp, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    contextp.startActivity(intent);
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


