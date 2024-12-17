package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.PageVO;
import com.example.demo.dto.Test;

@Mapper
public interface TestMapper {
	List<Test> getMyAccountList(Map<String, Object> map);

	int insertAccountList(List<Test> getList);

	Integer getSeq(Test entity);

	List<Test> getUpdateMyAccountList(Map<String, Object> paramMap);

	List<Map<String, Object>> testList(Test test);

	int delete(String[] seqArr);

	int update(Map<String, Object> item);

	List<String> getFilterList(Test test);

	int getTotalListCnt(Test test);

	List<Test> getFilteredList(Map<String, Object> map);
}
