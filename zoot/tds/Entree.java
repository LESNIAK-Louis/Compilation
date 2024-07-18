package zoot.tds;

public abstract class Entree {

    protected String idf;
    private int bloc;

    public Entree(String idf, int bloc){

        this.idf = idf;
        this.bloc = bloc;
    }

    @Override
    public int hashCode() {
        return idf.hashCode();
    }

    @Override
    public boolean equals(Object e){

        return (e instanceof Entree) && this.idf.equals(((Entree)e).idf) && this.bloc == ((Entree)e).bloc;
    }

    @Override
    public String toString(){
        return this.idf;
    }

    public String getIdf(){
        return this.idf;
    }

    public int getBloc() {
        return bloc;
    }
}
