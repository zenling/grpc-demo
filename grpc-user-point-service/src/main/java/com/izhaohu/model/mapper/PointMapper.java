package com.izhaohu.model.mapper;

import com.izhaohu.model.Point;

public interface PointMapper {
    public int addPoint(Point point) throws Exception;

    public int getPointByUserId(int userId) throws Exception;
}
