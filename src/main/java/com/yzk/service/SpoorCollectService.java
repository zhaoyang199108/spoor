package com.yzk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yzk.dto.SpoorResDto;
import com.yzk.entity.SpoorCollect;

@Service
public interface SpoorCollectService {
	int spoorCollect(SpoorCollect sc);
	List<SpoorResDto> findCollectByMobile(Long userId,int currentPage,int pageSize);
	int deleteCollect(Long userId,Long spoorId);
	int findCollectByUserIdAndSpoorId(Long userId,Long spoorId );
}
