package ifpb.api_medicamentos_neo4j.controller;

import ifpb.api_medicamentos_neo4j.DTO.*;
import ifpb.api_medicamentos_neo4j.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    @Autowired
    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @PostMapping
    public ResponseEntity<MedicamentoComComposicaoResponseDTO> salvarMedicamento(@RequestBody MedicamentoCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoService.salvarMedicamento(dto));
    }

    @GetMapping
    public ResponseEntity<List<MedicamentoResponseDTO>> buscarMedicamentos(){
        return ResponseEntity.ok(medicamentoService.buscarMedicamentos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> buscarMedicamentoPorId(@PathVariable String id){
        return ResponseEntity.ok(medicamentoService.buscarMedicamentoPorId(id));
    }

    @GetMapping("/medicamento/{nome}")
    public ResponseEntity<MedicamentoComComposicaoResponseDTO> buscarMedicamentoPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(medicamentoService.buscarMedicamentoPorNome(nome));
    }

    @GetMapping("/principio-ativo/{principioAtivo}")
    public ResponseEntity<List<MedicamentoResponseDTO>> buscarMedicamentosPorPrincipioAtivo(@PathVariable String principioAtivo) {
        return ResponseEntity.ok(medicamentoService.buscarMedicamentosPorPrincipioAtivo(principioAtivo));
    }

    @GetMapping("/medicamento/{medicamento}/mesma-composicao")
    public ResponseEntity<MedicamentosMesmaComposicaoResponseDTO> buscarMedicamentosComMesmaComposicao(@PathVariable String medicamento) {
        return ResponseEntity.ok(medicamentoService.buscarMedicamentosComMesmaComposicao(medicamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoComComposicaoResponseDTO> atualizarMedicamento(@PathVariable String id, @RequestBody MedicamentoUpdateDTO dto) {
        return ResponseEntity.ok(medicamentoService.atualizarMedicamento(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMedicamento(@PathVariable String id) {
        medicamentoService.deletarMedicamento(id);
        return ResponseEntity.noContent().build();
    }
}
