package org.example.lesson9.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "houses")
public class HouseDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "size")
    private double size;
    @Column(name = "color")
    private String color;
    @Column(name = "room_count")
    private int roomCount;
}
