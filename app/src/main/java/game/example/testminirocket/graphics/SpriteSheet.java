package game.example.testminirocket.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

import com.minirocket.game.R;


// Responsible for returning correct sprite
public class SpriteSheet {
    private Bitmap bitmap;
    private Context context;
    private int counter = 0;
    BitmapFactory.Options bitmapOptions;

    public SpriteSheet(Context context){
        this.context = context;
        bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;

        bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.ea98b1_planet, bitmapOptions);

    }

    public Sprite[] getPlanetSpriteArray(int index, int[] spriteList){
        Log.d("index", String.valueOf(index));
        for (int i = 0; i < spriteList.length; i++) {
            if (index == i){
                Log.d("found", String.valueOf(i));
                bitmap = BitmapFactory.decodeResource(this.context.getResources(), spriteList[i], bitmapOptions);
                break;
            }
        }
        Sprite[] spriteArray = new Sprite[50];
        for (int i = 0; i < spriteArray.length; i++) {
            spriteArray[i] = new Sprite(this, new Rect(i*100, 0, (i+1)*100, 100), 0);
        }
        return spriteArray;
    }
    public Sprite[] getAsteroidSpriteArray(){
        Sprite[] spriteArray = new Sprite[50];
        for (int i = 0; i < spriteArray.length; i++) {
            spriteArray[i] = new Sprite(this, new Rect(i*100, 0, (i+1)*100, 100), 0);
        }
        bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroid_sprite, bitmapOptions);
        return spriteArray;
    }
    public Sprite[] getRocketSpriteArray(){
        Sprite[] spriteArray = new Sprite[8];
        for (int i = 0; i < spriteArray.length; i++) {
            spriteArray[i] = new Sprite(this, new Rect(i*96, 0, (i+1)*96, 96), 90);
        }
        bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spaceship_sprite, bitmapOptions);

        return spriteArray;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}
