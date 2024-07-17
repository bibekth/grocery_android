package org.meropasal.merogrocery.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class MakeAppNameBolder {
    public MakeAppNameBolder() {
    }
    public static void makeBolder(Context context, TextView textView){
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
    }

}
