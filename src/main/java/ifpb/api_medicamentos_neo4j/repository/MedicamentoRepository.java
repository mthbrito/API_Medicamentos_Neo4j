package ifpb.api_medicamentos_neo4j.repository;

import ifpb.api_medicamentos_neo4j.entity.Medicamento;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface MedicamentoRepository extends Neo4jRepository<Medicamento, String> {

    boolean existsMedicamentoById(String id);

    Medicamento findMedicamentoById(String id);

    boolean existsMedicamentoByNome(String nome);

    Medicamento findMedicamentoByNome(String nome);

    @Query("""
            MATCH (m:Medicamento)-[:CONTEM]->(p:PrincipioAtivo)
            WHERE p.principio_ativo = $principioAtivo
            RETURN m;
            """)
    List<Medicamento> findMedicamentosByPrincipioAtivo(String principioAtivo);

    @Query("""
            MATCH (mRef:Medicamento {nome_comercial: $medicamento})-[:CONTEM]->(pRef:PrincipioAtivo)
            WITH mRef, pRef.principio_ativo AS principio
            ORDER BY principio
            WITH mRef, collect(principio) AS compRef
            MATCH (m:Medicamento)-[:CONTEM]->(p:PrincipioAtivo)
            WITH mRef, compRef, m, p.principio_ativo AS principio
            ORDER BY principio
            WITH mRef, compRef, m, collect(principio) AS compM
            WHERE compRef = compM AND m <> mRef
            return m
            """)
    List<Medicamento> findMedicamentosWithMesmaComposicao(String medicamento);

}
