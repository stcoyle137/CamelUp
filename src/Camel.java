public class Camel {
    private int id;
    private char representative;
    private int physPosition;
    private int racePosition;
    private Dice dice;

    public Camel(int id, char representative, int physPosition, int racePosition){
        this.id = id;
        this.representative = representative;
        this.physPosition = physPosition;
        this.racePosition = racePosition;
        this.dice = new Dice(false, 1,3);
    }

    public int getRacePosition(){
        return this.racePosition;
    }

    public int getPhysPosition(){
        return this.physPosition;
    }

    public void incrementPhysPosition(int increment){
        this.physPosition += increment;
    }

    public int rollRandom(){
        return this.dice.rollRandom();
    }

    public int rollSize(){
        return this.dice.size();
    }

    public int rollDeter(int index){
        return this.dice.roll(index);
    }

    public char getRepr(){
        return this.representative;
    }

}
