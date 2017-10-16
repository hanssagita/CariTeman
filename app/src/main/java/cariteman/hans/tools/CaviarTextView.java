package cariteman.hans.tools;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by hans.sagita on 16/10/2017.
 */

public class CaviarTextView extends android.support.v7.widget.AppCompatTextView {

    public CaviarTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CaviarTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CaviarTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("CaviarDreams.ttf", context);
        setTypeface(customFont);
    }

}
