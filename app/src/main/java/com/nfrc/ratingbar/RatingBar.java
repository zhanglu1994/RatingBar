package com.nfrc.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhangl on 2018/10/24.
 */

public class RatingBar extends View {


    private Bitmap mNormalBitmap, mFocusBitmap;
    private int mGradeNumber;

    private int padding = 10;

    private int mCurrentGrade = 0;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        mGradeNumber = array.getInteger(R.styleable.RatingBar_grade, 5);
        int starNormalId = array.getResourceId(R.styleable.RatingBar_starNormal, R.drawable.star_normal);
        int starFocusId = array.getResourceId(R.styleable.RatingBar_starFocus, R.drawable.star_selected);
        mNormalBitmap = BitmapFactory.decodeResource(getResources(), starNormalId);
        mFocusBitmap = BitmapFactory.decodeResource(getResources(), starFocusId);
        array.recycle();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = mNormalBitmap.getHeight() + getPaddingBottom() + getPaddingTop();
        int width = mNormalBitmap.getWidth() * mGradeNumber + padding * (mGradeNumber - 1);
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mGradeNumber; i++) {

            int x = (i * 1) * padding + i * mNormalBitmap.getWidth();

            if (mCurrentGrade > i){

                canvas.drawBitmap(mFocusBitmap, x, getPaddingTop(), null);
            }else {
                canvas.drawBitmap(mNormalBitmap, x, getPaddingTop(), null);
            }



        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                int currentGrade = (int) (moveX / mFocusBitmap.getWidth() + 1);
                if (currentGrade < 0) {
                    currentGrade = 0;
                }
                if (currentGrade > mGradeNumber) {
                    currentGrade = mGradeNumber;
                }
                mCurrentGrade = currentGrade;
                invalidate();
                break;
            
            default:
                break;
        }


        return true;
    }


}
