package pl.patrykgrzegorczyk.batterydaydream.widget;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Layout changing child position with animation. Can hold only one child
 */
public class ChildAnimatingLayout extends LinearLayout {

    private static final String TAG = "ChildAnimatingLayout";
    private static final int ANIMATION_DELAY = 30 * 1000; //30 seconds
    private static final int ANIMATION_DURATION = 1000; // 1 second

    private View mAnimatedView;
    private Handler mHandler = new Handler();
    private AnimateRunnable mAnimateRunnable = new AnimateRunnable(mHandler);


    public ChildAnimatingLayout(Context context) {
        //In FrameLayout super(context) is called here
        this(context, null);
    }

    public ChildAnimatingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChildAnimatingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if(mAnimatedView != null) {
            throw new IllegalStateException("Can have only one child");
        }

        super.addView(child, params);

        mAnimatedView = child;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if(mAnimatedView == null) {
            return;
        }

        //Center child view
        mAnimatedView.setX((getWidth() - mAnimatedView.getWidth()) / 2);
        mAnimatedView.setY((getHeight() - mAnimatedView.getHeight()) / 2);

        mHandler.postDelayed(mAnimateRunnable, ANIMATION_DELAY);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mHandler.removeCallbacks(mAnimateRunnable);
    }

    public class AnimateRunnable implements Runnable {

        private final Handler mHandler;

        public AnimateRunnable(Handler handler) {
            mHandler = handler;
        }

        @Override
        public void run() {
            //Calculate new position
            final int newX = (int) (Math.random() * (getWidth() - mAnimatedView.getWidth()));
            final int newY = (int) (Math.random() * (getHeight() - mAnimatedView.getHeight()));

            //Animate child view move
            AnimatorSet animatorSet = new AnimatorSet();
            Animator moveXAnimator = ObjectAnimator.ofFloat(mAnimatedView, "x", mAnimatedView.getX(), newX);
            Animator moveYAnimator = ObjectAnimator.ofFloat(mAnimatedView, "y", mAnimatedView.getY(), newY);

            animatorSet.play(moveXAnimator).with(moveYAnimator);
            animatorSet.setDuration(ANIMATION_DURATION);
            animatorSet.start();

            //Schedule next animation

            mHandler.removeCallbacks(this);
            mHandler.postDelayed(this, ANIMATION_DELAY);
        }
    }
}
