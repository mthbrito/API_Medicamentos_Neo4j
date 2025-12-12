package ifpb.api_medicamentos_neo4j;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public Medicamento findMedicamentoById(String id) {
        return medicamentoRepository.findMedicamentoById(id);
    }
}
