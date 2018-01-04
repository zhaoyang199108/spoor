package com.yzk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yzk.entity.SpoorBenefit;

public interface SpoorBenefitDao {
	int spoorBenefit(SpoorBenefit sc);
	List<SpoorBenefit> findBenefitByMobile(@Param("userId")Long userId,@Param("currentPage")int currentPage,@Param("pageSize")int pageSize);
	int deleteBenefit(@Param("userId")Long userId,@Param("spoorId")Long spoorId);
	SpoorBenefit findBenefitByUserIdAndSpoorId(@Param("spoorId")Long spoorId ,@Param("userId")Long userId);
	int findAllBenefits(@Param("spoorId")Long spoorId); 
	int deleteBenefitBySpoorId(@Param("spoorId")Long spoorId);
}
