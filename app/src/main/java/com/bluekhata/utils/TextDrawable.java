package com.bluekhata.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;

import com.bluekhata.R;

import java.util.Random;

public class TextDrawable extends Drawable {

    private final String text;
    private final Paint paint, bgPaint;

    public TextDrawable(String text, int textColor, int bgColor) {

        this.text = text;

        this.paint = new Paint();
        this.bgPaint = new Paint();

        paint.setColor(textColor);
        paint.setTextSize(22f);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        paint.setShadowLayer(6f, 0, 0, bgColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public TextDrawable(Context context, String text){
        int[] androidColors = context.getResources().getIntArray(R.array.rainbow);
        int bgColor = androidColors[new Random().nextInt(androidColors.length)];

        int textColor = darker(bgColor, 0.8f);

        this.text = String.valueOf(text.charAt(0));
        Log.e("txt>>",text);
        float textSize = context.getResources().getDimensionPixelSize(R.dimen._48ssp);

        this.paint = new Paint();
        this.bgPaint = new Paint();

        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);

        bgPaint.setColor(bgColor);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPaint(bgPaint);
        Rect bounds = getBounds();
        canvas.drawText(text, 0, text.length(), bounds.centerX(), bounds.centerY() - ((paint.descent() + paint.ascent()) / 2), paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    public static int darker (int color, float factor) {
        int a = Color.alpha( color );
        int r = Color.red( color );
        int g = Color.green( color );
        int b = Color.blue( color );

        return Color.argb( a,
                Math.max( (int)(r * factor), 0 ),
                Math.max( (int)(g * factor), 0 ),
                Math.max( (int)(b * factor), 0 ) );
    }
}
