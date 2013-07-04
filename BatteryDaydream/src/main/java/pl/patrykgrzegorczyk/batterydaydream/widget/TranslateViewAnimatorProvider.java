package pl.patrykgrzegorczyk.batterydaydream.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Provides translate animation
 */
public class TranslateViewAnimatorProvider implements ChildAnimatingLayout.ViewAnimatorProvider {

    private static final int ANIMATION_DURATION = 1000; // 1 second

    @Override
    public Animator provideAnimator(View view, int newX, int newY) {
        //Animate child view move
        AnimatorSet animatorSet = new AnimatorSet();
        Animator moveXAnimator = ObjectAnimator.ofFloat(view, "x", view.getX(), newX);
        Animator moveYAnimator = ObjectAnimator.ofFloat(view, "y", view.getY(), newY);

        animatorSet.play(moveXAnimator).with(moveYAnimator);
        animatorSet.setDuration(ANIMATION_DURATION);

        return animatorSet;
    }
}
