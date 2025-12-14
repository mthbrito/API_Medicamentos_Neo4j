package ifpb.api_medicamentos_neo4j.mapper;

import ifpb.api_medicamentos_neo4j.DTO.*;
import ifpb.api_medicamentos_neo4j.entity.Medicamento;
import ifpb.api_medicamentos_neo4j.entity.PrincipioAtivo;

import java.util.List;
import java.util.stream.Collectors;

public class MedicamentoMapper {

    public static MedicamentoComComposicaoResponseDTO toMedicamentoComComposicaoResponseDTO(Medicamento medicamento) {
        List<ComposicaoResponseDTO> composicao = medicamento.getComposicao()
                .stream()
                .map(contem -> new ComposicaoResponseDTO(
                        contem.getPrincipioAtivo().getNome(),
                        contem.getDosagem(),
                        contem.getUnidade()
                ))
                .toList();
        return new MedicamentoComComposicaoResponseDTO(
                medicamento.getNome(),
                medicamento.getFabricante(),
                composicao
        );
    };

    public static MedicamentoResponseDTO toMedicamentoResponseDTO(Medicamento medicamento) {
        return new MedicamentoResponseDTO(
                medicamento.getId(),
                medicamento.getNome(),
                medicamento.getFabricante()
        );
    }

    public static MedicamentosMesmaComposicaoResponseDTO toMedicamentosMesmaComposicaoResponseDTO(Medicamento medicamento, List<Medicamento> medicamentos) {
        MedicamentoComComposicaoResponseDTO medicamentoComComposicaoResponseDTO = toMedicamentoComComposicaoResponseDTO(medicamento);
        List<MedicamentoResponseDTO> medicamentosResponseDTO =
                medicamentos
                        .stream()
                        .map(MedicamentoMapper::toMedicamentoResponseDTO)
                        .toList();
        return new MedicamentosMesmaComposicaoResponseDTO(
                medicamentoComComposicaoResponseDTO,
                medicamentosResponseDTO
        );
    }
}
