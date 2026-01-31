package marcelo.HeroGarage.Personagem;

import marcelo.HeroGarage.Carros.CarrosModel;
import marcelo.HeroGarage.Carros.CarrosRepository;
import marcelo.HeroGarage.exception.IllegalArgumentException;
import marcelo.HeroGarage.exception.PersonagemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonagemServiceTest {

    @Mock
    private PersonagemRepository personagemRepository;

    @Mock
    private PersonagemMapper personagemMapper;

    @Mock
    private CarrosRepository carrosRepository;

    @InjectMocks
    private PersonagemService personagemService;

    private PersonagemModel personagemModel;
    private PersonagemDTO personagemDTO;

    @BeforeEach
    void setUp() {
        personagemModel = PersonagemModel.builder()
                .id(1L)
                .nome("Batman")
                .desenho("DC")
                .idade(35)
                .genero("Masculino")
                .carros(new ArrayList<>())
                .build();

        personagemDTO = new PersonagemDTO(1L, "Batman", "DC", 35, "Masculino", new ArrayList<>());
    }

    @Test
    @DisplayName("Deve criar um personagem com sucesso")
    void deveCriarPersonagem() {
        when(personagemMapper.map(personagemDTO)).thenReturn(personagemModel);
        when(personagemRepository.save(personagemModel)).thenReturn(personagemModel);
        when(personagemMapper.map(personagemModel)).thenReturn(personagemDTO);

        PersonagemDTO result = personagemService.criar(personagemDTO);

        assertNotNull(result);
        assertEquals(personagemDTO.getNome(), result.getNome());
        verify(personagemRepository).save(personagemModel);
    }

    @Test
    @DisplayName("Deve criar lote de personagens com sucesso")
    void deveCriarLotePersonagens() {
        List<PersonagemDTO> dtos = List.of(personagemDTO);
        List<PersonagemModel> models = List.of(personagemModel);

        when(personagemMapper.map(any(PersonagemDTO.class))).thenReturn(personagemModel);
        when(personagemRepository.saveAll(anyList())).thenReturn(models);
        when(personagemMapper.map(any(PersonagemModel.class))).thenReturn(personagemDTO);

        List<PersonagemDTO> result = personagemService.criarLote(dtos);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(personagemRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("Deve listar todos os personagens")
    void deveListarTodosPersonagens() {
        when(personagemRepository.findAll()).thenReturn(List.of(personagemModel));
        when(personagemMapper.map(any(PersonagemModel.class))).thenReturn(personagemDTO);

        List<PersonagemDTO> result = personagemService.listarTodos();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deve buscar personagem por ID com sucesso")
    void deveBuscarPorId() {
        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagemModel));
        when(personagemMapper.map(personagemModel)).thenReturn(personagemDTO);

        PersonagemDTO result = personagemService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar ID inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {
        when(personagemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PersonagemNotFoundException.class, () -> personagemService.buscarPorId(1L));
    }

    @Test
    @DisplayName("Deve atualizar personagem com sucesso")
    void deveAtualizarPersonagem() {
        // 1. Preparamos os dados de entrada (o que queremos atualizar)
        PersonagemDTO dadosParaAtualizar = new PersonagemDTO(1L, "Bruce Wayne", "DC", 36, "Masculino", new ArrayList<>());
        
        // 2. Configuramos os Mocks (simulamos o comportamento das dependências)
        // Quando procurar pelo ID 1, retorna o personagem que já existe
        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagemModel));
        
        // Quando salvar qualquer personagem, retorna o modelo (simulação simples)
        when(personagemRepository.save(any(PersonagemModel.class))).thenReturn(personagemModel);
        
        // Quando converter o modelo para DTO, retorna os dados atualizados
        when(personagemMapper.map(any(PersonagemModel.class))).thenReturn(dadosParaAtualizar);

        // 3. Executamos o método que queremos testar
        PersonagemDTO resultado = personagemService.atualizar(dadosParaAtualizar, 1L);

        // 4. Verificamos se deu tudo certo (Asserções)
        assertNotNull(resultado);
        assertEquals("Bruce Wayne", resultado.getNome());
        
        // Verifica se o repositório tentou salvar as alterações
        verify(personagemRepository).save(any(PersonagemModel.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar com idade inválida")
    void deveLancarExcecaoIdadeInvalida() {
        PersonagemDTO updateDTO = new PersonagemDTO();
        updateDTO.setIdade(-1);

        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagemModel));

        assertThrows(IllegalArgumentException.class, () -> personagemService.atualizar(updateDTO, 1L));
    }

    @Test
    @DisplayName("Deve deletar personagem com sucesso")
    void deveDeletarPersonagem() {
        when(personagemRepository.existsById(1L)).thenReturn(true);
        doNothing().when(personagemRepository).deleteById(1L);

        assertDoesNotThrow(() -> personagemService.deletar(1L));
        verify(personagemRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar ID inexistente")
    void deveLancarExcecaoAoDeletarIdInexistente() {
        when(personagemRepository.existsById(1L)).thenReturn(false);

        assertThrows(PersonagemNotFoundException.class, () -> personagemService.deletar(1L));
        verify(personagemRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Deve atualizar carros relacionados")
    void deveAtualizarCarrosRelacionados() {
        CarrosModel carro = CarrosModel.builder().id(10L).nome("Batmobile").build();
        PersonagemDTO updateDTO = new PersonagemDTO();
        updateDTO.setCarros(List.of(carro));

        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagemModel));
        when(carrosRepository.findAllById(anyList())).thenReturn(List.of(carro));
        when(personagemRepository.save(any(PersonagemModel.class))).thenReturn(personagemModel);
        when(personagemMapper.map(any(PersonagemModel.class))).thenReturn(personagemDTO);

        personagemService.atualizar(updateDTO, 1L);

        verify(carrosRepository).findAllById(anyList());
        assertEquals(1, personagemModel.getCarros().size());
        assertEquals(personagemModel, carro.getPersonagem());
    }
}