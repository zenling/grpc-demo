package com.izhaohu.service.serviceimpl;

import com.izhaohu.db.DB;
import com.izhaohu.model.Point;
import com.izhaohu.model.mapper.PointMapper;
import com.izhaohu.service.PointService;
import org.apache.ibatis.session.SqlSession;

public class PointServiceImpl implements PointService {
    @Override
    public int addPoint(Point point) {
        SqlSession sqlSession = DB.getSession();
        try {
            PointMapper pointMapper = sqlSession.getMapper(PointMapper.class);
            int toReturn = pointMapper.addPoint(point);
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

    @Override
    public int getPointsByUserId(int userId) {
        SqlSession sqlSession = DB.getSession();
        try {
            PointMapper pointMapper = sqlSession.getMapper(PointMapper.class);
            int toReturn = pointMapper.getPointByUserId(userId);
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
}
