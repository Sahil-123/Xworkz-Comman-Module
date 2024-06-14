package com.xworkz.repository;

import com.xworkz.dto.AdminDTO;

import java.util.List;
import java.util.Optional;

public interface AdminRepository {
    Optional<List<AdminDTO>> findByEmail(String email);

    boolean updateByDto(AdminDTO adminDTO);
}
