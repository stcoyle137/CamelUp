package CamelFramework;

public class Camel {
    private static int globalIdEnumorator = 0;
    private int rank;
    private int id;
    private char reprsnt;
    private Dice dice;

    public Camel(int rank, char reprsnt, Dice dice){
        this.rank = rank;
        this.reprsnt = reprsnt;
        this.id = globalIdEnumorator;
        this.dice = dice;
        globalIdEnumorator++;
    }

    public int getRank(){
        return rank;
    }

    public void setRank(int rank){
        this.rank = rank;
    }

    public int getId(){
        return id;
    }

    public String toString(){
        return Character.toString(this.reprsnt);
    }

    public int getDeterminedRoll(int index){
        return this.dice.getDeterminedRoll(index);
    }
    public int getRandomRoll(){
        return this.dice.getRandomRoll();
    }
    public void resetDice(){
         this.dice.resetDice();
    }

    public int getSize(){
        return this.dice.getSize();
    }

    public boolean isRolled(){
        return this.dice.isRolled();
    }
    public int[] getFaces(){ return this.dice.getFaces(); }
    public char getReprsnt(){ return this.reprsnt; }

}
