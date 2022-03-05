package myride.lesberweb.com.myride;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import com.google.android.gms.ads.AdRequest;


public class Tutorial extends AppCompatActivity {
    private ViewFlipper mViewFlipper;
    private Context mContext;
    private float initialX;
    ImageButton btnClose;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuto);


        mContext = this;
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(2000);
        mViewFlipper.startFlipping();
/*
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewFlipper.setAutoStart(true);
                mViewFlipper.setFlipInterval(2000);
                mViewFlipper.startFlipping();

            }
        });*/
        btnClose =(ImageButton)findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent e = new Intent(Tutorial.this, Auto.class);
                startActivity(e);
                finish();

            }
        });

/*

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewFlipper.stopFlipping();


            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float finalX = touchevent.getX();
                if (initialX > finalX) {
                    if (mViewFlipper.getDisplayedChild() == 1)
                        break;

                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.in_from_left));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.out_from_left));

                    mViewFlipper.showNext();
                } else {
                    if (mViewFlipper.getDisplayedChild() == 0)
                        break;

                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.in_from_right));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.out_from_right));

                    mViewFlipper.showPrevious();
                }
                break;
        }
        return false;
        */
    }

}
