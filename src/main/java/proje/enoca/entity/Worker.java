package proje.enoca.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Builder
@Entity
@Table(name = "Worker")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "worker_id", nullable = false)
    private Long workerId;

    @NotNull
    @Column(name ="first_name")

    private String firstName;

    @Column(name ="last_name")
    private String lastName;
    @Column(name ="departman")
    private String departman;
    @Column(name ="age")
    private Integer age;

    @Column(name = "salary")
    private Integer salary;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;




}
