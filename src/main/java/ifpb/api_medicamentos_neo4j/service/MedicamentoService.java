package ifpb.api_medicamentos_neo4j.service;

import ifpb.api_medicamentos_neo4j.DTO.MedicamentoCreateDTO;
import ifpb.api_medicamentos_neo4j.DTO.MedicamentoComComposicaoResponseDTO;
import ifpb.api_medicamentos_neo4j.DTO.MedicamentosMesmaComposicaoResponseDTO;
import ifpb.api_medicamentos_neo4j.DTO.MedicamentoResponseDTO;
import ifpb.api_medicamentos_neo4j.entity.Contem;
import ifpb.api_medicamentos_neo4j.entity.Medicamento;
import ifpb.api_medicamentos_neo4j.entity.PrincipioAtivo;
import ifpb.api_medicamentos_neo4j.exception.MedicamentoNaoEncontradoException;
import ifpb.api_medicamentos_neo4j.exception.PrincipioAtivoNaoEncontradoException;
import ifpb.api_medicamentos_neo4j.repository.MedicamentoRepository;
import ifpb.api_medicamentos_neo4j.repository.PrincipioAtivoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ifpb.api_medicamentos_neo4j.mapper.MedicamentoMapper.*;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final PrincipioAtivoRepository principioAtivoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository, PrincipioAtivoRepository principioAtivoRepository) {
        this.medicamentoRepository = medicamentoRepository;
        this.principioAtivoRepository = principioAtivoRepository;
    }

    @Transactional
    public MedicamentoComComposicaoResponseDTO salvarMedicamento(MedicamentoCreateDTO dto) {
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
        medicamentoRepository.save(medicamento);
        return toMedicamentoComComposicaoResponseDTO(medicamento);
    }

    public List<MedicamentoResponseDTO> buscarMedicamentos() {
        List<Medicamento> medicamentos = medicamentoRepository.findAll();
        List<MedicamentoResponseDTO> medicamentosResponseDTO = new ArrayList<>();
        medicamentos.forEach(medicamento -> {
            medicamentosResponseDTO.add(toMedicamentoResponseDTO(medicamento));
        });
        return medicamentosResponseDTO;
    }

    public MedicamentoResponseDTO buscarMedicamentoPorId(String id) {
        if(medicamentoRepository.existsMedicamentoById(id)){
            return toMedicamentoResponseDTO(medicamentoRepository.findMedicamentoById(id));
        }
        throw new MedicamentoNaoEncontradoException();
    }

    public MedicamentoResponseDTO buscarMedicamentoPorNome(String nome) {
        if(medicamentoRepository.existsMedicamentoByNome(nome)) {
            return toMedicamentoResponseDTO(medicamentoRepository.findMedicamentoByNome(nome));
        }
        throw new MedicamentoNaoEncontradoException();
    }

    public List<MedicamentoResponseDTO> buscarMedicamentosPorPrincipioAtivo(String principioAtivo) {
        if(principioAtivo == null || principioAtivoRepository.findPrincipioAtivoByNome(principioAtivo) == null) {
            throw new PrincipioAtivoNaoEncontradoException();
        }
        List<Medicamento> medicamentos = medicamentoRepository.findMedicamentosByPrincipioAtivo(principioAtivo);
        List<MedicamentoResponseDTO> medicamentosResponseDTO = new ArrayList<>();
        medicamentos.forEach(medicamento -> medicamentosResponseDTO.add(toMedicamentoResponseDTO(medicamento)));
        return medicamentosResponseDTO;
    }

    public MedicamentosMesmaComposicaoResponseDTO buscarMedicamentosComMesmaComposicao(String nome) {
        if(nome != null && medicamentoRepository.existsMedicamentoByNome(nome)) {
            List<Medicamento> medicamentos = medicamentoRepository.findMedicamentosWithMesmaComposicao(nome);
            List<PrincipioAtivo> principioAtivos = principioAtivoRepository.findPrincipiosAtivosByMedicamento(nome);
            return toMedicamentosMesmaComposicaoResponseDTO(medicamentos, principioAtivos);
        }
        throw new MedicamentoNaoEncontradoException();
    }
}
