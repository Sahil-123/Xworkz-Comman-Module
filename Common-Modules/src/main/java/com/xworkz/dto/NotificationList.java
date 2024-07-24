package com.xworkz.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
@ToString
@AllArgsConstructor
public class NotificationList <T>{
    private Integer count;
    private List<T> toDays;
    private List<T> older;

    public NotificationList(){
        this.count = 0;
        this.toDays = new ArrayList<>();
        this.older = new ArrayList<>();
    }
}
