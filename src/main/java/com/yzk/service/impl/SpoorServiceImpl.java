package com.yzk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yzk.dao.PictureDao;
import com.yzk.dao.SpoorBenefitDao;
import com.yzk.dao.SpoorCollectDao;
import com.yzk.dao.SpoorCommentDao;
import com.yzk.dao.SpoorDao;
import com.yzk.dao.SpoorUnreadDao;
import com.yzk.dao.UserDao;
import com.yzk.dao.VideoDao;
import com.yzk.dto.SpoorDetailResDto;
import com.yzk.dto.SpoorResDto;
import com.yzk.entity.SpoorBenefit;
import com.yzk.entity.SpoorComment;
import com.yzk.entity.SpoorPicture;
import com.yzk.entity.SpoorSpoor;
import com.yzk.entity.SpoorUser;
import com.yzk.service.IFileService;
import com.yzk.service.SpoorBenefitService;
import com.yzk.service.SpoorCollectService;
import com.yzk.service.SpoorCommentService;
import com.yzk.service.SpoorService;
import com.yzk.service.UserService;
import com.yzk.util.UploadFileUtil;

@Service
public class SpoorServiceImpl implements SpoorService {
	@Autowired
	private SpoorDao spoorDao;
	@Autowired
	private PictureDao pictureDao;
	@Autowired
	private VideoDao videoDao;
	@Autowired
	private IFileService fileServive;
	@Autowired
	private UserService userServive;
	@Autowired
	private SpoorCollectService spoorCollectService;
	@Autowired
	private SpoorBenefitService spoorBenefitService;
	@Autowired
	private SpoorCommentService spoorCommentService;
	@Autowired
	private SpoorBenefitDao spoorBenefitDao;
	@Autowired
	private SpoorCommentDao spoorCommentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private SpoorCollectDao spoorCollectDao;
	@Autowired
	private SpoorUnreadDao spoorUnreadDao;

	// @Autowired
	// private SpoorPictureService spoorPictureService;

	public int insertSpoor(SpoorSpoor ss, List<MultipartFile> files, String path) {
		// TODO Auto-generated method stub
		int count = spoorDao.insertSpoor(ss);
		int countPicture = 0;
		if (count > 0) {
			SpoorSpoor ssNow = spoorDao.findSpoorByTime();
			UploadFileUtil ufu = new UploadFileUtil();
			List<String> list = new ArrayList<String>();
			for (MultipartFile file : files) {
				String temp = ufu.uploadFile(file);
				list.add(temp);
			}
			SpoorPicture sp = new SpoorPicture();
			if (ssNow.getSpoorId() != 0) {
				sp.setSpoorId(ssNow.getSpoorId());
			}
			for (String name : list) {
				sp.setPictureUrl("/upload/img/" + name);
				countPicture = pictureDao.insertPicture(sp);
				if (countPicture == 0) {
					return 0;
				}
			}
		}
		return 1;
	}

