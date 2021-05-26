package tech.itpark.project_delivery_web.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Indexed
@NoArgsConstructor
@Table(name = "usr")
@ToString(of = {"id", "name", "surname", "email", "username", "status"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_generator")
    private Long id;

    @Field(termVector = TermVector.YES)
    private String name;
    @Field(termVector = TermVector.YES)
    private String surname;

    @NaturalId
    private String email;

    private String password;
    private Date creationDate;

    /**
     * @descr Путь к картинке (аватару) сотрудника.
     */
    private String fileName;

    private String country;

    private String city;

    /**
     * @descr Лист ролей сотрудника.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chief_id")
    private User chief;

    /**
     * @descr Уникальный номер календаря Юзера в сервисе Google calendar.
     */
    private String calendar;

    private String username;

    @Column(name = "changing_date")
    private Date changingDate;

    /**
     * @descr Лист комментариев о сотруднике.
     */
    @OneToMany(mappedBy = "owner", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    /**
     * @descr Лист оставленных сотрудником комментариев.
     */
    @OneToMany(mappedBy = "creator", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> creatorOfComments;

    /**
     * @descr Лист фидбэков о сотруднике.
     */
    @OneToMany(mappedBy = "owner", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Feedback> reports;

    /**
     * @descr Лист оставленных сотрудником фидбэков.
     */
    @OneToMany(mappedBy = "creator", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Feedback> creatorOfReports;


    /**
     * @descr Лист назначенных/проведенных 121 с этим сотрудником.
     */
    @OneToMany(mappedBy = "owner", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> tasks;

    /**
     * @descr Лист назначенных/проведенных 121 этим сотрудником.
     */
    @OneToMany(mappedBy = "creator", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> creatorOfTask;

    @OneToMany(mappedBy = "creator", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<News> news;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany()
    @JoinTable(name = "usr_activities",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "activity_id", referencedColumnName = "id")})
    private List<Activity> activities;
}
