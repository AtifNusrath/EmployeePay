package com.bridgelabz.demo.Service;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.bridgelabz.demo.dto.EmployeePayrollDTO;
import com.bridgelabz.demo.model.EmployeePayrollData;
import com.bridgelabz.demo.Exceptions.EmployeePayrollException;
import org.springframework.beans.factory.annotation.Autowired;
import com.bridgelabz.demo.repository.EmployeePayrollRepository;
import com.bridgelabz.demo.util.TokenUtil;

@Service
@Slf4j
public class EmployeePayrollService implements IEmployeePayrollService {

	@Autowired
    private EmployeePayrollRepository employeePayrollRepository;
	
	 @Autowired
	   TokenUtil tokenUtil;
	
	@Override
	public List<EmployeePayrollData> getEmployeePayrollData() {
		return employeePayrollRepository.findAll();
	}

	@Override
	public EmployeePayrollData getEmployeePayrollDataById(String token) {
		
		return employeePayrollRepository.findById(tokenUtil.decodeToken(token))
                .orElseThrow(() -> new EmployeePayrollException("Employee With employeeId: " + tokenUtil.decodeToken(token) + " does not exists"));
	}

	@Override
	public EmployeePayrollData createEmployeePayrollData(@RequestBody EmployeePayrollDTO empPayrollDTO) {
		EmployeePayrollData empData = new EmployeePayrollData(empPayrollDTO);
        return employeePayrollRepository.save(empData);
	}

	@Override
	public EmployeePayrollData updateEmployeePayrollData(@RequestHeader String token,@RequestBody EmployeePayrollDTO empPayrollDTO) {
		EmployeePayrollData empData = this.getEmployeePayrollDataById(token);
		empData.updateEmployeePayollData(empPayrollDTO);
        return employeePayrollRepository.save(empData);
	}

	@Override
	public void deleteEmployeePayrollData(String token) {
		 EmployeePayrollData empData = this.getEmployeePayrollDataById(token);
	        employeePayrollRepository.delete(empData);
	}

	@Override
    public List<EmployeePayrollData> getEmployeesPayrollDataByDepartment(String department) {
        return employeePayrollRepository.findEmployeesByDepartment(department);
    }
	
	 @Override
	    public List<EmployeePayrollData> getEmployeesPayrollDataByGender(String gender) {
	        return employeePayrollRepository.findEmployeesByGender(gender);
	    }

	    @Override
	    public String deleteallEmployeePayrollData() {
	        employeePayrollRepository.deleteAll();
	        return "All Data Delete";
	    }

	    @Override
	    public List<EmployeePayrollData> getAllEmployeePayrollData(String token) {
	        
	        Optional<EmployeePayrollData> empData = employeePayrollRepository.findById(tokenUtil.decodeToken(token));
	        if (empData.isPresent()) {
	            List<EmployeePayrollData> employeePayrollDataList = employeePayrollRepository.findAll();
	            return employeePayrollDataList;
	        }
	        return null;
	    }

	    @Override
	    public Optional<EmployeePayrollData> getupdateEmployeePayrollData(String token) {
	       
	        Optional<EmployeePayrollData> empData = employeePayrollRepository.findById(tokenUtil.decodeToken(token));
	        if (empData.isPresent()) {
	            Optional<EmployeePayrollData> employeePayrollUpdateData = employeePayrollRepository.findById(tokenUtil.decodeToken(token));
	            return employeePayrollUpdateData;
	        }
	        return null;
	    }

	    @Override
	    public String deleteAllEmployeePayrollData() {
	        employeePayrollRepository.deleteAll();
	        return "All Data Delete";
	    }

}
