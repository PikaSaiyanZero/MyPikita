package com.pikita.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Definitions
    ImageButton button;
    MediaPlayer mp;
    AnimationSet animationSet;

    // Click counting
    int clickCount = 0;
    int totalClicks = 0;

    // Pikita bouncing
    public void startBounceAnimation(View view, float distance, long duration) {
        totalClicks++; // Increment total clicks
        // The actual bounce
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0, -distance, 0);
        animator.setDuration(duration);
        animator.start();
        // Make him speak at animation start
        button.setImageResource(R.drawable.base__speak);
        // Stop speaking at animation end
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                clickCount++; // Increment click count on animation end
                if (clickCount >= totalClicks) { // Check if it's the last animation
                    button.setImageResource(R.drawable.base);
                    clickCount = 0; // Reset click count
                    totalClicks = 0; // Reset total clicks
                }
            }
        });
    }

    private void playSound() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.squeak);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release(); // Release the MediaPlayer resource after completion
                //button.setImageResource(R.drawable.base);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set view for Pikita
        button = (ImageButton)findViewById(R.id.imageButton);
        // Make Pikita clickable
        button.setOnTouchListener(imgButtonHandler);
        // Initialize media player
        mp = MediaPlayer.create(this, R.raw.squeak);
    }

    View.OnTouchListener imgButtonHandler = new View.OnTouchListener() {

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                playSound();

                // Apply animation to the view
                startBounceAnimation(v, 150, 600); // This will make the view bounce 200 pixels up and down in 1 second.

            }


            return false;
        }
    };
}

