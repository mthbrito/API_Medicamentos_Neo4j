package ifpb.api_medicamentos_neo4j.service;

import ifpb.api_medicamentos_neo4j.DTO.*;
import ifpb.api_medicamentos_neo4j.entity.Contem;
import ifpb.api_medicamentos_neo4j.entity.Medicamento;
import ifpb.api_medicamentos_neo4j.entity.PrincipioAtivo;
import ifpb.api_medicamentos_neo4j.exception.MedicamentoNaoEncontradoException;
import ifpb.api_medicamentos_neo4j.exception.PrincipioAtivoNaoEncontradoException;
import ifpb.api_medicamentos_neo4j.repository.MedicamentoRepository;
import ifpb.api_medicamentos_neo4j.repository.PrincipioAtivoRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.Nullable;
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

    public MedicamentoComComposicaoResponseDTO buscarMedicamentoPorNome(String nome) {
        if(medicamentoRepository.existsMedicamentoByNome(nome)) {
            return toMedicamentoComComposicaoResponseDTO(medicamentoRepository.findMedicamentoByNome(nome));
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
        if (nome == null || !medicamentoRepository.existsMedicamentoByNome(nome)) {
            throw new MedicamentoNaoEncontradoException();
        }
        Medicamento medicamento = medicamentoRepository.findMedicamentoByNome(nome);
        List<Medicamento> medicamentos = medicamentoRepository.findMedicamentosWithMesmaComposicao(nome);
            return toMedicamentosMesmaComposicaoResponseDTO(medicamento, medicamentos);
    }

    @Transactional
    public MedicamentoComComposicaoResponseDTO atualizarMedicamento(String id, MedicamentoUpdateDTO dto) {
        if(!medicamentoRepository.existsMedicamentoById(id)) {
            throw new MedicamentoNaoEncontradoException();
        }
        Medicamento medicamento = medicamentoRepository.findMedicamentoById(id);
        if (dto.nome() != null) {
            medicamento.setNome(dto.nome());
        }
        if (dto.fabricante() != null) {
            medicamento.setFabricante(dto.fabricante());
        }
        if (dto.composicao() != null) {
            medicamento.getComposicao().clear();
            for (ComposicaoUpdateDTO item : dto.composicao()) {

                PrincipioAtivo principio =
                        principioAtivoRepository
                                .findPrincipioAtivoByNome(item.principioAtivo());

                Contem contem = new Contem();
                contem.setPrincipioAtivo(principio);
                contem.setDosagem(item.dosagem());
                contem.setUnidade(item.unidade());

                medicamento.getComposicao().add(contem);
            }
        }
        return toMedicamentoComComposicaoResponseDTO(medicamentoRepository.save(medicamento));
    }

    public void deletarMedicamento(String id) {
        if(!medicamentoRepository.existsMedicamentoById(id)) {
            throw new MedicamentoNaoEncontradoException();
        }
        medicamentoRepository.deleteById(id);
    }

}
