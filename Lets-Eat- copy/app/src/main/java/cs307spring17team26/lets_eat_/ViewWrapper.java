package com.example.android.restaurantactivity;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by hareesh on 4/16/17.
 */

public class ViewWrapper {
    View base;
    RatingBar rate=null;
    TextView label=null;

    ViewWrapper(View base) {
        this.base=base;
    }

    RatingBar getRatingBar() {
        if (rate==null) {
            rate=(RatingBar)base.findViewById(R.id.rate);
        }
        return(rate);
    }

    TextView getLabel() {
        if (label==null) {
            label=(TextView)base.findViewById(R.id.label);
        }
        return(label);
    }
}
