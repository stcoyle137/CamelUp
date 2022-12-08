package CamelFramework;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Stack;

public class Tile {
    private static int globalIdEnumerator = 1;

    private Stack<Integer> herd;
    private int id;

    public Tile(){
        this.id = globalIdEnumerator++;
        this.herd = new Stack<>();
    }

    public void addCamel(int c){
        this.herd.push(c);
    }

    public Caravan createCaravan(int leaderId){
        if (!containsCamel(leaderId)){
            return new Caravan();
        }
        Caravan c = new Caravan();
        int toAdd = Integer.MIN_VALUE;
        while(toAdd != leaderId){
            toAdd = this.herd.pop();
            c.load(toAdd);
        }
        if(c.isEmpty()){
            throw new NullPointerException("Empty Caravan Created");
        }
        return c;
    }

    public Enumeration<Integer> getLocalRanking(){
        return this.herd.elements();
    }

    public boolean isEmpty(){
        return this.herd.isEmpty();
    }

    public int getId(){
        return this.id;
    }

    public boolean containsCamel(int cId){
        return this.herd.contains(cId);
    }

    public String toString(){
        Enumeration<Integer> enu = getLocalRanking();
        StringBuilder s = new StringBuilder();
        while (enu.hasMoreElements()) {
            s.append(enu.nextElement()).append(" ");
        }
        return s.toString();
    }

    public String toString(ArrayList<Camel> camels){
        Enumeration<Integer> enu = getLocalRanking();
        StringBuilder s = new StringBuilder();
        while (enu.hasMoreElements()) {
            int i = enu.nextElement();
            boolean flag = true;
            for(Camel c : camels){
                if(c.getId() == i){
                    s.append(c).append(" ");
                    flag = false;
                    break;
                }
            }
            if(!flag) {
                continue;
            }
            s.append(enu.nextElement()).append(" ");
        }
        return s.toString();
    }
}
