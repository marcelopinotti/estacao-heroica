package marcelo.HeroGarage.Personagem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import marcelo.HeroGarage.Carros.CarrosModel;

import java.util.List;

@Entity
@Table(name = "tb_personagens")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PersonagemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String desenho;
    private int idade;
    private String genero;
    @OneToMany(mappedBy = "personagem",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarrosModel> carros;
}
