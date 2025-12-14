package ifpb.api_medicamentos_neo4j.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@NoArgsConstructor
@Data
@RelationshipProperties
public class Contem {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private PrincipioAtivo principioAtivo;

    @Property("dosagem")
    private String dosagem;
    @Property("unidade")
    private String unidade;
}
