package com.bridgelabz.demo.model;

import com.bridgelabz.demo.dto.EmployeePayrollDTO;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "employeepayroll_db")
public @Data class EmployeePayrollData {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private int employeeId;
    @Column(name = "name")
    private String name;
    @Column(name = "salary")
    private long salary;
    @Column(name = "gender")
    private String gender;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "note")
    private String note;
    @Column(name = "profile_pic")
    private String profilePic;

    @ElementCollection
    @CollectionTable(name = "employee_department",
            joinColumns = @JoinColumn(name ="id"))
    @Column(name = "departments")
    private List<String> departments;

	public EmployeePayrollData() {

	}

	public EmployeePayrollData(EmployeePayrollDTO employeePayrollDTO){
        this.updateEmployeePayollData(employeePayrollDTO);
    }
	
	public EmployeePayrollData(int employeeId, EmployeePayrollDTO employeePayrollDTO) {
		this.employeeId = employeeId;
		this.name = employeePayrollDTO.name;
		this.salary = employeePayrollDTO.salary;
		this.gender = employeePayrollDTO.gender;
		this.startDate = employeePayrollDTO.startDate;
		this.note = employeePayrollDTO.note;
		this.profilePic = employeePayrollDTO.profilePic;
		this.departments = employeePayrollDTO.departments;
	}

}
