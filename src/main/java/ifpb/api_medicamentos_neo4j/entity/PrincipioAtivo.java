package ifpb.api_medicamentos_neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@NoArgsConstructor
@Data
@Node
public class PrincipioAtivo {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    @Property("principio_ativo")
    private String nome;
    @Property("sistema_atuacao")
    private String sistemaAtuacao;

}
