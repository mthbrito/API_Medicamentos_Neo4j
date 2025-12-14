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
                        new PrincipioAtivoResponseDTO(
                                contem.getPrincipioAtivo().getId(),
                                contem.getPrincipioAtivo().getNome()
                        ),
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

    public static MedicamentosMesmaComposicaoResponseDTO toMedicamentosMesmaComposicaoResponseDTO(List<Medicamento> medicamentos, List<PrincipioAtivo> principiosAtivos) {
        List<MedicamentoResponseDTO> medicamentosMesmaComposicao = medicamentos
                .stream()
                .map(MedicamentoMapper::toMedicamentoResponseDTO)
                .collect(Collectors.toList());
        List<String> nomesPrincipiosAtivos = principiosAtivos
                .stream()
                .map(PrincipioAtivo::getNome)
                .collect(Collectors.toList());
        return new MedicamentosMesmaComposicaoResponseDTO(
                medicamentosMesmaComposicao,
                nomesPrincipiosAtivos
        );
    }
}
