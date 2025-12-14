package ifpb.api_medicamentos_neo4j.DTO;

import java.util.List;

public record MedicamentosMesmaComposicaoResponseDTO(
    List<MedicamentoResponseDTO> medicamentos,
    List<String> principiosAtivos
) {
}
