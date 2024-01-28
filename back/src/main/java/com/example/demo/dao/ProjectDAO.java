package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.ProjectDTO;

@Repository
public class ProjectDAO {

	@Autowired
	SqlSession sqlSession;
	
	public List getProjectList(ProjectDTO projectDTO) {
		return sqlSession.selectList("project.getProjectList", projectDTO);
	}
	
	public int getProjectTotal(ProjectDTO projectDTO) {
		return sqlSession.selectOne("project.getProjectTotal", projectDTO);
	}
	
	public int addProject(ProjectDTO projectDTO) {
		sqlSession.insert("project.addProject", projectDTO);
		return projectDTO.getPrjSeq();
	}
	
	public void addProjectSkill(ProjectDTO projectDTO) {
		sqlSession.insert("skill.addProjectSkill", projectDTO);
		return;
	}
	
	public ProjectDTO getProjectDetail(ProjectDTO projectDTO) {
		return sqlSession.selectOne("project.getProjectDetail", projectDTO);
	}
	
	public void delProjectSkill(ProjectDTO projectDTO) {
		sqlSession.delete("skill.delProjectSkill", projectDTO);
	}
	
	public void editProject(ProjectDTO projectDTO) {
		sqlSession.update("project.editProject", projectDTO);
		return;
	}
	
	public void delProject(List list) {
		sqlSession.delete("project.delProject", list);
		return;
	}
}
