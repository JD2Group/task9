package org.example.lesson9.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "doors")
@NamedNativeQuery(name = "getBySize",
query = "select * from doors where size between :fromSize and :toSize",
resultClass = DoorDTO.class)
public class DoorDTO implements Serializable {
    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)    @Column(name = "id")    private int id;
    @Column(name = "size")
    private double size;
    @Column(name = "type")
    private String type;
}
