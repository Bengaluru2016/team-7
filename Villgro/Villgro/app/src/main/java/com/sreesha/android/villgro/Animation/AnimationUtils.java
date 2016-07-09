package com.sreesha.android.villgro.Animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Sreesha on 13-07-2015.
 */
public class AnimationUtils {

    private static int counter = 0;

    public static final String AVATAR_FLIP_SLIDE_IN_FADE_IN = "animateAvatarFadeInFlip";
    public static final String SLIDE_LEFT_TO_RIGHT = "slideLeftToRight";
    public static final String SLIDE_BOTTOM_TO_TOP = "slideBottomToTop";
    public static final String ALPHA_FADE_SLIDE = "alphaFadeSlideIn";
    public static final String FLIP = "flip";
    public static final String REVERSE_SCATTER = "reverseScatter";


    public static void animateAlphaFadeAndScatter(RecyclerView.ViewHolder holder, boolean goesDown) {
        counter = ++counter % 4;
        int holderHeight = holder.itemView.getHeight();
        int holderWidth = holder.itemView.getWidth();
        View holderItemView = holder.itemView;
        holderItemView.setPivotY(goesDown ? 0 : holderHeight);
        holderItemView.setPivotX(holderWidth / 2);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY
                = ObjectAnimator.ofFloat(holderItemView, "translationY", goesDown == true ? 300 : -300, 0);
        ObjectAnimator animatorTranslateX
                = ObjectAnimator.ofFloat(holderItemView, "translationX", counter == 1 || counter == 3 ? holderWidth : -holderWidth, 0);
        ObjectAnimator animatorScaleX
                = ObjectAnimator.ofFloat(holderItemView, "scaleX", counter == 1 || counter == 2 ? 0 : 2, 1f);
        ObjectAnimator animatorScaleY
                = ObjectAnimator.ofFloat(holderItemView, "scaleY", counter == 1 || counter == 2 ? 0 : 2, 1f);
        ObjectAnimator animatorAlpha
                = ObjectAnimator.ofFloat(holderItemView, "alpha", 0f, 1f);
        animatorAlpha.setInterpolator(new AccelerateInterpolator(5.5f));
        animatorSet.playTogether(animatorAlpha, animatorScaleX, animatorScaleY, animatorTranslateX, animatorTranslateY);
        animatorSet.setDuration(2000).setInterpolator(new DecelerateInterpolator(1.1f));
        animatorSet.start();
    }
}