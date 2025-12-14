package ifpb.api_medicamentos_neo4j.DTO;

public record ComposicaoUpdateDTO(
        String principioAtivo,
        String dosagem,
        String unidade
) {
}
