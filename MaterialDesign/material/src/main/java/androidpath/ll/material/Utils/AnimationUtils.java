package androidpath.ll.material.Utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Le on 2015/6/30.
 */
public class AnimationUtils {

    public static void animate(RecyclerView.ViewHolder holder, boolean isScrollingDown) {

        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", isScrollingDown ? 100 : -100, 0);
        ObjectAnimator animatorVibrateX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -25, 25, -20, 20, -15, 15, -10, 10, -5, 5, 0);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.playTogether(animatorTranslateY, animatorVibrateX);
        mAnimatorSet.start();
    }
}
