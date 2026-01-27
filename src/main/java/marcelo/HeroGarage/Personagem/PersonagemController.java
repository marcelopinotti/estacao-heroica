package marcelo.HeroGarage.Personagem;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/personagem")
public class PersonagemController {
    private PersonagemService personagemService;

    public PersonagemController(PersonagemService personagemService) {this.personagemService = personagemService;}

    // Adicionar personagens
    @PostMapping("/adicionar")
    public PersonagemDTO criarPersonagem(@RequestBody PersonagemDTO personagem){
        return personagemService.criarPersonagem(personagem);
    }

    // Mostrar personagem por id
    @GetMapping("/listar/{id}")
    public PersonagemDTO mostrarPersonagemPorId(@PathVariable Long id){return personagemService.mostrarPersonagemPorId(id);}

    // Listar todos os personagens
    @GetMapping("/listar")
    public List<PersonagemDTO> mostrarPersonagem(){
        return personagemService.mostrarPersonagem();
    }

    //Atualizar personagem
    @PutMapping("/atualizar/{id}")
    public PersonagemModel atualizarPersonagem(@PathVariable Long id, @RequestBody PersonagemModel personagem){
        return personagemService.atualizarPersonagem(personagem, id);
    }

    // Deletar personagem
    @DeleteMapping("/deletar/{id}")
    public void deletarPersonagem(@PathVariable Long id){
        personagemService.deletarPersonagem(id);
    }

}

