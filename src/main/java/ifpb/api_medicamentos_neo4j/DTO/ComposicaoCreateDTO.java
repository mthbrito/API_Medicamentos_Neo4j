package ifpb.api_medicamentos_neo4j.DTO;

public record ComposicaoCreateDTO(
        String principioAtivo,
        String dosagem,
        String unidade
) {
}
