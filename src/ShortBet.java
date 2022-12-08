import java.util.ArrayList;

public class ShortBet {
    public static double getEV(int cardValue, ArrayList<Double> a){
        if(a.isEmpty()){
            return 0;
        }
        double ev = a.get(0) * cardValue;
        for(int i = 1; i < a.size(); i++){
            if(i == 1){
                ev += a.get(i);
            }
            else{
                ev -= a.get(i);
            }
        }
        return ev;
    }

    public static double getLowestFairBet(ArrayList<Double> a){
        if(a.isEmpty()){
            return 0;
        }
        double baseValue = getEV(0,a);
        return (-baseValue) / (a.get(0));
    }

    public static double getLowestRollBet(ArrayList<Double> a){
        if(a.isEmpty()){
            return 0;
        }
        double baseValue = getEV(0,a);
        return (1-baseValue) / (a.get(0));
    }
}
