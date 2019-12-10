package tacos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name="Taco_Order")
public class Order implements Serializable {

    @NotBlank(message = "Name is required")
    @Column(name = "deliveryName")
    private String name;

    @Column(name = "deliveryStreet")
    @NotBlank(message = "Street is required")
    private String street;

    @Column(name = "deliveryCity")
    @NotBlank(message = "City is required")
    private String city;
    @Column(name = "deliveryState")
    @NotBlank(message = "State is required")
    private String state;

    @Column(name = "deliveryZip")
    @NotBlank(message = "Zip code is required")
    private String zip;

    //@CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([/])([1-9][0-9])$",
            message="Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3,fraction = 0,message = "Invalid CVV")
    private String ccCVV;

    @Id
    @GeneratedValue
    private Long id;


    @Column(name = "placedAt")
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(name="Taco_Order_Tacos",
    joinColumns = @JoinColumn(name="tacoOrder"),
    inverseJoinColumns = @JoinColumn(name="taco"))
    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco design)
    {
        this.tacos.add(design);
    }

    @PrePersist
    void createdAt()
    {
        this.createdAt = LocalDateTime.now();
    }

}
