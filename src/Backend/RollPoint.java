package Backend;

import java.awt.*;

public class RollPoint {
    private Point camelXroll;

    public RollPoint(int camelId, int roll){
        this.camelXroll = new Point(camelId,roll);
    }

    public int getCamel(){
        return this.camelXroll.x;
    }

    public int getRoll(){
        return this.camelXroll.y;
    }

    public RollPoint deepCopy(){
        return new RollPoint(this.getCamel(), this.getRoll());
    }

    public String toString(){
        return "(" + this.camelXroll.x + ", " + this.camelXroll.y + ")";
    }
}