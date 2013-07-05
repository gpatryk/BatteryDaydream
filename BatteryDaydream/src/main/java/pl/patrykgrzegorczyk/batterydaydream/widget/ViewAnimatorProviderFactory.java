package pl.patrykgrzegorczyk.batterydaydream.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import pl.patrykgrzegorczyk.batterydaydream.R;
import pl.patrykgrzegorczyk.batterydaydream.fragment.DefaultPreferenceFragment;

/**
 * Created view animator
 */
public class ViewAnimatorProviderFactory {

    private static final String TAG = "ViewAnimatorProviderFactory";

    public static ChildAnimatingLayout.ViewAnimatorProvider getViewAnimatorProvider(Context context) {

        String animationTypeMove = context.getString(R.string.animation_type_move);
        String animationTypeFadeOutFadeIn = context.getString(R.string.animation_type_fadeout_fadein);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String animationType = preferences.getString(DefaultPreferenceFragment.KEY_ANIMATION_TYPE, animationTypeMove);

        if(animationType.equals(animationTypeMove)) {
            return new TranslateViewAnimatorProvider();
        }

        if(animationType.equals(animationTypeFadeOutFadeIn)) {
            return new HideShowAnimatorProvider();
        }

        Log.w(TAG, "Unknown animation type. Default animation will be used");

        return new TranslateViewAnimatorProvider();
    }
}
