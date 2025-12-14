package ifpb.api_medicamentos_neo4j.repository;

import ifpb.api_medicamentos_neo4j.entity.PrincipioAtivo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface PrincipioAtivoRepository extends Neo4jRepository<PrincipioAtivo, String> {

    boolean existsPrincipioAtivoById(String id);

    PrincipioAtivo findPrincipioAtivoById(String id);

    boolean existsPrincipioAtivoByNome(String nome);

    PrincipioAtivo findPrincipioAtivoByNome(String nome);

    @Query("""
            MATCH (m:Medicamento{nome_comercial:$medicamento})-[:CONTEM]->(p:PrincipioAtivo) RETURN p
            """)
    List<PrincipioAtivo> findPrincipiosAtivosByMedicamento(String medicamento);

}
