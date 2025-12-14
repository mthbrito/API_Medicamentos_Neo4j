package ifpb.api_medicamentos_neo4j.DTO;

import java.util.List;

public record MedicamentoUpdateDTO (
        String nome,
        String fabricante,
        List<ComposicaoUpdateDTO> composicao
) {

}

