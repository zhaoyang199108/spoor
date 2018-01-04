package com.yzk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzk.dao.PictureDao;
import com.yzk.dao.SpoorBenefitDao;
import com.yzk.dao.SpoorCommentDao;
import com.yzk.dao.SpoorDao;
import com.yzk.dao.UserDao;
import com.yzk.dto.SpoorResDto;
import com.yzk.entity.SpoorBenefit;
import com.yzk.entity.SpoorPicture;
import com.yzk.entity.SpoorSpoor;
import com.yzk.entity.SpoorUser;
import com.yzk.service.SpoorBenefitService;
import com.yzk.service.SpoorCollectService;
@Service
public class SpoorBenefitServiceImpl implements SpoorBenefitService {
	private @Autowired SpoorBenefitDao spoorBenefitDao;
	private @Autowired SpoorDao spoorDao;
	private @Autowired PictureDao pictureDao;
	private @Autowired UserDao userDao;
	private @Autowired SpoorCollectService spoorCollectService;
	private @Autowired SpoorCommentDao spoorCommentDao;
	
	public int spoorBenefit(SpoorBenefit sc) {
		// TODO Auto-generated method stub
		spoorBenefitDao.spoorBenefit(sc);
		int benefitCount = spoorBenefitDao.findAllBenefits(sc.getSpoorId());
		return benefitCount;
	}
	public List<SpoorResDto> findBenefitByMobile(Long userId, int currentPage,
			int pageSize) {
		List<SpoorBenefit> list = spoorBenefitDao.findBenefitByMobile(userId,currentPage,pageSize);
		List<SpoorSpoor> listSpoor = new ArrayList<SpoorSpoor>();
		List<SpoorResDto> listRes = new ArrayList<SpoorResDto>();
		for(SpoorBenefit i : list){
			SpoorSpoor ss =spoorDao.findById(i.getSpoorId());
			listSpoor.add(ss);
		}
		for (SpoorSpoor i : listSpoor) {
			SpoorResDto srd = new SpoorResDto();
			List<SpoorPicture> listPic = pictureDao.findPicBySpoorId(i
					.getSpoorId());

			SpoorUser su = userDao.findUserByUserId(i.getUserId());
			// srd.spoorSpoor = i;
			int sc = spoorCollectService.findCollectByUserIdAndSpoorId(
					i.getSpoorId(), su.getUserId());
			srd.iscollect = sc + "";

			SpoorBenefit sb = findBenefitByUserIdAndSpoorId(i.getSpoorId(),
							su.getUserId());
			if (null == sb) {
				srd.isBenefit = "0";
			} else {
				srd.isBenefit = "1";
			}
			int benefitCount = spoorBenefitDao.findAllBenefits(i.getSpoorId());
			srd.benefitCount = "" + benefitCount;
			// srd.iscollect =
			int commentCount = spoorCommentDao.findSpoorCommentCounts(i
					.getSpoorId());
			srd.comments = "" + commentCount;
			srd.userIcon = su.getUserIcon();
			srd.nickName = su.getNickName();
			srd.userPhone = su.getUserPhone();
			srd.spoorId = i.getSpoorId();
			srd.createTime = i.getCreateTime();
			srd.spoorContent = i.getSpoorContent();
			srd.spoorTagOne = i.getSpoorTagOne();
			srd.spoorTagTwo = i.getSpoorTagTwo();
			srd.spoorTagThree = i.getSpoorTagThree();
			srd.spoorPictures = listPic;
			// srd.spoorVideos = listVideo;
			listRes.add(srd);
		}
		return listRes;
	}

	public int deleteBenefit(Long userId, Long spoorId) {
		// TODO Auto-generated method stub
		return spoorBenefitDao.deleteBenefit(userId,spoorId);
	}

	public SpoorBenefit findBenefitByUserIdAndSpoorId(Long spoorId ,Long userId) {
		// TODO Auto-generated method stub
		return spoorBenefitDao.findBenefitByUserIdAndSpoorId(spoorId , userId);
	}
	public int findAllBenefits(Long spoorId) {
		// TODO Auto-generated method stub
		return spoorBenefitDao.findAllBenefits(spoorId);
	}



}
