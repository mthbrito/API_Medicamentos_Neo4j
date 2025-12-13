package ifpb.api_medicamentos_neo4j.DTO;

import java.util.List;

public record MedicamentoCreateDTO(
        String nome,
        String fabricante,
        List<String> principiosAtivos
) {
}
