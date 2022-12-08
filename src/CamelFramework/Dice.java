package CamelFramework;


public class Dice {
    private boolean isRolled;
    private int[] faces;
    private int size;

    public Dice(int minVal, int maxVal){
        if(maxVal <= minVal){
            return;
        }
        this.size = maxVal - minVal + 1;
        this.faces = new int[this.size];

        for (int i = 0; i < this.size; i++) {
            this.faces[i] = i + minVal;
        }
    }

    public int getDeterminedRoll(int index){
        if(this.isRolled){
            return Integer.MIN_VALUE;
        }
        this.isRolled = true;
        return this.faces[index];
    }
    public int getRandomRoll(){
        int index = (int) (Math.random() * this.size);
        return getDeterminedRoll(index);
    }
    public void resetDice(){
        this.isRolled = false;
    }

    public int getSize(){
        return this.size;
    }

    public boolean isRolled(){
        return this.isRolled;
    }
}
