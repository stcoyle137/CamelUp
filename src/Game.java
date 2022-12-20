import Backend.RollLog;
import Backend.RollPoint;
import Backend.RollSpace;
import CamelFramework.Camel;
import CamelFramework.Stables;
import CamelFramework.Track;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {
    private Stables s;
    private Track t;
    private RollSpace sp;
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
        this.updateRanking();

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
        this.updateRanking();

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
        this.updateRanking();

        this.rollLog.addRollLog(this.s.findCamel(camelOutcome), roll, formerLoc, newLoc, affected);
    }
    public void runAnalysis(){
        //TODO: Create Save Point instead of using the restore Roll Points expression
        ArrayList<Integer> tmp = this.s.unrestedCamels();
        this.sp = new RollSpace(this.s);
        for(ArrayList<RollPoint> rps : this.sp.rollSpace){
            followRollPoints(rps);
            for(Camel c : this.s.camels()){
                int val = this.sp.outcome.get(c.getId()).get(c.getRank()-1);
                this.sp.outcome.get(c.getId()).set(c.getRank()-1, val+1);
            }
            restoreRollPoints(rps);
        }
        this.sp.normalizeOutcome();
        for(int id : tmp){
            determinedRollDeterminedCamel(0, id);
        }
    }

    public void followRollPoints(ArrayList<RollPoint> rps){
        for(RollPoint rp : rps){
            determinedRollDeterminedCamel(rp.getRoll(),rp.getCamel());
        }
    }

    public void restoreRollPoints(ArrayList<RollPoint> rps){
        resetLeg();
        for(int i = rps.size()-1; i >= 0; i--){
            determinedRollDeterminedCamel(-rps.get(i).getRoll(),rps.get(i).getCamel());
        }
        resetLeg();
    }

    public String getAnalysis(){
        StringBuilder s = new StringBuilder();
        for(Camel c : this.s.camels()){
            s.append(getOutcome(c.getReprsnt(), this.sp.outcome.get(c.getId())));
        }
        return s.toString();
    }
    public String getNormalizeAnalysis(){
        StringBuilder s = new StringBuilder();
        for(Camel c : this.s.camels()){
            s.append(getNormalizeOutcome(c.getReprsnt(), this.sp.outcomeNormalize.get(c.getId())));
            s.append("\t").append("Lowest Fair Bet: ").append((int) Math.ceil(ShortBet.getLowestFairBet(this.sp.outcomeNormalize.get(c.getId()))));
            s.append("\n\t").append("Lowest Roll Bet: ").append((int) Math.ceil(ShortBet.getLowestRollBet(this.sp.outcomeNormalize.get(c.getId()))));
            s.append("\n\n");
        }
        return s.toString();
    }


    private static String getOutcome(char repres, ArrayList<Integer> a){
        StringBuilder s = new StringBuilder(repres + ": ");
        for(int i = 0; i < a.size(); i++){
            s.append(i+1).append(" - ").append(a.get(i)).append(" times; ");
        }
        return s + "\n";
    }
    private static String getNormalizeOutcome(char repres, ArrayList<Double> a){
        StringBuilder s = new StringBuilder(repres + ": ");
        for(int i = 0; i < a.size(); i++){
            s.append(i+1).append(" - ").append(a.get(i)).append(" times; ");
        }
        return s + "\n";
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
        char[] c = {'r', 'b', 'g', 'y', 'p'};
        int[] i = {1, 1, 3, 3, 3};
        Game g = new Game(c,i, 17);
        g.randomRollRandomCamel();
        g.runAnalysis();
        System.out.println(g.getNormalizeAnalysis());
        System.out.println(g);
    }
}
