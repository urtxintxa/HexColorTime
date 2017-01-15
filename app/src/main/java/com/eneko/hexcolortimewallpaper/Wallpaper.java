package com.eneko.hexcolortimewallpaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Wallpaper extends WallpaperService{

    Paint paint;

    @Override
    public Engine onCreateEngine() {
        paint = new Paint();

        return new WallpaperEngine();
    }

    private class WallpaperEngine extends Engine {

        private final Handler handler = new Handler();
        private final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };
        private boolean visible = true;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {

            super.onCreate(surfaceHolder);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;

            if (visible)
            {
                handler.post(runnable);
            }
            else
            {
                handler.removeCallbacks(runnable);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(runnable);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            draw();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            visible = false;
            handler.removeCallbacks(runnable);
        }

        private void draw() {
            SurfaceHolder surfaceHolder = getSurfaceHolder();
            Canvas canvas = null;
            Calendar ca = Calendar.getInstance();
            try {
                canvas = surfaceHolder.lockCanvas();

                if (canvas != null) {

                    paint.setTypeface(getTypeface());
                    paint.setAntiAlias(true);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(Integer.parseInt("200"));
                    paint.setColor(Color.WHITE);

                    int xPos = (canvas.getWidth() / 2);
                    int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;

                    canvas.drawColor(Color.parseColor(getTimeColor(ca)));

                    canvas.drawText(getTimeText(ca), xPos, yPos, paint);
                }
            }
            finally {
                if(canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            handler.removeCallbacks(runnable);

            if(visible) {
                handler.postDelayed(runnable, 100);
            }
        }

        private String getTimeColor(Calendar ca) {
            return "#" +
                    String.format("%02d", ca.get(Calendar.HOUR_OF_DAY)) +
                    String.format("%02d", ca.get(Calendar.MINUTE)) +
                    String.format("%02d", ca.get(Calendar.SECOND));
        }

        private String getTimeText(Calendar ca) {
            String textType = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("textType", "");
            if(textType.equals("1")) {
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                return df.format(Calendar.getInstance().getTime());
            }
            else {
                return getTimeColor(ca);
            }
        }

        private Typeface getTypeface() {
            String textTypeface = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("textTypeface", "");

            switch (Integer.parseInt(textTypeface)) {
                default:
                    return Typeface.DEFAULT;
                case 2:
                    return Typeface.DEFAULT_BOLD;
                case 3:
                    return Typeface.MONOSPACE;
                case 4:
                    return Typeface.SANS_SERIF;
                case 5:
                    return Typeface.SERIF;
            }

        }
    }
}
