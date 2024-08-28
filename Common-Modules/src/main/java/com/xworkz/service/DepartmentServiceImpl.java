package com.xworkz.service;

import com.xworkz.dto.DTOListPage;
import com.xworkz.dto.DepartmentDTOListPage;
import com.xworkz.entity.ComplaintDTO;
import com.xworkz.entity.DepartmentDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.repository.DepartmentRepository;
import com.xworkz.requestDto.RequestDepartmentDTO;
import com.xworkz.responseDto.DepartmentNameAndIdResponseDto;
import com.xworkz.responseDto.ResponseDTO;
import com.xworkz.responseDto.ResponseDataDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
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
    public DepartmentDTOListPage<DepartmentDTO> getAllDepartmentsWithPagination(Integer offset, Integer pageSize) {
        System.out.println("Running Department service impl get departments with pagination.");
        return departmentRepository.findAll(offset,pageSize);
    }

    @Override
    public List<DepartmentNameAndIdResponseDto> getAllDepartmentsNameAndId() {

        Optional<List<DepartmentDTO>> departmentDTOList =
                departmentRepository.findAll();

        List<DepartmentNameAndIdResponseDto> departmentNameAndIdResponseDtos =departmentDTOList.get().stream().map(element->modelMapper.map(element, DepartmentNameAndIdResponseDto.class)).collect(Collectors.toList());
        return departmentNameAndIdResponseDtos;
    }

    @Override
    public ResponseDTO checkDepartmentName(String departmentName) {
        boolean check = departmentRepository.checkDepartmentName(departmentName);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("200");
        responseDTO.setData(new ResponseDataDTO(check));
        return responseDTO;
    }

    @Override
    @Transactional
    public Boolean validateAndSave(RequestDepartmentDTO requestDepartmentDTO, Model model) {
        System.out.println("Department Service save department process is initiated using dto." + requestDepartmentDTO);

        if(departmentRepository.checkDepartmentName(requestDepartmentDTO.getDepartmentName())){
            throw new InfoException("Department name already exists");
        }

        DepartmentDTO departmentDTO = modelMapper.map(requestDepartmentDTO,DepartmentDTO.class);
        departmentDTO.setCreatedBy("admin");
        departmentDTO.setCreatedDate(LocalDateTime.now());

        return departmentRepository.save(departmentDTO);
    }

}
