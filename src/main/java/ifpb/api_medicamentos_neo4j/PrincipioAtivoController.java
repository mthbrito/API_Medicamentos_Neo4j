package ifpb.api_medicamentos_neo4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/principiosAtivos")
public class PrincipioAtivoController {

    private final PrincipioAtivoService principioAtivoService;

    public PrincipioAtivoController(PrincipioAtivoService principioAtivoService) {
        this.principioAtivoService = principioAtivoService;
    }

    @GetMapping
    public ResponseEntity<PrincipioAtivo> buscarPrincipioAtivoPorId(@RequestParam String id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(principioAtivoService.buscarPrincipioAtivoPorId(id));
    }
}
