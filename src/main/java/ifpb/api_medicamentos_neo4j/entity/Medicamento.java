package ifpb.api_medicamentos_neo4j.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.Set;

@NoArgsConstructor
@Data
@Node
public class Medicamento {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    @Property("nome_comercial")
    private String nome;
    @Property("fabricante")
    private String fabricante;

    @Relationship(type = "CONTEM")
    private Set<Contem> composicao;
}
