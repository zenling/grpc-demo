package com.izhaohu.service.serviceimpl;

import com.izhaohu.db.DB;
import com.izhaohu.model.User;
import com.izhaohu.model.mapper.UserMapper;
import com.izhaohu.service.UserService;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author zengling
 */
public class UserServiceImpl implements UserService {
    public int addUser(User user) {
        SqlSession sqlSession = DB.getSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            int toReturn = userMapper.addUser(user);
            sqlSession.commit();
            return toReturn;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();;
        }

        return 0;
    }

    public int updateUser(User user, int id) {
        SqlSession sqlSession = DB.getSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            int toReturn = userMapper.updateUser(user, id);
            sqlSession.commit();
            return toReturn;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();;
        }
        return 0;
    }

    public int deleteUser(int id) {
        SqlSession sqlSession = DB.getSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            int toReturn = userMapper.deleteUser(id);
            sqlSession.commit();
            return toReturn;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();;
        }
        return 0;
    }

    public User getUserById(int id) {
        SqlSession sqlSession = DB.getSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User toReturn = userMapper.getUserById(id);
            sqlSession.commit();
            return toReturn;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();;
        }
        return null;
    }

    public List<User> getUsers() {
        SqlSession sqlSession = DB.getSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<User> toReturn = userMapper.getUsers();
            sqlSession.commit();
            return toReturn;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();;
        }
        return null;
    }

    
}
