package ifpb.api_medicamentos_neo4j.service;

import ifpb.api_medicamentos_neo4j.entity.PrincipioAtivo;
import ifpb.api_medicamentos_neo4j.exception.PrincipioAtivoNaoEncontradoException;
import ifpb.api_medicamentos_neo4j.repository.PrincipioAtivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrincipioAtivoService {

    private final PrincipioAtivoRepository principioAtivoRepository;

    public PrincipioAtivoService(PrincipioAtivoRepository principioAtivoRepository) {
        this.principioAtivoRepository = principioAtivoRepository;
    }

    public List<PrincipioAtivo> buscarPrincipiosAtivos() {
        return principioAtivoRepository.findAll();
    }

    public PrincipioAtivo buscarPrincipioAtivoPorId(String id) {
        if(principioAtivoRepository.existsPrincipioAtivoById(id)) {
            return principioAtivoRepository.findPrincipioAtivoById(id);
        }
        throw new PrincipioAtivoNaoEncontradoException();
    }

    public PrincipioAtivo buscarPrincipioAtivoPorNome(String nome) {
        if(principioAtivoRepository.existsPrincipioAtivoByNome(nome)) {
            return principioAtivoRepository.findPrincipioAtivoByNome(nome);
        }
        throw new PrincipioAtivoNaoEncontradoException();
    }

    public PrincipioAtivo atualizar(PrincipioAtivoUpdateDTO dto) {

        PrincipioAtivo principioAtivo = repository.findById(dto.getId())
                .orElseThrow(() ->
                        new PrincipioAtivoNaoEncontradoException("Princípio ativo não encontrado"));

        principioAtivo.setNome(dto.getNome());
        principioAtivo.setDescricao(dto.getDescricao());

        return repository.save(principioAtivo);
    }

    public void deletar(String id) {

        if (!repository.existsById(id)) {
            throw new PrincipioAtivoNaoEncontradoException("Princípio ativo não encontrado");
        }

        repository.deleteById(id);
    }
}
