package marcelo.HeroGarage.Personagem;


import marcelo.HeroGarage.Carros.CarrosModel;
import marcelo.HeroGarage.Carros.CarrosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.function.Consumer;

@Service
public class PersonagemService {
    private final PersonagemRepository personagemRepository;
    private final PersonagemMapper personagemMapper;
    private final CarrosRepository carrosRepository;

    public PersonagemService(PersonagemRepository personagemRepository, PersonagemMapper personagemMapper, CarrosRepository carrosRepository) {
        this.personagemRepository = personagemRepository;
        this.personagemMapper = personagemMapper;
        this.carrosRepository = carrosRepository;
    }

    public PersonagemDTO criarPersonagem(PersonagemDTO personagemDTO) {
        PersonagemModel personagem = personagemMapper.map(personagemDTO);
        personagem = personagemRepository.save(personagem);
        return personagemMapper.map(personagem);
    }


    public List<PersonagemDTO> criarAlgunsPersonagens(List<PersonagemDTO> personagens) {
        List<PersonagemModel> personagensModel = personagens.stream()
                .map(personagemMapper::map)
                .collect(Collectors.toList());
        return personagemRepository.saveAll(personagensModel).stream()
                .map(personagemMapper::map)
                .collect(Collectors.toList());
    }


    public List<PersonagemDTO> mostrarPersonagem(){
        List<PersonagemModel> personagens = personagemRepository.findAll();
        return personagens.stream()
                .map(personagemMapper::map)
                .collect(Collectors.toList());
    }


    public PersonagemDTO mostrarPersonagemPorId(Long id){
        Optional<PersonagemModel> personagemID = personagemRepository.findById(id);
        return personagemID.map(personagemMapper::map)
                .orElse(null);
    }

    public PersonagemDTO atualizarPersonagem(PersonagemDTO personagemDTO, Long id){
        Optional<PersonagemModel> personagemId = personagemRepository.findById(id);
        if (personagemId.isPresent()) {
            PersonagemModel personagemAtualizado = personagemId.get();
            atribuirNotNull(personagemDTO.getNome(), personagemAtualizado::setNome);
            atribuirNotNull(personagemDTO.getDesenho(), personagemAtualizado::setDesenho);
            atribuirNotNull(personagemDTO.getIdade(), personagemAtualizado::setIdade);
            atribuirNotNull(personagemDTO.getGenero(), personagemAtualizado::setGenero);
            if (personagemDTO.getCarros() != null) {
                List<Long> carrosIds = personagemDTO.getCarros().stream()
                        .map(CarrosModel::getId)
                        .filter(idCarro -> idCarro != null)
                        .collect(Collectors.toList());
                List<CarrosModel> carros = carrosRepository.findAllById(carrosIds);
                carros.forEach(carro -> carro.setPersonagem(personagemAtualizado));
                personagemAtualizado.setCarros(carros);
            }
            PersonagemModel personagemSalvo = personagemRepository.save(personagemAtualizado);
            return personagemMapper.map(personagemSalvo);
        }
        return null;
    }

    private static <T> void atribuirNotNull(T valor, Consumer<T> setter) {
        if (valor != null) {
            setter.accept(valor);
        }
    }

    public void deletarPersonagem(Long id){
        personagemRepository.deleteById(id);
    }

}
