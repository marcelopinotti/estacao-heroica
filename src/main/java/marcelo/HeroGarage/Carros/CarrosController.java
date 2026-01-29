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

    @PostMapping("/adicionar-varios")
    public ResponseEntity<String> criarCarros(@RequestBody List<CarrosDTO> carros) {
        List<CarrosDTO> carrosDTO = carrosService.criarAlgunsCarros(carros);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Carros adicionados com sucesso! IDs deles: " +
                        carrosDTO.stream()
                                .map(CarrosDTO::getId)
                                .toList());
    }

    @PostMapping("/adicionar")
    public ResponseEntity<String> criarCarros(@RequestBody CarrosDTO carros){
        CarrosDTO carrosDTO = carrosService.criarCarros(carros);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Carro criado com sucesso! ID: " + carrosDTO.getId());}

    @GetMapping("/listar")
    public ResponseEntity<List<CarrosDTO>>  mostrarCarros(){
        List<CarrosDTO> carros = carrosService.mostrarCarros();
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> mostrarCarrosPorId(@PathVariable Long id) {
        CarrosDTO carro = carrosService.mostrarCarrosPorId(id);
        if (carro == null) {
            throw new CarroNotFoundException("Carro não encontrado com o id: " + id);
        }
        return ResponseEntity.ok(carro);
    }

    @PatchMapping("atualizar/{id}")
    public ResponseEntity<?> atualizarCarros(@PathVariable Long id, @RequestBody CarrosDTO carros){
        return ResponseEntity.ok("Carro atualizado com sucesso! ID: " + carrosService.atualizarCarros(carros, id).getId());
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<String> deletarCarros(@PathVariable Long id){
        if(carrosService.mostrarCarrosPorId(id) != null) {
            carrosService.deletarCarros(id);
            return ResponseEntity.ok("Carro deletado com sucesso!");
        } throw new CarroNotFoundException("Carro não encontrado.");
    }
}
