package jetpack.randa.com.ayahandamal;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class OthmaniTextView extends android.support.v7.widget.AppCompatTextView {

    public OthmaniTextView(Context context) {
        super(context);
        setTypeFace();
    }

    public OthmaniTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeFace();
    }

    public OthmaniTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeFace();
    }
    private void setTypeFace(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/ar-Othmani.ttf");
        this.setTypeface(typeface);
    }
}
