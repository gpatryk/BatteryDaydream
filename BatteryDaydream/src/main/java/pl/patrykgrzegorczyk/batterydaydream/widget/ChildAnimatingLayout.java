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
    private static final int DEFAULT_ANIMATION_DELAY = 30 * 1000; //30 seconds
    private static final int DEFAULT_ANIMATION_DURATION = 1000; // 1 second

    private View mAnimatedView;
    private Handler mHandler = new Handler();
    private AnimateRunnable mAnimateRunnable = new AnimateRunnable(mHandler);
    private int mAnimationDelay = DEFAULT_ANIMATION_DELAY;
    private int mAnimationDuration = DEFAULT_ANIMATION_DURATION;


    public ChildAnimatingLayout(Context context) {
        //XXX In FrameLayout super(context) is called here
        this(context, null);
    }

    public ChildAnimatingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChildAnimatingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public int getAnimationDelay() {
        return mAnimationDelay;
    }

    public void setAnimationDelay(int animationDelay) {
        mAnimationDelay = animationDelay;
    }

    public int getAnimationDuration() {
        return mAnimationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        mAnimationDuration = animationDuration;
    }

    @Override
    public void addView(View child) {
        ensureOneChildHosting();

        super.addView(child);

        mAnimatedView = child;
    }

    @Override
    public void addView(View child, int index) {
        ensureOneChildHosting();

        super.addView(child, index);

        mAnimatedView = child;
    }

    @Override
    public void addView(View child, int width, int height) {
        ensureOneChildHosting();

        super.addView(child, width, height);

        mAnimatedView = child;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        ensureOneChildHosting();

        super.addView(child, index, params);

        mAnimatedView = child;
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        ensureOneChildHosting();

        super.addView(child, params);

        mAnimatedView = child;
    }

    private void ensureOneChildHosting() {
        if(mAnimatedView != null) {
            throw new IllegalStateException("Layout can host only one direct child");
        }
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

        mHandler.postDelayed(mAnimateRunnable, getAnimationDelay());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mHandler.removeCallbacks(mAnimateRunnable);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //If size has changed it's possible that child view is out of sight,
        //so better move the child to new position in layout bounds

        mHandler.removeCallbacks(mAnimateRunnable);
        mHandler.post(mAnimateRunnable);
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
            animatorSet.setDuration(getAnimationDuration());
            animatorSet.start();

            //Schedule next animation

            mHandler.removeCallbacks(this);
            mHandler.postDelayed(this, getAnimationDelay());
        }
    }
}
