package zoot.types;

public abstract class Type {

    @Override
    public abstract String toString();

    public boolean equals(Type t){
        return this.toString().equals(t.toString());
    }
}
