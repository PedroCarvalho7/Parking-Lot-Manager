import java.util.Objects;

public class Lugar {

    private int numero;
    private int escalao;

    /**
     * Construtor
     * @param numero - número do lugar
     * @param escalao - escalão a que pertence
     */
    public Lugar(int numero, int escalao) {
        this.numero = numero;
        this.escalao = escalao;
    }

    public int obterNumero() {
        return numero;
    }

    public int obterEscalao() {
        return escalao;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lugar)) return false;
        Lugar lugar = (Lugar) o;
        return numero == lugar.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "Lugar{" + "numero=" + numero + ", escalao=" + escalao + '}';
    }
}

