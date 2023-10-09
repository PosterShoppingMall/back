package adultdinosaurdooley.threesixnine.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshTokenEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;


    @Column(nullable = false)
    private String token;

    public RefreshTokenEntity updateToken(String token){
        this.token = token;
        return this;
    }
    @Builder
    public RefreshTokenEntity(UserEntity userEntity, String token){
        this.userEntity = userEntity;
        this.token = token;
    }

}
