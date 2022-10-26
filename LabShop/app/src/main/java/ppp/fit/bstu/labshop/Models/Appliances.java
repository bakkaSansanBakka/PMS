package ppp.fit.bstu.labshop.Models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class Appliances implements Serializable {
    private static int counter = 0;

    public final int objectId;
    public String Name;
    public String Category;
    public double Price;
    public boolean IsInStock;
    public String Image;

    public Appliances() {
        this.objectId = counter++;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Appliances)) return false;
        Appliances o = (Appliances) obj;
        return o.objectId == this.objectId;
    }
}
