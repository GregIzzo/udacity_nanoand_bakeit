package com.example.android.udacity_nanoand_bakeit.utilities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class Utils {

    /**
     * This routine was posted on StackOverflow by Jonik:
     * https://stackoverflow.com/questions/18021148/display-a-loading-overlay-on-android-screen
     *
     * @param view         View to animate
     * @param toVisibility Visibility at the end of animation
     * @param toAlpha      Alpha at the end of animation
     * @param duration     Animation duration in ms
     */
    public static void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        boolean show = toVisibility == View.VISIBLE;
        if (show) {
            view.setAlpha(0);
        }
        view.setVisibility(View.VISIBLE);
        view.animate()
                .setDuration(duration)
                .alpha(show ? toAlpha : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(toVisibility);
                    }
                });
    }
    public static boolean isImageUrl(String url){
         String[] imageExtensions = new String[] {"jpg", "gif", "jpeg", "png"};
         for (String currExtension : imageExtensions){
             if (url.toLowerCase().endsWith(currExtension)){
                 return true;
             }
         }
         return false;
    }
}
