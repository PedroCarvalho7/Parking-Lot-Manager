import java.util.Objects;

public class Funcionario {

    private String nome;
    private int escalao;

    /**
     * Construtor
     * @param nome - o nome do funcionário
     * @param escalao - o escalão do funcionário
     */
    public Funcionario(String nome, int escalao) {
        this.nome = nome;
        this.escalao = escalao;
    }

    public String obterNome() {
        return nome;
    }

    public int getEscalao() {
        return escalao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Funcionario)) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(nome, that.nome); // Assume que nome é único
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome); // Baseado apenas no nome
    }

    @Override
    public String toString() {
        return nome + " (escalao " + escalao + ")";
    }
}
