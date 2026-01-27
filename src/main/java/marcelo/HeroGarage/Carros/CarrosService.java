package marcelo.HeroGarage.Carros;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.function.Consumer;

@Service
public class CarrosService {

    private CarrosRepository carrosRepository;
    private CarrosMapper carrosMapper;

    public CarrosService(CarrosRepository carrosRepository, CarrosMapper carrosMapper) {
        this.carrosRepository = carrosRepository;
        this.carrosMapper = carrosMapper;
    }

    public CarrosDTO criarCarros(CarrosDTO carrosDTO) {
        CarrosModel carros = carrosMapper.map(carrosDTO);
        carros = carrosRepository.save(carros);
        return carrosMapper.map(carros);
    }

    public List<CarrosDTO> criarAlgunsCarros(List<CarrosDTO> carros) {
        List<CarrosModel> carrosModel = carros.stream()
                .map(carrosMapper::map)
                .collect(Collectors.toList());
        return carrosRepository.saveAll(carrosModel).stream()
                .map(carrosMapper::map)
                .collect(Collectors.toList());
    }

    public List<CarrosDTO> mostrarCarros() {
        List<CarrosModel> carros = carrosRepository.findAll();
        return carros.stream()
                .map(carrosMapper::map)
                .collect(Collectors.toList());
    }

    public CarrosDTO mostrarCarrosPorId(Long id) {
        Optional<CarrosModel> carrosID = carrosRepository.findById(id);
        return carrosID.map(carrosMapper::map)
                .orElse(null);
    }

    public CarrosDTO atualizarCarros(CarrosDTO carrosDTO, Long id){
        Optional<CarrosModel> carrosId = carrosRepository.findById(id);
        if (carrosId.isPresent()) {
            CarrosModel carrosAtualizado = carrosId.get();
            atribuirNotNull(carrosDTO.getNome(), carrosAtualizado::setNome);
            atribuirNotNull(carrosDTO.getMarca(), carrosAtualizado::setMarca);
            atribuirNotNull(carrosDTO.getModelo(), carrosAtualizado::setModelo);
            atribuirNotNull(carrosDTO.getAno(), carrosAtualizado::setAno);
            atribuirNotNull(carrosDTO.getPersonagem(), carrosAtualizado::setPersonagem);
            atribuirNotNull(carrosDTO.getCambio(), carrosAtualizado::setCambio);
            atribuirNotNull(carrosDTO.getCor(), carrosAtualizado::setCor);
            CarrosModel carrosSalvo = carrosRepository.save(carrosAtualizado);
            return carrosMapper.map(carrosSalvo);
        }
        return null;
    }

    private static <T> void atribuirNotNull(T valor, Consumer<T> setter) {
        if (valor != null) {
            setter.accept(valor);
        }
    }


    public void deletarCarros(Long id){
        carrosRepository.deleteById(id);
    }
}
