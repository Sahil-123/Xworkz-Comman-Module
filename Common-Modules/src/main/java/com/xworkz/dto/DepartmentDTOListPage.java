package com.xworkz.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTOListPage <T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long count;
    private List<T> list;
}
