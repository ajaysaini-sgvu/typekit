package com.nagarro.typekit.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.nagarro.typekit.R;
import java.lang.ref.WeakReference;

public class TypekitLoader {

    private final WeakReference<TextView> textView;
    private static LruCache<String, Typeface> typefaceCache = new LruCache<String, Typeface>(12);

    private TypekitLoader(TextView textView, Context mContext, AttributeSet attrs) {
        this.textView = new WeakReference<TextView>(textView);
        takeTypeface(mContext, attrs);
    }

    public static TypekitLoader get(TextView view, Context context, AttributeSet attrs){
        return new TypekitLoader(view, context, attrs);
    }

    private void takeTypeface(Context mContext, AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.Typekitable, 0, 0);
        try {
            String typefaceName = typedArray.getString(R.styleable.Typekitable_fontFamily);
            setTypeface(typefaceName);
        } finally {
            typedArray.recycle();
        }
    }

    private void setTypeface(String typefaceName) {
        TextView textView = this.textView.get();
        if(typefaceName != null && !TextUtils.isEmpty(typefaceName)) {
            Typeface typeface = TypekitLoader.getTypeFace(textView.getContext(), typefaceName);
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
    }

    private static Typeface getTypeFace(Context mContext, String typeFaceName) {
        try {
            Typeface typeface = typefaceCache.get(typeFaceName);
            if(typeface == null) {
                typeface = Typeface.createFromAsset(mContext.getAssets(), String.format("fonts/%s", typeFaceName + ".ttf"));

                // keeping typeface in cache
                typefaceCache.put(typeFaceName, typeface);
            }
            return typeface;
        } catch (Exception e) {
            return null;
        }
    }
}