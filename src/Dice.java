import java.util.Random;

public class Dice {
    private boolean rolled;
    private int[] values;

    public Dice(boolean rolled, int minRoll, int maxRoll){
        if(minRoll >= maxRoll){
            return;
        }

        this.rolled = rolled;
        this.values = new int[maxRoll - minRoll];

        for (int i = minRoll; i < maxRoll; i++){
            this.values[i-minRoll] = i;
        }
    }

    public int roll(int i){
        if(rolled){
            return Integer.MIN_VALUE;
        }
        rolled = true;
        return values[i];
    }

    public int rollRandom(){
        int index = (int) Math.floor(Math.random() * size());
        return roll(index);
    }

    public void resetLeg(){
        rolled = false;
    }

    public boolean isRolled(){
        return rolled;
    }

    public int size(){
        return values.length;
    }
}
