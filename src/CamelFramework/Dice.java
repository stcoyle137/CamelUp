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

    public int getDeterminedRoll(int value){
        this.isRolled = true;
        return value;
    }
    public int getRandomRoll(){
        int index = (int) (Math.random() * this.size);
        return getDeterminedRoll(this.faces[index]);
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
    public int[] getFaces(){
        return this.faces;
    }
}
