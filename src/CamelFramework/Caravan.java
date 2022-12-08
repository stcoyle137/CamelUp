package CamelFramework;

import java.util.Stack;

public class Caravan {
    private Stack<Integer> caravan;

    public Caravan(){
        this.caravan = new Stack<>();
    }

    public void load(Integer c){
        caravan.push(c);
    }

    public int unload(){
        return caravan.pop();
    }
    public int size(){
        return caravan.size();
    }

    public boolean isEmpty() { return caravan.isEmpty(); }
}
