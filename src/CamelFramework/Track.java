package CamelFramework;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

public class Track {
    private ArrayList<Tile> track;
    private int length;
    public Track(int length){
        this.length = length;
        this.track = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            this.track.add(new Tile());
        }
    }

    public int findCamel(int cId){
        for(Tile t : this.track){
            if(t.containsCamel(cId)){
                return t.getId();
            }
        }
        return -1;
    }

    public int indexToTileId(int index){
        return this.track.get(index).getId();
    }

    public int tileIdToIndex(int id){
        for(Tile t : this.track){
            if(t.getId() == id){
                return this.track.indexOf(t);
            }
        }
        return -1;
    }

    public int[] tileSpace(){
        this.track.trimToSize();
        int[] list = new int[this.track.size()];
        for (int i = 0; i < this.track.size(); i++){
            list[i] = this.track.get(i).getId();
        }
        return list;
    }

    public int moveCamelToTile(int cId, int newTileId){
        int cLoc = this.findCamel(cId);
        if(cLoc == -1){
            return -1;
        }

        Caravan toMove = this.track.get(tileIdToIndex(cLoc)).createCaravan(cId);
        int tmpLength = toMove.size();
        this.addCaravan(toMove, newTileId);
        return tmpLength;
    }

    public void addCaravan(Caravan c, int tileId){
        int tileIndex = tileIdToIndex(tileId);
        if(tileId >= this.track.get(this.track.size()-1).getId()){
            tileIndex = this.track.size()-1;
        }
        else if(tileId < this.track.get(0).getId()){
            tileIndex = 0;
        }
        while(!c.isEmpty()) {
            this.track.get(tileIndex).addCamel(c.unload());
        }
    }

    public void addCamel(int c, int tileId){
        int tileIndex = tileIdToIndex(tileId);
        if(tileId >= this.track.get(this.track.size()-1).getId()){
            tileIndex = this.track.size()-1;
        }
        else if(tileId < this.track.get(0).getId()){
            tileIndex = 0;
        }
        this.track.get(tileIndex).addCamel(c);
    }

    public ArrayList<Integer> getRanking(){
        ArrayList<Integer> ranking = new ArrayList<>();
        for (int i = this.track.size()-1; i >= 0; i--){
            if(this.track.get(i).isEmpty()){
                continue;
            }
            Enumeration<Integer> localRanking = this.track.get(i).getLocalRanking();
            ArrayList<Integer> tmpLocalRanking = new ArrayList<>();
            while(localRanking.hasMoreElements()){
                tmpLocalRanking.add(localRanking.nextElement());
            }
            Collections.reverse(tmpLocalRanking);
            ranking.addAll(tmpLocalRanking);
        }
        return ranking;
    }


    public String toString(){
        StringBuilder s = new StringBuilder("\n-------------");
        s.append(this.track.get(0).getId()-1).append("\n");
        for(Tile t : this.track){
            s.append(t.toString()).append("\n-------------").append(t.getId()).append("\n");
        }
        return s.toString();
    }

    public int getLength(){
        return length;
    }

    public String toString(ArrayList<Camel> camels){
        StringBuilder s = new StringBuilder("\n-------------");
        s.append(this.track.get(0).getId()-1).append("\n");
        for(Tile t : this.track){
            s.append(t.toString(camels)).append("\n-------------").append(t.getId()).append("\n");
        }
        return s.toString();
    }
}
