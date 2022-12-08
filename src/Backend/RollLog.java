package Backend;

import CamelFramework.Camel;

public class RollLog {
    private String s;
    public RollLog(){
        s = "Roll History:\n";
    }
    public void addRollLog(Camel c, int roll, int formerLoc, int newLoc, int moved){
        s += c.toString() + " rolled a " + roll + " and moved from " + formerLoc + " to " + newLoc + " affecting " + moved + " camels\n";
    }

    public void legReset(){
        s += "Leg reset \n";
    }

    public String toString(){
        return s;
    }
}
