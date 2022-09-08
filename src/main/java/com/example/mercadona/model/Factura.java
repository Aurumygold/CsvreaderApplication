package com.example.mercadona.model;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Factura")
public class Factura {

    @Id
    @Column(unique = true)
    private Integer identificador;
    @Column(unique = true, length = 8)
    private Integer identificadorlegacy;
    private String nombre;
    private String fecha;
    private String importe;


}
