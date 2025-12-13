package ifpb.api_medicamentos_neo4j.service;

import ifpb.api_medicamentos_neo4j.DTO.MedicamentoCreateDTO;
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

        Set<PrincipioAtivo> principios = dto.principiosAtivos()
                .stream()
                .map(nome -> {
                    try {
                        return principioAtivoRepository.findPrincipioAtivoByNome(nome);
                    } catch (PrincipioAtivoNaoEncontradoException e) {
                        PrincipioAtivo principioAtivo = new PrincipioAtivo();
                        principioAtivo.setNome(nome);
                        return principioAtivo;
                    }
                })
                .collect(Collectors.toSet());
        medicamento.setPrincipiosAtivos(principios);
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
