package zoot.tds;

public class EntreeFonction extends Entree {

    private int nbParams;

    public EntreeFonction(String idf, int bloc, int nbParams) {
        super(idf,bloc);

        this.nbParams = nbParams;
    }

    @Override
    public boolean equals(Object e){

        return (e instanceof Entree) && this.idf.equals(((Entree)e).idf) && this.nbParams == ((EntreeFonction)e).nbParams;
    }
}
