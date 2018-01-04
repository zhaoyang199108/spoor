package com.yzk.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzk.dao.SpoorCommentDao;
import com.yzk.dao.UserDao;
import com.yzk.dto.SpoorCommentBySelfDto;
import com.yzk.dto.SpoorCommentResDto;
import com.yzk.entity.SpoorComment;
import com.yzk.entity.SpoorUser;
import com.yzk.service.SpoorCommentService;
import com.yzk.service.SpoorService;
@Service
public class SpoorCommentServiceImpl implements SpoorCommentService {
	private @Autowired SpoorCommentDao spoorCommentDao;
	private @Autowired UserDao userDao;
	private @Autowired SpoorService spoorService;
	public int addComment(SpoorComment sc) {
		return spoorCommentDao.addComment(sc);
	}
	public int findAllComment(Long spoorId) {
		return spoorCommentDao.findSpoorCommentCounts(spoorId);
	}
	public List<SpoorCommentResDto> findSpoorCommentReply(List<SpoorCommentResDto> scrdList,SpoorComment sc){
		
		List<SpoorComment> scListComment = spoorCommentDao.findCommentList(sc.getId());
		if(scListComment.size() == 0){
			return scrdList;
		}
		for(SpoorComment i :scListComment){
			SpoorCommentResDto scrd = new SpoorCommentResDto();
			scrd.id = i.getId();
			scrd.createTime = i.getCreateTime();
			scrd.userComment = i.getUserComment();
			SpoorUser su = userDao.findUserByUserId(i.getUserId());
			scrd.nickName = su.getNickName();
			scrd.userIcon = su.getUserIcon();
			scrd.userPhone = su.getUserPhone()+"";
			SpoorComment scFind = spoorCommentDao.findSpoorCommentById(i.getParentId());
			SpoorUser suFind = userDao.findUserByUserId(scFind.getUserId());
			scrd.reNickName = suFind.getNickName();
			scrd.reUserPhone = suFind.getUserPhone()+"";
			scrd.reUserIcon =suFind.getUserIcon();
			scrdList.add(scrd);
			findSpoorCommentReply(scrdList,i);
		}
		return scrdList;
	}
	//递归查找评论
	public List<SpoorCommentResDto> findSpoorCommentDetail(Long spoorId) {
		List<SpoorComment> scList = spoorCommentDao.findRootCommentList(spoorId); 
		List<SpoorCommentResDto> listScrd = new ArrayList<SpoorCommentResDto>();
		if(scList.size()>0){
			for(SpoorComment sc : scList){
				SpoorCommentResDto scrd = new SpoorCommentResDto();
				SpoorUser su = userDao.findUserByUserId(sc.getUserId());
				scrd.id = sc.getId();
				scrd.nickName = su.getNickName();
				scrd.createTime = sc.getCreateTime();
				scrd.userIcon = su.getUserIcon();
				scrd.userPhone = su.getUserPhone()+"";
				scrd.userComment = sc.getUserComment();
				scrd.reNickName="";
				scrd.reUserIcon = "";
				scrd.reUserPhone = "";
				listScrd.add(scrd);
				List<SpoorCommentResDto> scrdList = new ArrayList<SpoorCommentResDto>();
				List<SpoorCommentResDto> list = findSpoorCommentReply(scrdList,sc);
				listScrd.addAll(list);
			}
		}
		return listScrd;
	}
	public Set<Long> findMyCommentList(String mobile) {
		Set<Long> s = new HashSet<Long>();
		SpoorUser su = userDao.findUserByUserName(mobile);
		List<Long> list = spoorCommentDao.findNoticeByUser(su.getUserId());
		for(Long i:list){
			s.add(i);
		}
		List<Long> listComment = spoorCommentDao.findMyCommentList(su.getUserId());
		for(Long i:listComment){
			s.add(i);
		}
		return s;
	}
	public List<SpoorCommentBySelfDto> findSpoorCommentSelf(Long spoorId) {
		List<SpoorComment> scList = spoorCommentDao.findRootCommentList(spoorId); 
		List<SpoorCommentBySelfDto> listScrd = new ArrayList<SpoorCommentBySelfDto>();
		if(scList.size()>0){
			for(SpoorComment sc : scList){
				SpoorCommentBySelfDto scrd = new SpoorCommentBySelfDto();
				SpoorUser su = userDao.findUserByUserId(sc.getUserId());
				scrd.id = sc.getId();
				scrd.nickName = su.getNickName();
				scrd.createTime = sc.getCreateTime();
				scrd.userIcon = su.getUserIcon();
				scrd.userPhone = su.getUserPhone()+"";
				scrd.userComment = sc.getUserComment();
				scrd.spoorId = spoorId;
				scrd.reNickName="";
				scrd.reUserIcon = "";
				scrd.reUserPhone = "";
				listScrd.add(scrd);
				List<SpoorCommentBySelfDto> scrdList = new ArrayList<SpoorCommentBySelfDto>();
				List<SpoorCommentBySelfDto> list = findSpoorCommentNoticeReply(scrdList,sc,spoorId);
				listScrd.addAll(list);
			}
		}
		return listScrd;
	}
	public List<SpoorCommentBySelfDto> findSpoorCommentNoticeReply(List<SpoorCommentBySelfDto> scrdList,SpoorComment sc,Long spoorId){
		
		List<SpoorComment> scListComment = spoorCommentDao.findCommentList(sc.getId());
		if(scListComment.size() == 0){
			return scrdList;
		}
		for(SpoorComment i :scListComment){
			SpoorCommentBySelfDto scrd = new SpoorCommentBySelfDto();
			scrd.id = i.getId();
			scrd.createTime = i.getCreateTime();
			scrd.userComment = i.getUserComment();
			scrd.spoorId = spoorId;
			SpoorUser su = userDao.findUserByUserId(i.getUserId());
			scrd.nickName = su.getNickName();
			scrd.userIcon = su.getUserIcon();
			scrd.userPhone = su.getUserPhone()+"";
			SpoorComment scFind = spoorCommentDao.findSpoorCommentById(i.getParentId());
			SpoorUser suFind = userDao.findUserByUserId(scFind.getUserId());
			scrd.reNickName = suFind.getNickName();
			scrd.reUserPhone = suFind.getUserPhone()+"";
			scrd.reUserIcon =suFind.getUserIcon();
			scrdList.add(scrd);
			findSpoorCommentNoticeReply(scrdList,i,spoorId);
		}
		return scrdList;
	}
	public int selectCountUnRead(Long userId) {
		return spoorCommentDao.selectCountUnRead(userId);
	}
	public SpoorComment findCommentIdByUserId(SpoorComment sc) {

		return spoorCommentDao.findCommentIdByUserId(sc);
	}
	public SpoorComment findCommentByCommentId(Long commentId) {
		return spoorCommentDao.findCommentByCommentId(commentId);
	}
}
