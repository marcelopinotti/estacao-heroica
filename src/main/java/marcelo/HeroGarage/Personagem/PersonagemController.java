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

import java.util.List;

@RestController
@RequestMapping("/personagem")
public class PersonagemController {
    private final PersonagemService personagemService;

    public PersonagemController(PersonagemService personagemService) {
        this.personagemService = personagemService;
    }

    @PostMapping("/adicionar")
    public ResponseEntity<String> criarPersonagem(@RequestBody PersonagemDTO personagem) {
        PersonagemDTO personagemDTO = personagemService.criarPersonagem(personagem);
        return ResponseEntity.status(HttpStatus.CREATED).body("Personagem criado com sucesso! ID: " + personagemDTO.getId());
    }

    @PostMapping("/adicionar-varios")
    public ResponseEntity<?> criarPersonagem(@RequestBody List<PersonagemDTO> personagem) {
        List<PersonagemDTO> personagemDTO = personagemService.criarAlgunsPersonagens(personagem);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Personagens adicionados com sucesso! IDs deles: " +
                        personagemDTO.stream()
                                .map(PersonagemDTO::getId)
                                .toList());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> mostrarPersonagemPorId(@PathVariable Long id) {
        PersonagemDTO personagem = personagemService.mostrarPersonagemPorId(id);
        if (personagem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personagem não encontrado com o id: " + id);
        }
        return ResponseEntity.ok(personagem);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PersonagemDTO>> mostrarPersonagem() {
        return ResponseEntity.ok(personagemService.mostrarPersonagem());
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarPersonagem(@PathVariable Long id, @RequestBody PersonagemDTO personagem) {
        if (personagemService.mostrarPersonagemPorId(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personagem não encontrado com o id: " + id);
        }
        return ResponseEntity.ok("Personagem atualizado com sucesso! Id: " + personagemService.atualizarPersonagem(personagem, id).getId());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarPersonagem(@PathVariable Long id) {
        if (personagemService.mostrarPersonagemPorId(id) != null) {
            personagemService.deletarPersonagem(id);
            return ResponseEntity.ok("Personagem deletado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personagem não encontrado.");
    }
}