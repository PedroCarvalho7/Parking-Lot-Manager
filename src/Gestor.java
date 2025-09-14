import java.util.*;

public class Gestor {

    private int nrEscaloes;
    private int nrLugares;
    private int estrategia;

    private BidirectionalHashMap<Funcionario, Lugar> atribuicoes;
    private Map<String, Funcionario> funcionarios;
    private Map<Integer, PriorityQueue<Lugar>> lugaresPorEscalao;

    public Gestor(int nrEscaloes, int nrLugares, int estrategia) {
        this.nrEscaloes = nrEscaloes;
        this.nrLugares = nrLugares;
        this.estrategia = estrategia;

        this.atribuicoes = new BidirectionalHashMap<>();
        this.funcionarios = new HashMap<>();
        this.lugaresPorEscalao = new HashMap<>();

        inicializarLugares();
    }

    private void inicializarLugares() {
        int lugaresPorEscalaoFixo = nrLugares/ nrEscaloes;
        int numeroLugar = 1;

        for (int escalao = nrEscaloes; escalao >= 1; escalao--) {
            PriorityQueue<Lugar> fila = new PriorityQueue<>(Comparator.comparingInt(Lugar::obterNumero));
            for (int i = 0; i < lugaresPorEscalaoFixo; i++) {
                fila.add(new Lugar(numeroLugar, escalao));
                numeroLugar++;
            }
            lugaresPorEscalao.put(escalao, fila);
        }
    }

    public boolean registar(String nome, int escalao) {
        if (!funcionarios.containsKey(nome)) {
            funcionarios.put(nome, new Funcionario(nome, escalao));
            return true;
        }
        return false;
    }

    public boolean atribuir(String nome) {
        Funcionario f = funcionarios.get(nome);
        if (f == null || atribuicoes.containsKey(f)) {
            return false;
        }

        Lugar lugar = (estrategia == 1)
                ? obterLugarLivre(f.getEscalao())
                : obterLugarDinamicamente(f.getEscalao());

        if (lugar != null) {
            atribuicoes.put(f, lugar);
            return true;
        }
        return false;
    }

    private Lugar obterLugarLivre(int escalao) {
        PriorityQueue<Lugar> fila = lugaresPorEscalao.get(escalao);
        return (fila != null && !fila.isEmpty()) ? fila.poll() : null;
    }

    private Lugar obterLugarDinamicamente(int escalao) {
        for (int e = escalao; e >= 1; e--) {
            Lugar l = obterLugarLivre(e);
            if (l != null) return l;
        }
        return null;
    }

    public int totalAtribuidos() {
        return atribuicoes.size();
    }

    public int atribuidosNoEscalao(int escalao) {
        int count = 0;
        for (Funcionario f : atribuicoes.keySet()) {
            if (f.getEscalao() == escalao) count++;
        }
        return count;
    }

    public Funcionario obterDono(int numeroLugar) {
        for (Map.Entry<Funcionario, Lugar> entry : atribuicoes.entrySet()) {
            if (entry.getValue().obterNumero() == numeroLugar) {
                return entry.getKey();
            }
        }
        return null;
    }

    public int obterNumero(String nomeFuncionario) {
        Funcionario f = funcionarios.get(nomeFuncionario);
        if (f != null && atribuicoes.containsKey(f)) {
            return atribuicoes.getValue(f).obterNumero();
        }
        return -1;
    }

    public Lugar removerAtribuicaoPorNome(String nome) {
        Funcionario f = funcionarios.get(nome);
        if (f != null && atribuicoes.containsKey(f)) {
            Lugar l = atribuicoes.removeByKey(f);
            if (l != null) {
                lugaresPorEscalao.get(l.obterEscalao()).add(l);
                return l;
            }
        }
        return null;
    }
    public Lugar removerAtribuicaoPorNumero(int numeroLugar) {
        for (Map.Entry<Funcionario, Lugar> entry : atribuicoes.entrySet()) {
            if (entry.getValue().obterNumero() == numeroLugar) {
                Lugar l = entry.getValue();
                atribuicoes.removeByKey(entry.getKey());
                lugaresPorEscalao.get(l.obterEscalao()).add(l);
                return l;
            }
        }
        return null;
    }
}

