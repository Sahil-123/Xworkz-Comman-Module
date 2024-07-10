package com.xworkz.service;

import com.xworkz.dto.DepartmentDTO;
import com.xworkz.repository.DepartmentRepository;
import com.xworkz.responseDto.DepartmentNameAndIdResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<List<DepartmentDTO>> getAllDepartments() {
        System.out.println("service fetching departments");
        return departmentRepository.findAll();
    }

    @Override
    public List<DepartmentNameAndIdResponseDto> getAllDepartmentsNameAndId() {

        Optional<List<DepartmentDTO>> departmentDTOList =
                departmentRepository.findAll();

        List<DepartmentNameAndIdResponseDto> departmentNameAndIdResponseDtos =departmentDTOList.get().stream().map(element->modelMapper.map(element, DepartmentNameAndIdResponseDto.class)).collect(Collectors.toList());
        return departmentNameAndIdResponseDtos;
    }

}
