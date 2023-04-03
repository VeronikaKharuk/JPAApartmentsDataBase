package acad.prog.apartments.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Data
@Getter
@Setter
@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "area_metres", nullable = false)
    private Integer area;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @Column(name = "price_usd", nullable = false)
    private Integer priceUSD;


}