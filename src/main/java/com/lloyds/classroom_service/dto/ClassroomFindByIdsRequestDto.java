package com.lloyds.classroom_service.dto;


import lombok.Data;

import java.util.List;

@Data
public class ClassroomFindByIdsRequestDto {
    private List<Integer> ids;
}
