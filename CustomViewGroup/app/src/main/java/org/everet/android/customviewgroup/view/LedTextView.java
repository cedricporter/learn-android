package org.everet.android.customviewgroup.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by cedricporter on 14/12/14.
 */
public class LedTextView extends TextView {
    private static final String FONT_DIGITAL_7 = "fonts/digital-7.ttf";

    public LedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        AssetManager assets = context.getAssets();
        final Typeface font = Typeface.createFromAsset(assets, FONT_DIGITAL_7);
        setTypeface(font);
    }
}
