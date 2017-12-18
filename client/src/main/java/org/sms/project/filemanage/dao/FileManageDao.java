package org.sms.project.filemanage.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.sms.project.filemanage.entity.FileManage;
import org.sms.project.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileManageDao {

    @Autowired
    private SqlSession sqlSession;

    public int insert(FileManage fileManage) {
        return sqlSession.insert(this.getClass().getName() + ".insert", fileManage);
    }

    public int update(FileManage fileManage) {
        return sqlSession.update(this.getClass().getName() + ".updateByPrimaryKeySelective", fileManage);
    }

    public int delete(String id) {
        return sqlSession.update(this.getClass().getName() + ".deleteByPrimaryKey", id);
    }

    public FileManage findById(String id) {
        return sqlSession.selectOne(this.getClass().getName() + ".selectByPrimaryKey", id);
    }

    public int getCount() {
        return sqlSession.selectOne(this.getClass().getName() + ".selectCount");
    }

    public List<FileManage> queryByCondition(String query, String order, Page page) {
        return sqlSession.selectList(this.getClass().getName() + ".selectByPage", page);
    }
}
