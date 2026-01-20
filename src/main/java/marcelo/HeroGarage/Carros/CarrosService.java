package marcelo.HeroGarage.Carros;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarrosService {

    private final CarrosRepository carrosRepository;

    public List<CarrosModel> mostrarCarros(){
        return carrosRepository.findAll();
    }
    public CarrosModel mostrarCarrosPorId(Long id){
        Optional<CarrosModel> carrosID = carrosRepository.findById(id);
        return carrosID.orElse(null);
    }
}
