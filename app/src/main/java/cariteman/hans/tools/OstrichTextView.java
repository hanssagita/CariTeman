package cariteman.hans.tools;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by HansSagita on 14/10/17.
 */

public class OstrichTextView extends android.support.v7.widget.AppCompatTextView{

    public OstrichTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public OstrichTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public OstrichTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("ostrich-regular.ttf", context);
        setTypeface(customFont);
    }

}
