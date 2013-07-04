package pl.patrykgrzegorczyk.batterydaydream.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import org.jetbrains.annotations.NotNull;

/**
 * Provides animation with fade out and fade in in new position
 */
public class HideShowAnimatorProvider implements ChildAnimatingLayout.ViewAnimatorProvider {
    @NotNull
    @Override
    public Animator provideAnimator(@NotNull View view, int newX, int newY) {
        AnimatorSet animatorSet = new AnimatorSet();
        //Fade out view
        Animator fadeOutAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        fadeOutAnimator.setDuration(500);
        animatorSet.play(fadeOutAnimator);

        //Move it to desired place with 0 time
        Animator moveXAnimator = ObjectAnimator.ofFloat(view, "x", view.getX(), newX);
        moveXAnimator.setDuration(0);
        Animator moveYAnimator = ObjectAnimator.ofFloat(view, "y", view.getY(), newY);
        moveYAnimator.setDuration(0);
        animatorSet.play(moveXAnimator).with(moveYAnimator).after(fadeOutAnimator);

        //Fade in view
        Animator fadeInAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        fadeInAnimator.setDuration(500);
        animatorSet.play(fadeInAnimator).after(moveXAnimator);

        animatorSet.setDuration(1000);

        return animatorSet;
    }
}
