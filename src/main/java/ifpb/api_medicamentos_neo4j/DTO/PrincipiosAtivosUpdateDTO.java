package ifpb.api_medicamentos_neo4j.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public record PrincipiosAtivosUpdateDTO (
    String nome;
    String fabricante;
    List<ComposicaoUpdateDTO> composicao
) {

}
