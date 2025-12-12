package ifpb.api_medicamentos_neo4j;

import org.springframework.stereotype.Service;

@Service
public class PrincipioAtivoService {

    private final PrincipioAtivoRepository principioAtivoRepository;

    public PrincipioAtivoService(PrincipioAtivoRepository principioAtivoRepository) {
        this.principioAtivoRepository = principioAtivoRepository;
    }

    public PrincipioAtivo buscarPrincipioAtivoPorId(String Id) {
        return principioAtivoRepository.findPrincipioAtivoById(Id);
    }
}
