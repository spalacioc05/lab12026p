package com.udea.lab12026p.mapper;

import org.mapstruct.Mapper;

import com.udea.lab12026p.dto.CustomerDTO;
import com.udea.lab12026p.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);
    Customer toEntity(CustomerDTO customerDTO);
}
