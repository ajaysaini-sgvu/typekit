package com.nagarro.typekit;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.nagarro.typekit.utils.TypekitLoader;
import com.nagarro.typekit.utils.Typekitable;

public class TextView extends AppCompatTextView implements Typekitable {

    private TypekitLoader typekitLoader;

    public TextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typekitLoader = TypekitLoader.get(this, context, attrs);
    }

}
