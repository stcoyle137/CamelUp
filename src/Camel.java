public class Camel {
    private static int globalIdEnumorator = 0;
    private int rank;
    private int id;
    private char reprsnt;

    public Camel(int rank, char reprsnt){
        this.rank = rank;
        this.reprsnt = reprsnt;
        this.id = globalIdEnumorator;
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

}
