package com.example.medapp.activities;

import android.content.Context;

public class ResourceUtils {

    public static int getResId(Context context, String resName) {

        return context.getResources().getIdentifier(
                resName,
                "drawable",
                context.getPackageName()
        );
    }
}