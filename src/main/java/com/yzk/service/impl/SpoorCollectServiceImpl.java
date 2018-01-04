package com.yzk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzk.dao.PictureDao;
import com.yzk.dao.SpoorBenefitDao;
import com.yzk.dao.SpoorCollectDao;
import com.yzk.dao.SpoorCommentDao;
import com.yzk.dao.SpoorDao;
import com.yzk.dao.UserDao;
import com.yzk.dto.SpoorResDto;
import com.yzk.entity.SpoorBenefit;
import com.yzk.entity.SpoorCollect;
import com.yzk.entity.SpoorPicture;
import com.yzk.entity.SpoorSpoor;
import com.yzk.entity.SpoorUser;
import com.yzk.service.SpoorBenefitService;
import com.yzk.service.SpoorCollectService;
@Service
public class SpoorCollectServiceImpl implements SpoorCollectService {
	private @Autowired SpoorCollectDao spoorCollectDao;
	private @Autowired SpoorDao spoorDao;
	@Autowired
	private PictureDao pictureDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private SpoorBenefitService spoorBenefitService;
	@Autowired
	private SpoorBenefitDao spoorBenefitDao;
	@Autowired
	private SpoorCommentDao spoorCommentDao;
	public int spoorCollect(SpoorCollect sc) {
		int count = spoorCollectDao.spoorCollect(sc);
		return count;
	}
	public List<SpoorResDto> findCollectByMobile(Long userId,int currentPage,int pageSize) {
		List<SpoorCollect> list = spoorCollectDao.findCollectByMobile(userId,currentPage,pageSize);
		List<SpoorSpoor> listSpoor = new ArrayList<SpoorSpoor>();
		List<SpoorResDto> listRes = new ArrayList<SpoorResDto>();
		for(SpoorCollect i : list){
			SpoorSpoor ss =spoorDao.findById(i.getSpoorId());
			listSpoor.add(ss);
		}
		for (SpoorSpoor i : listSpoor) {
			SpoorResDto srd = new SpoorResDto();
			List<SpoorPicture> listPic = pictureDao.findPicBySpoorId(i
					.getSpoorId());

			SpoorUser su = userDao.findUserByUserId(i.getUserId());
			// srd.spoorSpoor = i;
			int sc = findCollectByUserIdAndSpoorId(
					i.getSpoorId(), su.getUserId());
			srd.iscollect = sc + "";

			SpoorBenefit sb = spoorBenefitService
					.findBenefitByUserIdAndSpoorId(i.getSpoorId(),
							userId);
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
	public int deleteCollect(Long userId, Long spoorId) {
		return spoorCollectDao.deleteCollect(userId, spoorId);
	}
	public int findCollectByUserIdAndSpoorId(Long spoorId, Long userId) {
		// TODO Auto-generated method stub
		return spoorCollectDao.findCollectByUserIdAndSpoorId(spoorId,userId);
	}

}
