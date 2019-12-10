package tacos;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Taco {

    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;

    @Size(min=1, message="You must choose at least 1 ingredient")
    @NotNull(message = "You must choose at least 1 ingredient")
    @ManyToMany(targetEntity = Ingredient.class)
    @JoinTable(name = "Taco_Ingredients",
    joinColumns = @JoinColumn(name = "taco"),
    inverseJoinColumns = @JoinColumn(name="ingredient"))
    private List<Ingredient> ingredients;
    @Id
    @GeneratedValue
    private Long id;
    @Column(name="createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    void createdAt()
    {
        this.createdAt = LocalDateTime.now();
    }

}
