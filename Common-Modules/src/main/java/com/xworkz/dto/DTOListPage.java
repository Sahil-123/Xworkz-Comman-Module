package com.xworkz.dto;

import lombok.*;

import java.util.List;
import java.util.Optional;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DTOListPage <T>{
    private Long count;
    private Optional<List<T>> list;
}
