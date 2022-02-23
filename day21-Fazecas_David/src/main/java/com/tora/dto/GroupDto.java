package com.tora.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupDto {
    private String groupName;
    private List<String> members;
}
