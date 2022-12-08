package CamelFramework;

import CamelFramework.Camel;
import CamelFramework.Dice;

import javax.print.attribute.IntegerSyntax;
import java.util.ArrayList;

public class Stables {
    private ArrayList<Camel> camels;

    public Stables(){
        this.camels = new ArrayList<>();
    }

    public Stables(ArrayList<Camel> camels){
        this.camels = camels;
    }

    public void addCamel(int rank, char represent, int minRollVal, int maxRollVal){
        Camel tmpCamel = new Camel(rank, represent, new Dice(minRollVal, maxRollVal));
        this.camels.add(tmpCamel);
    }

    public void addCamel(Camel c){
        this.camels.add(c);
    }

    public void newLeg(){
        for(Camel c : this.camels){
            c.resetDice();
        }
    }

    public ArrayList<Camel> restedCamels(){
        ArrayList<Camel> tmp = new ArrayList<>();
        for(Camel c : this.camels){
            if(!c.isRolled()){
                tmp.add(c);
            }
        }
        return tmp;
    }

    public ArrayList<Integer> unrestedCamels(){
        ArrayList<Integer> tmp = new ArrayList<>();
        for(Camel c : this.camels){
            if(c.isRolled()){
                tmp.add(c.getId());
            }
        }
        return tmp;
    }

    public int determinedRoll(int camelId, int roll){
        if(findCamel(camelId).isRolled()){
            return Integer.MIN_VALUE;
        }
        return findCamel(camelId).getDeterminedRoll(roll);
    }

    public int randomedRoll(int camelId){
        if(findCamel(camelId).isRolled()){
            return Integer.MIN_VALUE;
        }
        return findCamel(camelId).getRandomRoll();
    }

    public void updateRank(ArrayList<Integer> ranks){
        int currentRank = 1;
        for (int rank : ranks) {
            findCamel(rank).setRank(currentRank++);
        }
    }

    public Camel findCamel(int id){
        for(Camel c : this.camels){
            if(c.getId() == id){
                return c;
            }
        }
        throw new NullPointerException("No camel found");
    }

    public ArrayList<Camel> camels(){
        return this.camels;
    }


    public String toString(){
        StringBuilder s = new StringBuilder();
        for(Camel c : this.camels){
            s.append(c).append(" : ").append(c.getRank()).append("\n");
        }
        return s.toString();
    }
}
