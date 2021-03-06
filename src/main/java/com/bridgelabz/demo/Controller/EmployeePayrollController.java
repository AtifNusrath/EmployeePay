package com.bridgelabz.demo.Controller;

import com.bridgelabz.demo.dto.EmployeePayrollDTO;
import com.bridgelabz.demo.dto.ResponseDTO;
import com.bridgelabz.demo.model.EmployeePayrollData;
import com.bridgelabz.demo.util.TokenUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.demo.Exceptions.EmployeePayrollException;
import com.bridgelabz.demo.Service.IEmployeePayrollService;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;

@RestController
@RequestMapping("/employeepayrollservice")
@Slf4j
public class EmployeePayrollController {
	@Autowired
	private IEmployeePayrollService employeePayrollService;
	
	 @Autowired
	    private TokenUtil tokenUtil;

//	@RequestMapping(value = { "", "/", "get" })
//	public ResponseEntity<ResponseDTO> getEmployeePayrollData() {
//		List<EmployeePayrollData> empDataList = null;
//		empDataList = employeePayrollService.getEmployeePayrollData();
//		ResponseDTO respDTO = new ResponseDTO("Get Call Success", empDataList);
//		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
//	}

	@GetMapping("/get/all")
    public List<EmployeePayrollData> getEmployeePayrollData() {
        List<EmployeePayrollData> employeesDataList = employeePayrollService.getEmployeePayrollData();
        return employeesDataList;
    }
	
	@GetMapping("/get")
	public ResponseEntity<ResponseDTO> getEmployeePayrollDataById(@RequestHeader String token) {
		EmployeePayrollData payrollData = employeePayrollService.getEmployeePayrollDataById(token);
		ResponseDTO respDTO = new ResponseDTO("Get Call Success for id:", payrollData);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseDTO> createEmployeePayrollData(
			@Valid @RequestBody EmployeePayrollDTO employeePayrollDTO) {
		log.debug("Employee DTO" + employeePayrollDTO.toString());
		EmployeePayrollData payrollData = employeePayrollService.createEmployeePayrollData(employeePayrollDTO);
		System.out.println(payrollData);

		ResponseDTO respDTO = new ResponseDTO("Created Employee payroll data for:",tokenUtil.createToken(payrollData.getEmployeeId()));
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDTO> updateEmployeePayrollData(@RequestHeader String token,
			@Valid @RequestBody EmployeePayrollDTO employeePayrollDTO) {
		EmployeePayrollData payrollData = employeePayrollService.updateEmployeePayrollData(token, employeePayrollDTO);
		ResponseDTO respDTO = new ResponseDTO("Updated Employee payroll Data for: ", payrollData);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDTO> deleteEmployeePayrollData(@RequestHeader String token) {
		employeePayrollService.deleteEmployeePayrollData(token);
		ResponseDTO respDTO = new ResponseDTO("Delete Call Success for id: ", "employeeId " + tokenUtil.decodeToken(token));
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}
	
	 @GetMapping("/department/{department}")
	    public ResponseEntity<ResponseDTO> getEmployeeByDepartment(@PathVariable String department) {

	        List<EmployeePayrollData> employeeList = null;
	        employeeList = employeePayrollService.getEmployeesPayrollDataByDepartment(department);
	        ResponseDTO response = new ResponseDTO("Get Call for Department Successful", employeeList);
	        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	}
	 
	 @GetMapping("/department/{gender}")
	    public ResponseEntity<ResponseDTO> getEmployeeByGender(@PathVariable String gender) {
	        List<EmployeePayrollData> employeeList = null;
	        employeeList = employeePayrollService.getEmployeesPayrollDataByGender(gender);
	        ResponseDTO response = new ResponseDTO("Get Call for gender Successful", employeeList);
	        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	    }


	    @GetMapping("/readdata")
	    public ResponseEntity<ResponseDTO> readdata(@RequestHeader(name = "token") String token) throws EmployeePayrollException {
	        List<EmployeePayrollData> employeeList = employeePayrollService.getAllEmployeePayrollData(token);
	        if (employeeList.size() > 0) {
	            ResponseDTO responseDTO = new ResponseDTO("all user Fetched successfully", employeeList);
	            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	        } else {
	            throw new EmployeePayrollException("No Data Found");
	        }

	    }

	    @GetMapping("/readupdatedata")
	    public ResponseEntity<ResponseDTO> readupdatedata(@RequestHeader(name = "token") String token) throws EmployeePayrollException {
	        Optional<EmployeePayrollData> employeeList = null;
	        employeeList = employeePayrollService.getupdateEmployeePayrollData(token);

	        ResponseDTO responseDTO = new ResponseDTO("Updated data", employeeList);
	        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

	    }

	    @DeleteMapping("/deleteall")
	    public ResponseEntity<ResponseDTO> deleteAllEmployeePayrollData() {
	        String empData = employeePayrollService.deleteAllEmployeePayrollData();
	        ResponseDTO respDTO = new ResponseDTO("Deleted Successful,Deleted Id:", empData);
	        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	    }
}