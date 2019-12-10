package tacos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
@Entity
public class Ingredient {
    @Id
    private final String id;
    private final String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private final Type type;

    public enum Type
    {
        WRAP,PROTEIN,VEGGIES,CHEESE,SAUCE;
    }
}
