package ifpb.api_medicamentos_neo4j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Node
public class Medicamento {

    @Id
    private String id;
    @Property("nome_comercial")
    private String nome;
    @Property("fabricante")
    private String fabricante;

}
