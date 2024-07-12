package com.xworkz.service;

import com.xworkz.dto.DepartmentAdminDTO;
import com.xworkz.dto.DepartmentDTO;
import com.xworkz.dto.EmployeeDTO;
import com.xworkz.dto.UserDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.repository.DepartmentRepository;
import com.xworkz.repository.EmployeeRepository;
import com.xworkz.requestDto.RequestRegisterEmployeeDTO;
import com.xworkz.requestDto.RequestSignupDTO;
import com.xworkz.responseDto.ResponseDTO;
import com.xworkz.utils.CustomeMailSender;
import com.xworkz.utils.PasswordGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomeMailSender mailSender;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Boolean validateAndSaveByDeptAdmin(RequestRegisterEmployeeDTO requestRegisterEmployeeDTO, Model model) {

        System.out.println("Employee Service process is initiated using DTO: " + requestRegisterEmployeeDTO);

        Optional<EmployeeDTO> employeeDTOOptional = employeeRepository.findByEmail(requestRegisterEmployeeDTO.getEmail());

        if (employeeDTOOptional.isPresent()) {
            System.out.println(employeeDTOOptional.get());
            throw new InfoException("Email already exist");
        }

        Optional<List<EmployeeDTO>> employeeDTOList = employeeRepository.findByEmployeeMobile(requestRegisterEmployeeDTO.getMobile());

        if (employeeDTOList.isPresent() && !employeeDTOList.get().isEmpty()) {
            System.out.println(employeeDTOList.get());
            throw new InfoException("Mobile number already exist");
        }

        DepartmentAdminDTO departmentAdminDTO = (DepartmentAdminDTO) model.getAttribute("departmentAdminData");
        Optional<DepartmentDTO> departmentDTO = departmentRepository.findById(departmentAdminDTO.getDepartmentId());


        if(!departmentDTO.isPresent()){
            throw new InfoException("Department not found.");
        }

        EmployeeDTO employeeDTO = modelMapper.map(requestRegisterEmployeeDTO, EmployeeDTO.class);

        employeeDTO.setDepartmentId(departmentAdminDTO.getDepartmentId());
        employeeDTO.setCreatedBy(employeeDTO.getFname() + " " + employeeDTO.getLname());
        employeeDTO.setCreatedDate(LocalDateTime.now());
        employeeDTO.setPassword(PasswordGenerator.generatePassword());
        System.out.println(employeeDTO);
        Boolean result = employeeRepository.save(employeeDTO);
        mailSender.sendEmployeeRegisterMail(employeeDTO.getEmail(), employeeDTO.getPassword(),departmentDTO.get().getDepartmentName());
        return result;
    }

    @Override
    public ResponseDTO checkMobile(String mobile) {
        return null;
    }

    @Override
    public ResponseDTO checkMail(String mail) {
        return null;
    }
}
