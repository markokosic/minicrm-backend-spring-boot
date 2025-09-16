package com.markokosic.minicrm.service;

import com.markokosic.minicrm.context.TenantContextHolder;
import com.markokosic.minicrm.dto.request.CreateCompanyRequestDTO;
import com.markokosic.minicrm.mapper.CompanyMapper;
import com.markokosic.minicrm.model.Company;
import com.markokosic.minicrm.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

	private final CustomerService customerService;
	private final CompanyMapper companyMapper;
	private final CompanyRepository companyRepository;


	@Transactional
	public void createCompany(CreateCompanyRequestDTO request){
		try{
			Long tenantId = TenantContextHolder.getTenantId();
			Company company = companyMapper.toEntity(request, tenantId);

			companyRepository.save(company);
		} catch (Exception e) {
			//TODO add custom exception
			throw new RuntimeException("Error creating Company", e);
		}

	}

}
