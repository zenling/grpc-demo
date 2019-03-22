package com.izhaohu.point.test;

import com.izhaohu.model.Point;
import com.izhaohu.service.PointService;
import com.izhaohu.service.serviceimpl.PointServiceImpl;

public class PointServiceTest {
    public static void main(String[] args) {
        PointService pointService = new PointServiceImpl();
//        pointService.addPoint(new Point(10, 1));

        int point = pointService.getPointsByUserId(1);
        System.out.println("user point:"+point);
    }

}
