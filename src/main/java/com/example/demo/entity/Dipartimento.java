package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dipartimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dipartimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String nome;

    @Column(length = 15, unique = true)
    private String sigla;

    @OneToMany(mappedBy = "dipartimento")
    private Set<CorsoLaurea> corsiLaurea=new HashSet<>();
}
