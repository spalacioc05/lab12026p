package com.udea.lab12026p.mapper;

import org.mapstruct.Mapper;

import com.udea.lab12026p.dto.TransactionDTO;
import com.udea.lab12026p.entity.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
	TransactionDTO toDTO(Transaction transaction);
	Transaction toEntity(TransactionDTO transactionDTO);
}
