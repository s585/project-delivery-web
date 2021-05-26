package tech.itpark.project_delivery_web.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "user_authentication_details")
public class UserAuthenticationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = {CascadeType.ALL})
    private User user;

    @Column(name = "count_of_failed_attempts")
    private int failedAttemptCount;

    @Column(name = "lock_time")
    private Date lockTime;

    @Column(name = "account_locked")
    private boolean accountLocked;
    
    public UserAuthenticationDetails(User user, Integer failedAttemptCount) {
        this.user = user;
        this.failedAttemptCount = failedAttemptCount;
    }
}
