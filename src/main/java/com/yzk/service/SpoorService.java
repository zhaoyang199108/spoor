package com.yzk.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yzk.dto.SpoorDetailResDto;
import com.yzk.dto.SpoorResDto;
import com.yzk.entity.SpoorSpoor;
@Service
public interface SpoorService {
	int insertSpoor(SpoorSpoor ss,List<MultipartFile> files,String path);
	List<SpoorResDto> findSpoorByMobile(String mobile);
	List<SpoorResDto> findSpoorByMobile(String mobile,String loginId);
	List<SpoorResDto> findAll(String mobile);
	List<SpoorResDto> findAll();
	List<SpoorResDto> findSpoorByTags(String tagOne,String tagTwo,String Three,String mobile);
	List<SpoorResDto>  findSpoorByTag(String tag);
	List<SpoorResDto>  findSpoorByTag(String tag,String mobile);
	SpoorDetailResDto findSpoorDetail(String spoorId,String mobile);
	int deleteSpoorBySpoorId(Long spoorId);
	SpoorSpoor findSpoorBySpoorId(Long spoorId);
} 