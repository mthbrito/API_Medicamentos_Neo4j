package ifpb.api_medicamentos_neo4j.controller;

import ifpb.api_medicamentos_neo4j.DTO.MedicamentoCreateDTO;
import ifpb.api_medicamentos_neo4j.entity.Medicamento;
import ifpb.api_medicamentos_neo4j.entity.PrincipioAtivo;
import ifpb.api_medicamentos_neo4j.service.MedicamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @PostMapping
    public ResponseEntity<Medicamento> salvarMedicamento(@RequestBody MedicamentoCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoService.salvarMedicamento(dto));
    }

    @GetMapping
    public ResponseEntity<List<Medicamento>> buscarMedicamentos(){
        return ResponseEntity.ok(medicamentoService.buscarMedicamentos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> buscarMedicamentoPorId(@PathVariable String id){
        return ResponseEntity.ok(medicamentoService.buscarMedicamentoPorId(id));
    }

    @GetMapping("/medicamento/{nome}")
    public ResponseEntity<Medicamento> buscarMedicamentoPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(medicamentoService.buscarMedicamentoPorNome(nome));
    }

    @GetMapping("/principio-ativo/{principioAtivo}")
    public ResponseEntity<List<Medicamento>> buscarMedicamentosPorPrincipioAtivo(@PathVariable String principioAtivo) {
        return ResponseEntity.ok(medicamentoService.buscarMedicamentosPorPrincipioAtivo(principioAtivo));
    }

    @GetMapping("/medicamento/{medicamento}/mesma-composicao")
    public ResponseEntity<List<Medicamento>> buscarMedicamentosComMesmaComposicao(@PathVariable String medicamento) {
        return ResponseEntity.ok(medicamentoService.buscarMedicamentosComMesmaComposicao(medicamento));
    }
}
