package marcelo.HeroGarage.Carros;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import marcelo.HeroGarage.Personagem.PersonagemModel;


@Entity
@Table(name = "tb_carros")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarrosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String marca;
    private String modelo;
    private int ano;
    @ManyToOne(cascade = CascadeType.ALL ,fetch = FetchType.LAZY) // fetch lazy carrega dados quando são acessados
    @JoinColumn(name = "personagem_id") // cria uma chave estrangeira
    private PersonagemModel personagem;

}
