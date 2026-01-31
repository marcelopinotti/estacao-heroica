package marcelo.HeroGarage.Carros;

import marcelo.HeroGarage.exception.CarroNotFoundException;
import marcelo.HeroGarage.exception.IllegalArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarrosServiceTest {

    @Mock
    private CarrosRepository carrosRepository;

    @Mock
    private CarrosMapper carrosMapper;

    @InjectMocks
    private CarrosService carrosService;

    private CarrosModel carroModel;
    private CarrosDTO carroDTO;

    @BeforeEach
    void setUp() {
        carroModel = CarrosModel.builder()
                .id(1L)
                .nome("Batmobile")
                .marca("Wayne Tech")
                .modelo("Tumbler")
                .ano(2022)
                .cor("Preto")
                .cambio("Automático")
                .build();

        carroDTO = new CarrosDTO(1L, "Batmobile", "Wayne Tech", "Tumbler", 2022, null, "Automático", "Preto");
    }

    @Test
    @DisplayName("Deve criar um carro com sucesso")
    void deveCriarCarro() {
        when(carrosMapper.map(carroDTO)).thenReturn(carroModel);
        when(carrosRepository.save(carroModel)).thenReturn(carroModel);
        when(carrosMapper.map(carroModel)).thenReturn(carroDTO);

        CarrosDTO result = carrosService.criar(carroDTO);

        assertNotNull(result);
        assertEquals(carroDTO.getNome(), result.getNome());
        verify(carrosRepository).save(carroModel);
    }

    @Test
    @DisplayName("Deve criar lote de carros com sucesso")
    void deveCriarLoteCarros() {
        List<CarrosDTO> dtos = List.of(carroDTO);
        List<CarrosModel> models = List.of(carroModel);

        when(carrosMapper.map(any(CarrosDTO.class))).thenReturn(carroModel);
        when(carrosRepository.saveAll(anyList())).thenReturn(models);
        when(carrosMapper.map(any(CarrosModel.class))).thenReturn(carroDTO);

        List<CarrosDTO> result = carrosService.criarLote(dtos);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(carrosRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("Deve listar todos os carros")
    void deveListarTodosCarros() {
        when(carrosRepository.findAll()).thenReturn(List.of(carroModel));
        when(carrosMapper.map(any(CarrosModel.class))).thenReturn(carroDTO);

        List<CarrosDTO> result = carrosService.listarTodos();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deve buscar carro por ID com sucesso")
    void deveBuscarPorId() {
        when(carrosRepository.findById(1L)).thenReturn(Optional.of(carroModel));
        when(carrosMapper.map(carroModel)).thenReturn(carroDTO);

        CarrosDTO result = carrosService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar ID inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {
        when(carrosRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CarroNotFoundException.class, () -> carrosService.buscarPorId(1L));
    }

    @Test
    @DisplayName("Deve atualizar carro com sucesso")
    void deveAtualizarCarro() {
        // 1. Dados que queremos atualizar
        CarrosDTO dadosParaAtualizar = new CarrosDTO(1L, "Novo Batmobile", "Wayne Tech", "Tumbler", 2023, null, "Automático", "Preto");

        // 2. Mocks (simulações)
        when(carrosRepository.findById(1L)).thenReturn(Optional.of(carroModel));
        when(carrosRepository.save(any(CarrosModel.class))).thenReturn(carroModel);
        when(carrosMapper.map(any(CarrosModel.class))).thenReturn(dadosParaAtualizar);

        // 3. Execução
        CarrosDTO resultado = carrosService.atualizar(dadosParaAtualizar, 1L);

        // 4. Verificações
        assertNotNull(resultado);
        assertEquals("Novo Batmobile", resultado.getNome());
        assertEquals(2023, resultado.getAno());
        verify(carrosRepository).save(any(CarrosModel.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar com ano inválido")
    void deveLancarExcecaoAnoInvalido() {
        CarrosDTO updateDTO = new CarrosDTO();
        updateDTO.setAno(-1);

        when(carrosRepository.findById(1L)).thenReturn(Optional.of(carroModel));

        assertThrows(IllegalArgumentException.class, () -> carrosService.atualizar(updateDTO, 1L));
    }

    @Test
    @DisplayName("Deve deletar carro com sucesso")
    void deveDeletarCarro() {
        when(carrosRepository.existsById(1L)).thenReturn(true);
        doNothing().when(carrosRepository).deleteById(1L);

        assertDoesNotThrow(() -> carrosService.deletar(1L));
        verify(carrosRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar ID inexistente")
    void deveLancarExcecaoAoDeletarIdInexistente() {
        when(carrosRepository.existsById(1L)).thenReturn(false);

        assertThrows(CarroNotFoundException.class, () -> carrosService.deletar(1L));
        verify(carrosRepository, never()).deleteById(anyLong());
    }
}