	public List<SpoorResDto> findSpoorByMobile(String mobile) {
		SpoorUser su = userServive.findUserByUserName(mobile);
		List<SpoorSpoor> list = spoorDao.findSpoorByMobile(su.getUserId());
		List<SpoorResDto> listRes = new ArrayList<SpoorResDto>();
		for (SpoorSpoor i : list) {
			SpoorResDto srd = new SpoorResDto();
			List<SpoorPicture> listPic = pictureDao.findPicBySpoorId(i
					.getSpoorId());

			SpoorUser su1 = userDao.findUserByUserName(mobile);
			// SpoorUser su = userDao.findUserByUserId(i.getUserId());
			// srd.spoorSpoor = i;
			// int sc = spoorCollectService.findCollectByUserIdAndSpoorId(
			// su1.getUserId(),i.getSpoorId());
			// srd.iscollect = sc + "";
			//
			// SpoorBenefit sb = spoorBenefitService
			// .findBenefitByUserIdAndSpoorId(i.getSpoorId(),
			// su1.getUserId());
			// if (null == sb) {
			// srd.isBenefit = "0";
			// } else {
			// srd.isBenefit = "1";
			// }
			srd.iscollect = "0";
			srd.isBenefit = "0";
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

	public List<SpoorResDto> findAll(String mobile) {
		List<SpoorSpoor> list = spoorDao.findAll();
		List<SpoorResDto> listRes = new ArrayList<SpoorResDto>();
		for (SpoorSpoor i : list) {
			SpoorResDto srd = new SpoorResDto();
			List<SpoorPicture> listPic = pictureDao.findPicBySpoorId(i
					.getSpoorId());
			SpoorUser su1 = userDao.findUserByUserName(mobile);
			SpoorUser su = userDao.findUserByUserId(i.getUserId());
			// srd.spoorSpoor = i;
			int sc = spoorCollectService.findCollectByUserIdAndSpoorId(
					su1.getUserId(), i.getSpoorId());
			srd.iscollect = sc + "";

			SpoorBenefit sb = spoorBenefitService
					.findBenefitByUserIdAndSpoorId(i.getSpoorId(),
							su1.getUserId());
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

	public List<SpoorResDto> findSpoorByTags(String tagOne, String tagTwo,
			String Three, String mobile) {
		List<SpoorResDto> listRes = new ArrayList<SpoorResDto>();
		// List<SpoorSpoor> listSpoor = new ArrayList<SpoorSpoor>();
		// List<SpoorSpoor> ssOne = spoorDao.findTagOne(tagOne);
		// if (ssOne.size() > 0) {
		// for (SpoorSpoor i : ssOne) {
		// listSpoor.add(i);
		// }
		// }
		// List<SpoorSpoor> ssTwo = spoorDao.findTagTwo(tagOne);
		// if (ssTwo.size() > 0) {
		// for (SpoorSpoor i : ssTwo) {
		// listSpoor.add(i);
		// }
		// }
		// List<SpoorSpoor> ssThree = spoorDao.findTagThree(tagOne);
		// if (ssThree.size() > 0) {
		// for (SpoorSpoor i : ssThree) {
		// listSpoor.add(i);
		// }
		// }
		//
		// //
		// List<SpoorSpoor> ssOne1 = spoorDao.findTagOne(tagTwo);
		// if (ssOne1.size() > 0) {
		// for (SpoorSpoor i : ssOne1) {
		// listSpoor.add(i);
		// }
		// }
		// List<SpoorSpoor> ssTwo1 = spoorDao.findTagTwo(tagTwo);
		// if (ssTwo1.size() > 0) {
		// for (SpoorSpoor i : ssTwo1) {
		// listSpoor.add(i);
		// }
		// }
		// List<SpoorSpoor> ssThree1 = spoorDao.findTagThree(tagTwo);
		// if (ssThree1.size() > 0) {
		// for (SpoorSpoor i : ssThree1) {
		// listSpoor.add(i);
		// }
		// }
		// //
		// List<SpoorSpoor> ssOne2 = spoorDao.findTagOne(Three);
		// if (ssOne2.size() > 0) {
		// for (SpoorSpoor i : ssOne2) {
		// listSpoor.add(i);
		// }
		// }
		// List<SpoorSpoor> ssTwo2 = spoorDao.findTagTwo(Three);
		// if (ssTwo2.size() > 0) {
		// for (SpoorSpoor i : ssTwo2) {
		// listSpoor.add(i);
		// }
		// }
		// List<SpoorSpoor> ssThree2 = spoorDao.findTagThree(Three);
		// if (ssThree2.size() > 0) {
		// for (SpoorSpoor i : ssThree2) {
		// listSpoor.add(i);
		// }
		// }
		List<SpoorSpoor> listSpoor = spoorDao.findAllByTags(tagOne, tagTwo,
				Three);
		if (listSpoor.size() > 0) {
			for (SpoorSpoor i : listSpoor) {
				SpoorResDto srd = new SpoorResDto();
				List<SpoorPicture> listPic = pictureDao.findPicBySpoorId(i
						.getSpoorId());
				SpoorUser su1 = userDao.findUserByUserName(mobile);
				SpoorUser su = userDao.findUserByUserId(i.getUserId());
				// srd.spoorSpoor = i;
				int sc = spoorCollectService.findCollectByUserIdAndSpoorId(
						su1.getUserId(), i.getSpoorId());
				srd.iscollect = sc + "";
				SpoorBenefit sb = spoorBenefitService
						.findBenefitByUserIdAndSpoorId(i.getSpoorId(),
								su1.getUserId());
				if (null == sb) {
					srd.isBenefit = "0";
				} else {
					srd.isBenefit = "1";
				}
				int benefitCount = spoorBenefitDao.findAllBenefits(i
						.getSpoorId());
				srd.benefitCount = "" + benefitCount;
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
		}
		return listRes;
	}

	public List<SpoorResDto> findSpoorByTag(String tag) {
		List<SpoorSpoor> listSpoor = spoorDao.findByTag(tag);
		List<SpoorResDto> listRes = new ArrayList<SpoorResDto>();
		if (listSpoor.size() > 0) {
			for (SpoorSpoor i : listSpoor) {
				SpoorResDto srd = new SpoorResDto();
				List<SpoorPicture> listPic = pictureDao.findPicBySpoorId(i
						.getSpoorId());

				SpoorUser su = userDao.findUserByUserId(i.getUserId());
				// int sc = spoorCollectService.findCollectByUserIdAndSpoorId(
				// i.getSpoorId(), su.getUserId());
				// srd.iscollect = sc + "";
				// SpoorBenefit sb = spoorBenefitService
				// .findBenefitByUserIdAndSpoorId(i.getSpoorId(),
				// su.getUserId());
				// if (null == sb) {
				// srd.isBenefit = "0";
				// } else {
				// srd.isBenefit = "1";
				// }
				srd.iscollect = "0";
				srd.isBenefit = "0";
				int benefitCount = spoorBenefitDao.findAllBenefits(i
						.getSpoorId());
				srd.benefitCount = "" + benefitCount;
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
		}
		return listRes;
	}

	public SpoorDetailResDto findSpoorDetail(String spoorId, String mobile) {
		SpoorSpoor ss = spoorDao.findById(Long.parseLong(spoorId));
		// List<SpoorDetailResDto> list = new ArrayList<SpoorDetailResDto>();
		// List<SpoorComment> sc
		// =spoorCommentDao.findRootCommentList(Long.parseLong(spoorId));

		SpoorDetailResDto sdrd = new SpoorDetailResDto();
		List<SpoorPicture> listPic = pictureDao.findPicBySpoorId(ss
				.getSpoorId());
		if (mobile == null || "".equals(mobile)) {

		}
		SpoorUser suSpoor = userDao.findUserByUserId(ss.getUserId());
		SpoorUser su = userDao.findUserByUserName(mobile);
		if (su == null) {
			sdrd.iscollect = "0";
			sdrd.isBenefit = "0";
			int benefitCount = spoorBenefitDao.findAllBenefits(ss.getSpoorId());
			sdrd.benefitCount = "" + benefitCount;
			int commentCount = spoorCommentDao.findSpoorCommentCounts(ss
					.getSpoorId());
			sdrd.comments = "" + commentCount;
			sdrd.userIcon = suSpoor.getUserIcon();
			sdrd.nickName = suSpoor.getNickName();
			sdrd.userPhone = suSpoor.getUserPhone();
			sdrd.spoorId = ss.getSpoorId();
			sdrd.createTime = ss.getCreateTime();
			sdrd.spoorContent = ss.getSpoorContent();
			sdrd.spoorTagOne = ss.getSpoorTagOne();
			sdrd.spoorTagTwo = ss.getSpoorTagTwo();
			sdrd.spoorTagThree = ss.getSpoorTagThree();
			sdrd.spoorPictures = listPic;

			sdrd.spoorComment = spoorCommentService.findSpoorCommentDetail(ss
					.getSpoorId());
		} else {
			int collectCount = spoorCollectService
					.findCollectByUserIdAndSpoorId(su.getUserId(),
							ss.getSpoorId());
			sdrd.iscollect = collectCount + "";
			SpoorBenefit sb = spoorBenefitService
					.findBenefitByUserIdAndSpoorId(ss.getSpoorId(),
							su.getUserId());
			if (null == sb) {
				sdrd.isBenefit = "0";
			} else {
				sdrd.isBenefit = "1";
			}
			int benefitCount = spoorBenefitDao.findAllBenefits(ss.getSpoorId());
			sdrd.benefitCount = "" + benefitCount;
			int commentCount = spoorCommentDao.findSpoorCommentCounts(ss
					.getSpoorId());
			sdrd.comments = "" + commentCount;
			sdrd.userIcon = suSpoor.getUserIcon();
			sdrd.nickName = suSpoor.getNickName();
			sdrd.userPhone = suSpoor.getUserPhone();
			sdrd.spoorId = ss.getSpoorId();
			sdrd.createTime = ss.getCreateTime();
			sdrd.spoorContent = ss.getSpoorContent();
			sdrd.spoorTagOne = ss.getSpoorTagOne();
			sdrd.spoorTagTwo = ss.getSpoorTagTwo();
			sdrd.spoorTagThree = ss.getSpoorTagThree();
			sdrd.spoorPictures = listPic;

			sdrd.spoorComment = spoorCommentService.findSpoorCommentDetail(ss
					.getSpoorId());
		}
		// SpoorUser su = userDao.findUserByUserId(ss.getUserId());

		// list.add(sdrd);
		return sdrd;
	}

	public List<SpoorResDto> findAll() {
		List<SpoorSpoor> list = spoorDao.findAll();
		List<SpoorResDto> listRes = new ArrayList<SpoorResDto>();
		for (SpoorSpoor i : list) {
			SpoorResDto srd = new SpoorResDto();
			List<SpoorPicture> listPic = pictureDao.findPicBySpoorId(i
					.getSpoorId());
			// SpoorUser su = userDao.findUserByUserName(mobile);
			SpoorUser su = userDao.findUserByUserId(i.getUserId());
			// srd.spoorSpoor = i;
			// int sc = spoorCollectService.findCollectByUserIdAndSpoorId(
			// i.getSpoorId(), su.getUserId());
			// srd.iscollect = sc + "";
			//
			// SpoorBenefit sb = spoorBenefitService
			// .findBenefitByUserIdAndSpoorId(i.getSpoorId(),
			// su.getUserId());
			// if (null == sb) {
			// srd.isBenefit = "0";
			// } else {
			// srd.isBenefit = "1";
			// }
			srd.iscollect = "0";
			srd.isBenefit = "0";
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
			System.out.println(i.getCreateTime());
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

	// 登录状态下
	public List<SpoorResDto> findSpoorByMobile(String mobile, String loginId) {
		SpoorUser su = userServive.findUserByUserName(mobile);
		List<SpoorSpoor> list = spoorDao.findSpoorByMobile(su.getUserId());
		List<SpoorResDto> listRes = new ArrayList<SpoorResDto>();
		for (SpoorSpoor i : list) {
			SpoorResDto srd = new SpoorResDto();
			List<SpoorPicture> listPic = pictureDao.findPicBySpoorId(i
					.getSpoorId());

			SpoorUser currentUser = userDao.findUserByUserName(loginId);
			// SpoorUser findUser = userDao.findUserByUserId(i.getUserId());
			int sc = spoorCollectService.findCollectByUserIdAndSpoorId(
					currentUser.getUserId(), i.getSpoorId());
			srd.iscollect = sc + "";
			SpoorBenefit sb = spoorBenefitService
					.findBenefitByUserIdAndSpoorId(i.getSpoorId(),
							currentUser.getUserId());
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

	public List<SpoorResDto> findSpoorByTag(String tag, String mobile) {

		List<SpoorSpoor> listSpoor = spoorDao.findByTag(tag);
		List<SpoorResDto> listRes = new ArrayList<SpoorResDto>();
		if (listSpoor.size() > 0) {
			for (SpoorSpoor i : listSpoor) {
				SpoorResDto srd = new SpoorResDto();
				List<SpoorPicture> listPic = pictureDao.findPicBySpoorId(i
						.getSpoorId());
				SpoorUser su = userDao.findUserByUserName(mobile);
				SpoorUser su1 = userDao.findUserByUserId(i.getUserId());
				int sc = spoorCollectService.findCollectByUserIdAndSpoorId(
						su.getUserId(), i.getSpoorId());
				srd.iscollect = sc + "";
				SpoorBenefit sb = spoorBenefitService
						.findBenefitByUserIdAndSpoorId(i.getSpoorId(),
								su.getUserId());
				if (null == sb) {
					srd.isBenefit = "0";
				} else {
					srd.isBenefit = "1";
				}
				int benefitCount = spoorBenefitDao.findAllBenefits(i
						.getSpoorId());
				srd.benefitCount = "" + benefitCount;
				int commentCount = spoorCommentDao.findSpoorCommentCounts(i
						.getSpoorId());
				srd.comments = "" + commentCount;
				srd.userIcon = su1.getUserIcon();
				srd.nickName = su1.getNickName();
				srd.userPhone = su1.getUserPhone();
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
		}
		return listRes;
	}

	@Transactional
	public int deleteSpoorBySpoorId(Long spoorId) {
		int sb = spoorBenefitDao.deleteBenefitBySpoorId(spoorId);
		List<SpoorComment> list = spoorCommentDao.findCommentBySpoorId(spoorId);
		for (SpoorComment i : list) {
			spoorUnreadDao.deleteSpoorUnreadByCommentId(i.getId());
		}
		int sc = spoorCommentDao.deleteCommentBySpoorId(spoorId);
		int scs = spoorCollectDao.deleteCollectBySpoorId(spoorId);
		int sp = pictureDao.deletePictureBySpoorId(spoorId);
		int s = deleteSpoorId(spoorId);
		if (s > 0) {
			return 1;
		}
		return 0;
	}

	private int deleteSpoorId(Long spoorId) {

		return spoorDao.deleteSpoorId(spoorId);
	}

	public SpoorSpoor findSpoorBySpoorId(Long spoorId) {
		// TODO Auto-generated method stub
		return spoorDao.findSpoorBySpoorId(spoorId);
	}
}
