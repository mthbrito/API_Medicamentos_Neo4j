package ifpb.api_medicamentos_neo4j.DTO;

import java.util.List;

public record MedicamentoComComposicaoResponseDTO(
        String nome,
        String fabricante,
        List<ComposicaoResponseDTO> composicao
) {
}
