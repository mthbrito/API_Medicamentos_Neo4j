package ifpb.api_medicamentos_neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PrincipioAtivoRepository extends Neo4jRepository<PrincipioAtivo, String> {
    PrincipioAtivo findPrincipioAtivoById(String id);
}
