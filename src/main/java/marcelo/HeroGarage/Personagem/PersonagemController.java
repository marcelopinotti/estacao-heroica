package marcelo.HeroGarage.Personagem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personagem")
public class PersonagemController {
    private PersonagemService PersonagemService;

    public PersonagemController(PersonagemService personagemService) {
        PersonagemService = personagemService;
    }

    @GetMapping("/criar")
    private criarPersonagem() {
        PersonagemService.criarPersonagem();
    }


}

