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

    public List<CarrosModel> mostrarCarros() {
        return carrosRepository.findAll();
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
    public void deletarCarros(Long id){
        carrosRepository.deleteById(id);
    }
}
