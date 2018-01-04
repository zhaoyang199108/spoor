package com.yzk.service;

import java.util.List;

import com.yzk.dto.SpoorResDto;
import com.yzk.entity.SpoorBenefit;

public interface SpoorBenefitService {
	int spoorBenefit(SpoorBenefit sc);
	List<SpoorResDto> findBenefitByMobile(Long userId,int currentPage,int pageSize);
	int deleteBenefit(Long userId,Long spoorId);
	SpoorBenefit findBenefitByUserIdAndSpoorId(Long spoorId ,Long userId);
	int findAllBenefits(Long spoorId);
}
