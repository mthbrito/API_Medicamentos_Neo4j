package ifpb.api_medicamentos_neo4j;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public Medicamento findMedicamentoById(@RequestParam String id){
        return medicamentoService.findMedicamentoById(id);
    }
}
