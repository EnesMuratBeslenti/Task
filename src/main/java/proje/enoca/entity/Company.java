package proje.enoca.entity;

import jdk.jfr.DataAmount;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Builder
@Entity
@Table(name = "Company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @NotNull
    @Column(name = "name")
    @Size(min =  5 ,max = 15)
    private String companyName;
    @NotNull
    @Column(name = "employee_numbers")
    private Integer employeeNumbers;

    @Column(name = "avabile_empyoler")
    private Integer avabileEmployye;
    @Column(name = "budget")
    private Integer budget;
     @DataAmount
     @Column(name = "revenues")
    private Integer revenues;
    @DataAmount
    @Column(name = "expenses")
    private Integer expenses;












}
