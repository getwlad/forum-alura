package com.wladmirrodrigues.forumalura.domain.curso;

import com.wladmirrodrigues.forumalura.curso.DadosCadastroCurso;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Curso")
@Table(name = "cursos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Curso(DadosCadastroCurso dados) {
        this.nome = dados.nome();
    }
}
