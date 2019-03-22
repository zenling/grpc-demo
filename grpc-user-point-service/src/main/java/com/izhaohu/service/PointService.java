package com.izhaohu.service;

import com.izhaohu.model.Point;

public interface PointService {

    public int addPoint(Point point);

    public int getPointsByUserId(int userId);
}
