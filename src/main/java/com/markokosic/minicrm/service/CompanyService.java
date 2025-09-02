package com.markokosic.minicrm.service;

import com.markokosic.minicrm.dto.request.CreateCompanyRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

	private final CustomerService customerService;

	@Transactional
	public void createCompany(CreateCompanyRequestDTO request){
		try{
			public void result = customerService.
		} catch(Exception error){

		}

	}

}
