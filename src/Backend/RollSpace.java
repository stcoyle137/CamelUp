package Backend;

import CamelFramework.Camel;
import CamelFramework.Stables;

import java.util.ArrayList;
import java.util.Hashtable;

public class RollSpace {
    public ArrayList<ArrayList<RollPoint>> rollSpace;
    public Hashtable<Integer, ArrayList<Integer>> outcome;
    public Hashtable<Integer, ArrayList<Double>> outcomeNormalize;

    public RollSpace(Stables s){
        this.rollSpace = new ArrayList<>();
        this.outcome = new Hashtable<>();
        this.outcomeNormalize = new Hashtable<>();

        ArrayList<Integer> camelIds = new ArrayList<>();
        Hashtable<Integer, ArrayList<Integer>> idToRollSpace = new Hashtable<>();
        for(Camel c : s.camels()){
            if(c.isRolled()){
                continue;
            }
            camelIds.add(c.getId());
            ArrayList<Integer> faces = new ArrayList<>();
            for(int i : c.getFaces()){
                faces.add(i);
            }
            idToRollSpace.put(c.getId(), faces);

            outcome.put(c.getId(), new ArrayList<>());
            for(int i = 0; i<s.camels().size(); i++){
                outcome.get(c.getId()).add(0);
            }
        }

        ArrayList<ArrayList<Integer>> permutatedIds = permutateId(camelIds);
        for (ArrayList<Integer> o : permutatedIds){
            rollSpace.addAll(permutatedRolls(o, idToRollSpace));
        }
    }

    public void normalizeOutcome(){
        for(int keyId : outcome.keySet()){
            int total = 0;
            for(int count : outcome.get(keyId)){
                total += count;
            }
            ArrayList<Double> d = new ArrayList<>();
            for(int count : outcome.get(keyId)){
                d.add(((double) count)/((double) total));
            }
            outcomeNormalize.put(keyId, d);
        }
    }

    public static ArrayList<ArrayList<Integer>> permutateId(ArrayList<Integer> ids){
        ids.trimToSize();
        if(ids.size() == 1){
            ArrayList<ArrayList<Integer>> t = new ArrayList<>();
            t.add(ids);
            return t;
        }
        ArrayList<ArrayList<Integer>> permutated = new ArrayList<>();
        for(Integer i : ids){
            ArrayList<ArrayList<Integer>> nextPermutated = permutateId(removeElement(ids, i));
            for(ArrayList<Integer> a : nextPermutated){
                a.add(i);
                permutated.add(a);
            }
        }
        return permutated;
    }

    public static ArrayList<ArrayList<RollPoint>> permutatedRolls(ArrayList<Integer> order, Hashtable<Integer, ArrayList<Integer>> idToRollSpace){
        ArrayList<ArrayList<RollPoint>> permutatedRolls = new ArrayList<>();

        int element = order.get(0);

        if(order.size() == 1){
            for(int r : idToRollSpace.get(element)) {
                ArrayList<RollPoint> tmpPermutatedRolls = new ArrayList<>();
                tmpPermutatedRolls.add(new RollPoint(order.get(0), r));
                permutatedRolls.add(tmpPermutatedRolls);
            }
        }
        else {
            ArrayList<RollPoint> tmpPermutatedRolls = new ArrayList<>();
            for(int r : idToRollSpace.get(element)) {
                tmpPermutatedRolls.add(new RollPoint(order.get(0), r));
            }
            ArrayList<ArrayList<RollPoint>> previousPermutatedRolls = permutatedRolls(removeElement(order,element), idToRollSpace);
            for(RollPoint newP : tmpPermutatedRolls){
                for(ArrayList<RollPoint> a : previousPermutatedRolls){
                    ArrayList<RollPoint> copy = new ArrayList<>();
                    copy.add(newP);
                    for(RollPoint rp : a){
                        copy.add(rp.deepCopy());
                    }
                    permutatedRolls.add(copy);
                }
            }
        }
        return permutatedRolls;
    }

    public static ArrayList<Integer> removeElement(ArrayList<Integer> a, int i){
        ArrayList<Integer> copy = new ArrayList<>();
        for(int elmt : a){
            if(elmt != i){
                copy.add(elmt);
            }
        }
        return copy;
    }

    public String toString(){
        return this.outcome.toString();
    }
}
