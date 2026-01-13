package marcelo.HeroGarage.Carros;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "tb_carro")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarrosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "nome")
    private String nome;
    @NotBlank(message = "A marca é obrigatória.")
    @Column(nullable = false, name = "marca")
    private String marca;
    @NotBlank(message = "O modelo é obrigatório.")
    @Column(nullable = false, name = "modelo")
    private String modelo;
    @Column(nullable = false, name = "ano")
    @Min(value = 1886)
    @Max(value = 2030)
    private int ano;

}
