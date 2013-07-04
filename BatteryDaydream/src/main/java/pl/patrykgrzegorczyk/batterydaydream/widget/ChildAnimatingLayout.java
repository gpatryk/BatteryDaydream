package pl.patrykgrzegorczyk.batterydaydream.widget;


import android.animation.Animator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Layout changing child position with animation. Can hold only one direct child
 */
public class ChildAnimatingLayout extends LinearLayout {

    private static final String TAG = "ChildAnimatingLayout";
    private static final int DEFAULT_ANIMATION_DELAY = 30 * 1000; //30 seconds

    private View mAnimatedView;
    private final Handler mHandler = new Handler();
    private final AnimateRunnable mAnimateRunnable = new AnimateRunnable(mHandler);
    private int mAnimationDelay = DEFAULT_ANIMATION_DELAY;
    private ViewAnimatorProvider mViewAnimationProvider;
    private boolean mAnimateOnSizeChanged;


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

    @Nullable
    public ViewAnimatorProvider getViewAnimationProvider() {
        return mViewAnimationProvider;
    }

    public void setViewAnimationProvider(@Nullable ViewAnimatorProvider viewAnimationProvider) {
        mViewAnimationProvider = viewAnimationProvider;
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

        if(Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onAttachedToWindow()");
        }

        if(mAnimatedView == null) {
            return;
        }

        mHandler.postDelayed(mAnimateRunnable, getAnimationDelay());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if(Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onDetachedFromWindow()");
        }

        mHandler.removeCallbacks(mAnimateRunnable);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onConfigurationChanged(" + newConfig + ")");
        }

        //This prevents animation on view first show.
        //onConfiguration don't occurs on start. If it occurred we should
        //animate child layout when size changed - it can be out of layout bounds.
        mAnimateOnSizeChanged = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if(Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onSizeChanged()");
        }

        if(!mAnimateOnSizeChanged) {
            //Shouldn't animate
            return;
        }

        mAnimateOnSizeChanged = false;

        //If size has changed it's possible that child view is out of sight,
        //so better move the child to new position in layout bounds
        mHandler.removeCallbacks(mAnimateRunnable);
        mHandler.post(mAnimateRunnable);
    }

    private class AnimateRunnable implements Runnable {

        private final Handler mHandler;

        public AnimateRunnable(Handler handler) {
            mHandler = handler;
        }

        @Override
        public void run() {
            if(Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "run()");
            }

            if(mAnimatedView == null) {
                return;
            }

            //Calculate new position
            final int newX = (int) (Math.random() * (getWidth() - mAnimatedView.getWidth()));
            final int newY = (int) (Math.random() * (getHeight() - mAnimatedView.getHeight()));

            if(mViewAnimationProvider == null) {
                return;
            }

            Animator animator = mViewAnimationProvider.provideAnimator(mAnimatedView, newX, newY);
            animator.start();

            //Schedule next animation
            mHandler.removeCallbacks(this);
            mHandler.postDelayed(this, getAnimationDelay());
        }
    }

    /**
     * Provides animation which will be played on child view of {@link ChildAnimatingLayout}
     */
    public interface ViewAnimatorProvider {
        /**
         * Returns animator which will be animating view move from current position to (newX, newY)
         * @param view view to animate
         * @param newX desired X position of the view
         * @param newY desired Y position of the view
         * @return
         */
        @NotNull Animator provideAnimator(@NotNull View view, int newX, int newY);
    }
}
