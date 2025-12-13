package ifpb.api_medicamentos_neo4j.exception;

public class MedicamentoNaoEncontradoException extends RuntimeException {

    public MedicamentoNaoEncontradoException(String message) {
        super(message);
    }

    public MedicamentoNaoEncontradoException() {
        System.out.println("Medicamento não encontrado!");
    }
}
