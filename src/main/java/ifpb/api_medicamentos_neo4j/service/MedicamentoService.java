package ifpb.api_medicamentos_neo4j.service;

import ifpb.api_medicamentos_neo4j.DTO.MedicamentoCreateDTO;
import ifpb.api_medicamentos_neo4j.entity.Contem;
import ifpb.api_medicamentos_neo4j.entity.Medicamento;
import ifpb.api_medicamentos_neo4j.entity.PrincipioAtivo;
import ifpb.api_medicamentos_neo4j.exception.MedicamentoNaoEncontradoException;
import ifpb.api_medicamentos_neo4j.exception.PrincipioAtivoNaoEncontradoException;
import ifpb.api_medicamentos_neo4j.repository.MedicamentoRepository;
import ifpb.api_medicamentos_neo4j.repository.PrincipioAtivoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final PrincipioAtivoRepository principioAtivoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository, PrincipioAtivoRepository principioAtivoRepository) {
        this.medicamentoRepository = medicamentoRepository;
        this.principioAtivoRepository = principioAtivoRepository;
    }

    @Transactional
    public Medicamento salvarMedicamento(MedicamentoCreateDTO dto) {
        Medicamento medicamento = new Medicamento();
        medicamento.setNome(dto.nome());
        medicamento.setFabricante(dto.fabricante());

        Set<Contem> composicao = dto.composicao()
                .stream()
                .map(item -> {
                    PrincipioAtivo principioAtivo = new PrincipioAtivo();
                    try {
                        principioAtivo = principioAtivoRepository.findPrincipioAtivoByNome(item.principioAtivo());
                    } catch (PrincipioAtivoNaoEncontradoException e) {
                        principioAtivo.setNome(item.principioAtivo());
                    }
                    Contem contem = new Contem();
                    contem.setPrincipioAtivo(principioAtivo);
                    contem.setDosagem(item.dosagem());
                    contem.setUnidade(item.unidade());
                    return contem;
                })
                .collect(Collectors.toSet());
        medicamento.setComposicao(composicao);
        return medicamentoRepository.save(medicamento);
    }

    public List<Medicamento> buscarMedicamentos() {
        return medicamentoRepository.findAll();
    }

    public Medicamento buscarMedicamentoPorId(String id) {
        if(medicamentoRepository.existsMedicamentoById(id)){
            return medicamentoRepository.findMedicamentoById(id);
        }
        throw new MedicamentoNaoEncontradoException();
    }

    public Medicamento buscarMedicamentoPorNome(String nome) {
        if(medicamentoRepository.existsMedicamentoByNome(nome)) {
            return medicamentoRepository.findMedicamentoByNome(nome);
        }
        throw new MedicamentoNaoEncontradoException();
    }

    public List<Medicamento> buscarMedicamentosPorPrincipioAtivo(String principioAtivo) {
        if(principioAtivo == null || principioAtivoRepository.findPrincipioAtivoByNome(principioAtivo) == null) {
            throw new PrincipioAtivoNaoEncontradoException();
        }
        return medicamentoRepository.findMedicamentosByPrincipioAtivo(principioAtivo);
    }

    public List<Medicamento> buscarMedicamentosComMesmaComposicao(String medicamento) {
        if(medicamento != null && medicamentoRepository.existsMedicamentoByNome(medicamento)) {
            return medicamentoRepository.findMedicamentosWithMesmaComposicao(medicamento);
        }
        throw new MedicamentoNaoEncontradoException();
    }
}
