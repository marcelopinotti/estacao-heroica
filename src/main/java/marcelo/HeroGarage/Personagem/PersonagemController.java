package marcelo.HeroGarage.Personagem;

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
@RequestMapping("/personagens")
public class PersonagemController {
    private final PersonagemService personagemService;

    public PersonagemController(PersonagemService personagemService) {
        this.personagemService = personagemService;
    }

    @PostMapping("/lote")
    public ResponseEntity<String> criarLote(@RequestBody List<PersonagemDTO> personagens) {
        List<PersonagemDTO> personagensCriados = personagemService.criarLote(personagens);
        List<Long> ids = personagensCriados.stream()
                .map(PersonagemDTO::getId)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Personagens adicionados com sucesso! IDs: " + ids);
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criar(@RequestBody PersonagemDTO personagem) {
        PersonagemDTO personagemCriado = personagemService.criar(personagem);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Personagem criado com sucesso! ID: " + personagemCriado.getId());
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PersonagemDTO>> listarTodos() {
        return ResponseEntity.ok(personagemService.listarTodos());
    }

    @GetMapping("listar/{id}")
    public ResponseEntity<PersonagemDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(personagemService.buscarPorId(id));
    }

    @PatchMapping("atualizar/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody PersonagemDTO personagem) {
        PersonagemDTO personagemAtualizado = personagemService.atualizar(personagem, id);
        return ResponseEntity.ok("Personagem atualizado com sucesso! ID: " + personagemAtualizado.getId());
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        personagemService.deletar(id);
        return ResponseEntity.ok("Personagem deletado com sucesso!");
    }
}

