package ifpb.api_medicamentos_neo4j.service;

import ifpb.api_medicamentos_neo4j.DTO.PrincipioAtivoUpdateDTO;
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

    public PrincipioAtivo atualizarPrincipioAtivo(String id, PrincipioAtivoUpdateDTO dto) {

        if(!principioAtivoRepository.existsPrincipioAtivoById(id)) {
            throw new PrincipioAtivoNaoEncontradoException();
        }
        PrincipioAtivo principioAtivo = principioAtivoRepository.findPrincipioAtivoById(id);

        if(dto.nome() != null) {
            principioAtivo.setNome(dto.nome());
        }
        if(dto.sistemaAtuacao() != null) {
            principioAtivo.setSistemaAtuacao(dto.sistemaAtuacao());
        }
        return principioAtivoRepository.save(principioAtivo);
    }

    public void deletarPrincipioAtivo(String id) {
        if (!principioAtivoRepository.existsById(id)) {
            throw new PrincipioAtivoNaoEncontradoException("Princípio ativo não encontrado");
        }
        principioAtivoRepository.deleteById(id);
    }
}
