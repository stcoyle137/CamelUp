import Backend.RollLog;
import CamelFramework.Camel;
import CamelFramework.Track;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private Stables s;
    private Track t;
    private RollLog rollLog;
    public Game(char[] rprsnts, int trackLength){
        this.rollLog = new RollLog();
        this.s = new Stables();
        for(Character c : rprsnts) {
            s.addCamel(0, c, 1, 3);
        }
        this.t = new Track(trackLength);
        for(Camel c : s.camels()){
            t.addCamel(c.getId(), c.getRandomRoll());
            c.resetDice();
        }
        this.s.updateRank(this.t.getRanking());
        System.out.println(t.toString(s.camels()));
        System.out.println(s);
    }

    public Game(char[] rprsnts, int[] startingPosition, int trackLength){
        this.rollLog = new RollLog();
        this.s = new Stables();
        for(Character c : rprsnts) {
            s.addCamel(0, c, 1, 3);
        }
        this.t = new Track(trackLength);
        for(int i = 0; i < startingPosition.length; i++){
            t.addCamel(this.s.camels().get(i).getId(), startingPosition[i]);
        }
        this.s.updateRank(this.t.getRanking());
    }
    public void determinedRollDeterminedCamel(int rollOutcome, int camelOutcome){
        int roll = s.determinedRoll(camelOutcome,rollOutcome);
        if(roll == Integer.MIN_VALUE) {
            return;
        }
        int formerLoc = t.findCamel(camelOutcome);
        int newLoc = formerLoc + roll;

        int affected = t.moveCamelToTile(camelOutcome, newLoc);
        updateRanking();

        this.rollLog.addRollLog(this.s.findCamel(camelOutcome), roll, formerLoc, newLoc, affected);
    }
    public void randomRollDeterminedCamel(int camelOutcome){
        int roll = s.randomedRoll(camelOutcome);
        if(roll == Integer.MIN_VALUE) {
            return;
        }
        int formerLoc = t.findCamel(camelOutcome);
        int newLoc = formerLoc + roll;

        int affected = t.moveCamelToTile(camelOutcome, newLoc);
        updateRanking();

        this.rollLog.addRollLog(this.s.findCamel(camelOutcome), roll, formerLoc, newLoc, affected);
    }
    public void randomRollRandomCamel(){
        ArrayList<Camel> tmp = s.restedCamels();
        if(tmp.isEmpty()){
            return;
        }
        Collections.shuffle(tmp);
        int camelOutcome = tmp.get(0).getId();
        int roll = s.randomedRoll(camelOutcome);
        int formerLoc = t.findCamel(camelOutcome);
        int newLoc = formerLoc + roll;

        int affected = t.moveCamelToTile(camelOutcome, newLoc);
        updateRanking();

        this.rollLog.addRollLog(this.s.findCamel(camelOutcome), roll, formerLoc, newLoc, affected);
    }
    public void resetLeg(){
        s.newLeg();
        rollLog.legReset();
    }
    public String toString(){
        return t.toString(s.camels()) + s;
    }
    public String getLog(){
        return rollLog.toString();
    }
    public void updateRanking(){
        s.updateRank(t.getRanking());
    }
    public static void main(String[] args){
        char[] c = {'r','b','g','y','p'};
        Game g = new Game(c,10);
        g.randomRollRandomCamel();
        g.randomRollRandomCamel();
        g.randomRollRandomCamel();
        g.randomRollRandomCamel();
        g.randomRollRandomCamel();
        System.out.println(g);
        System.out.println(g.getLog());
    }
}
