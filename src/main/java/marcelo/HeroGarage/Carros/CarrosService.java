package marcelo.HeroGarage.Carros;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarrosService {

    private CarrosRepository carrosRepository;

    public CarrosService(CarrosRepository carrosRepository) {this.carrosRepository = carrosRepository;}

    public CarrosModel criarCarros(CarrosModel carros) {
        return carrosRepository.save(carros);
    }

    public List<CarrosModel> mostrarCarros() {
        return carrosRepository.findAll();
    }

    public CarrosModel mostrarCarrosPorId(Long id) {
        Optional<CarrosModel> carrosID = carrosRepository.findById(id);
        return carrosID.orElse(null);
    }
}
