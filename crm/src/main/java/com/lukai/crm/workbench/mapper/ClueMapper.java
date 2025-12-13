package com.lukai.crm.workbench.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.Clue;

@Mapper
public interface ClueMapper {
	
	List<Clue> selectClueByConditionForPage(Map<String, Object> map);
	int selectClueByConditionForPageCount(Map<String, Object> map);
	/**
	 * リードを挿入する
	 * @param clue
	 * @return
	 */
	int insertClue(Clue clue);
	/**
	 * リードを削除	する
	 * @param ids
	 * @return
	 */
	int deleteClueByIds(String[] ids);
	/**
	 * IDでリードを検索する
	 * @param id
	 * @return
	 */
	Clue selectClueByClueId(String id);
	/**
	 * 編集のためにIDでリードを検索する
	 * @param id
	 * @return
	 */
	Clue selectClueForEditByClueId(String id);
	
	int updateClue(Clue clue);
	
	Clue selectClueForConvertById(String id);
	
}
