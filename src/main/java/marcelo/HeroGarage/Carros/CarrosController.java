package marcelo.HeroGarage.Carros;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import marcelo.HeroGarage.exception.*;
import java.util.List;

@RestController
@RequestMapping("/carros")
public class CarrosController {

    private final CarrosService carrosService;

    public CarrosController(CarrosService carrosService) {
        this.carrosService = carrosService;
    }

    @PostMapping("/lote")
    public ResponseEntity<String> criarLote(@RequestBody List<CarrosDTO> carros) {
        List<CarrosDTO> carrosCriados = carrosService.criarLote(carros);
        List<Long> ids = carrosCriados.stream().map(CarrosDTO::getId).toList();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Carros adicionados com sucesso! IDs: " + ids);
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criar(@RequestBody CarrosDTO carro) {
        CarrosDTO carroCriado = carrosService.criar(carro);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Carro criado com sucesso! ID: " + carroCriado.getId());
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CarrosDTO>> listarTodos() {
        return ResponseEntity.ok(carrosService.listarTodos());
    }

    @GetMapping("listar/{id}")
    public ResponseEntity<CarrosDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(carrosService.buscarPorId(id));
    }

    @PatchMapping("atualizar/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody CarrosDTO carro) {
        CarrosDTO carroAtualizado = carrosService.atualizar(carro, id);
        return ResponseEntity.ok("Carro atualizado com sucesso! ID: " + carroAtualizado.getId());
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        carrosService.deletar(id);
        return ResponseEntity.ok("Carro deletado com sucesso!");
    }
}
