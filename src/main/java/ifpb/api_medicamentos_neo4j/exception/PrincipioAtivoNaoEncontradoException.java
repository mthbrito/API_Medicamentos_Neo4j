package ifpb.api_medicamentos_neo4j.exception;

public class PrincipioAtivoNaoEncontradoException extends RuntimeException {
    public PrincipioAtivoNaoEncontradoException(String message) {
        super(message);
    }

    public PrincipioAtivoNaoEncontradoException() {
        System.out.println("Principio Ativo não encontrado!");
    }
}
