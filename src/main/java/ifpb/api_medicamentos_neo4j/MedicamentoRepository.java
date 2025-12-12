package ifpb.api_medicamentos_neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MedicamentoRepository extends Neo4jRepository<Medicamento, String> {
    Medicamento findMedicamentoById(String id);
}
