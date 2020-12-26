package com.namaskar.namaskar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.namaskar.namaskar.QuestionModel;
import com.namaskar.namaskar.Score6Activity;
import com.namaskar.namaskar.ScoreActivity;
import com.namaskar.namaskar.Scroe3Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private TextView question,noIndicator;
    private Button shareBtn,nextBtn;
    private LinearLayout optionsContainer;
    private List<QuestionModel>list;
    private  int count=0;
    private int position=0;
    private int score=0;
    private String category;
    private int setNo;
    private Dialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //////connect with id/////////

        question=findViewById(R.id.question);
        noIndicator=findViewById(R.id.no_indicator);
        shareBtn=findViewById(R.id.share_btn);
        nextBtn=findViewById(R.id.next_btn);
        optionsContainer=findViewById(R.id.options_container);

        loading=new Dialog(this);
        loading.setContentView(R.layout.loading_dailog);
        loading.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loading.setCancelable(false);
        ////////////////////////////////////////
       category=getIntent().getStringExtra("category");
       setNo=getIntent().getIntExtra("setNo",1);



        ///////////list//////////////////////

        //////////////////////////////////////////////////////


           list=new ArrayList<>();
              loading.show();
           myRef.child("SETS").child(category).child("questions").orderByChild("setNo").equalTo(setNo).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                       list.add(snapshot.getValue(QuestionModel.class));
                       Collections.shuffle(list);

                   }
                   if(list.size()>0){

                       for(int i=0;i<4;i++) {
                           optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                   checkAnswer((Button) view);

                               }
                           });
                       }
                       playAnim(question,0,list.get(position).getQuestion());
                       nextBtn.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               nextBtn.setEnabled(false);
                               nextBtn.setAlpha(0);
                               enableOption(true);
                               position++;
                               if(position ==10){

                                   if(score >=5) {
                                       Intent scoreIntent = new Intent(QuestionActivity.this, ScoreActivity.class);
                                       scoreIntent.putExtra("score", score);
                                       scoreIntent.putExtra("total",10);
                                       startActivity(scoreIntent);
                                       finish();
                                       return;
                                   }else if( score==0||score <=3){

                                       Intent scoreI = new Intent(QuestionActivity.this, Scroe3Activity.class);
                                           startActivity(scoreI);
                                           finish();
                                   }
                                   else if(score==4){
                                       Intent scoreIw = new Intent(QuestionActivity.this, Score6Activity.class);
                                       startActivity(scoreIw);
                                       finish();
                                   }

                               }
                               count=0;
                               playAnim(question,0,list.get(position).getQuestion());
                           }
                       });
                       shareBtn.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               String body=list.get(position).getQuestion() +"\n"+
                                       list.get(position).getOptionA()+"\n"+
                                       list.get(position).getOptionB()+"\n"+
                                       list.get(position).getOptionC()+"\n"+
                                       list.get(position).getOptionD();
                               Intent share=new Intent(Intent.ACTION_SEND);
                               share.setType("plan/text");

                               share.putExtra(Intent.EXTRA_SUBJECT,"Namaskar Quiz ");
                               share.putExtra(Intent.EXTRA_TEXT,body);
                               startActivity(Intent.createChooser(share,"Share Via"));
                           }
                       });

                   }else {
                       finish();
                       Toast.makeText(QuestionActivity.this, "No question", Toast.LENGTH_SHORT).show();

                   }
                   loading.dismiss();
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {
                   Toast.makeText(QuestionActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
               loading.dismiss();
               finish();
               }
           });

        ///////////////////////click listener//////////////////////////////////



    }
    private void playAnim(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(value==0&&count<4){
                    String option="";
                    if(count==0){
                        option=list.get(position).getOptionA();
                    }
                    else if(count==1)
                    {
                        option=list.get(position).getOptionB();
                    }
                    else if(count==2)
                    {
                        option=list.get(position).getOptionC();
                    }
                    else if(count==3)
                    {
                        option=list.get(position).getOptionD();
                    }

                    playAnim(optionsContainer.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if(value==0){
                    try {
                        ((TextView)view).setText(data);
                        noIndicator.setText(position+1+"/"+10);
                    }catch (ClassCastException ex){
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    private void checkAnswer(Button selectedOption){
        enableOption(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if(selectedOption.getText().toString().equals(list.get(position).getCorrectAns())){
            //correct
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));

        }else {
            //incorrect
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));

            Button correctoption = (Button) optionsContainer.findViewWithTag(list.get(position).getCorrectAns());
            correctoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));

        }
    }
    private void enableOption(boolean enable){
        for(int i=0;i<4;i++){
            optionsContainer.getChildAt(i).setEnabled(enable);
            if(enable){
                optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));

            }

        }

    }
}
