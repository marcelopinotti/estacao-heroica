package marcelo.HeroGarage.Carros;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carros")
@RequiredArgsConstructor
public class CarrosController {

    private final CarrosService carrosService;

    @PostMapping("/adicionarCarro")
    public String criarCarros(){
        return "Carros criado com sucesso";
    }

    @GetMapping("/listar")
    public List<CarrosModel> mostrarCarros(){
        return carrosService.mostrarCarros();
    }

    @GetMapping("/listar/{id}")
    public CarrosModel mostrarCarrosPorId(@PathVariable Long id){
        return carrosService.mostrarCarrosPorId(id);
    }

    @PutMapping("atualizarCarro")
    public String atualizarCarros(){
        return "Carros atualizado com sucesso";
    }

    @DeleteMapping("deletarCarrosid")
    public String deletarCarrosPorID(){
        return "Carros deletado com sucesso";
    }
}
