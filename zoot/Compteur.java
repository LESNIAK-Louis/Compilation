package zoot;

public class Compteur {
    private int nb;

    /**
     * Constructeur privé du singleton Compteur
     */
    private Compteur() {
        this.nb = 0;
    }

    private static final Compteur instance = new Compteur();

    /**
     * @return instance de la classe Compteur.
     */
    public static Compteur getInstance() {
        return instance;
    }

    /**
     * Retourne un nombre unique
     * @return nb
     */
    public int nombreUnique() {
        return nb++;
    }
}
