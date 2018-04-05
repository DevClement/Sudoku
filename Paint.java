package fr.clement.game.sudoku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class Paint extends View {



    public Paint(Context context) {
        super(context);
    }

    @SuppressLint("NewApi")
    public void Draw(Canvas paint){

    paint.drawColor(Color.GREEN);
    paint.clipOutRect(10,10,10,10);

    }
}
