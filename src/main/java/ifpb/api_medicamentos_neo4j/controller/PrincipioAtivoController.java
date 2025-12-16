package ifpb.api_medicamentos_neo4j.controller;

import ifpb.api_medicamentos_neo4j.DTO.PrincipioAtivoUpdateDTO;
import ifpb.api_medicamentos_neo4j.entity.PrincipioAtivo;
import ifpb.api_medicamentos_neo4j.service.PrincipioAtivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/principios-ativos")
public class PrincipioAtivoController {

    private final PrincipioAtivoService principioAtivoService;

    @Autowired
    public PrincipioAtivoController(PrincipioAtivoService principioAtivoService) {
        this.principioAtivoService = principioAtivoService;
    }

    @GetMapping
    public ResponseEntity<List<PrincipioAtivo>> buscarPrincipiosAtivos(){
        return ResponseEntity.ok(principioAtivoService.buscarPrincipiosAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrincipioAtivo> buscarPrincipioAtivoPorId(@PathVariable String id) {
        return ResponseEntity.ok(principioAtivoService.buscarPrincipioAtivoPorId(id));
    }

    @GetMapping("/principio-ativo/{nome}")
    public ResponseEntity<PrincipioAtivo> buscarPrincipioAtivoPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(principioAtivoService.buscarPrincipioAtivoPorNome(nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrincipioAtivo> atualizarPrincipioAtivo(@PathVariable String id, @RequestBody PrincipioAtivoUpdateDTO dto) {
        return ResponseEntity.ok(principioAtivoService.atualizarPrincipioAtivo(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        principioAtivoService.deletarPrincipioAtivo(id);
        return ResponseEntity.noContent().build();
    }
}
