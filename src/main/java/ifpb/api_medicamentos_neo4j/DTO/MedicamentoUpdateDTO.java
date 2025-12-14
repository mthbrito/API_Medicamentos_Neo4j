package ifpb.api_medicamentos_neo4j.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicamentoUpdateDTO {

    private String id;
    private String nome;
    private String descricao;
    private Double preco;
    private String fabricante;
}

