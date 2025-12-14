package ifpb.api_medicamentos_neo4j.DTO;

public record ComposicaoResponseDTO(
        PrincipioAtivoResponseDTO principioAtivo,
        String dosagem,
        String unidade
) {
}
