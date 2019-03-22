package com.izhaohu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_point")
@Entity
public class Point {
    private Integer id;
    private int amount;
    private Integer userId;

    public Point() {

    }

    public Point(Integer id, int amount, Integer userId) {
        this.id = id;
        this.amount = amount;
        this.userId = userId;
    }

    public Point( int amount, Integer userId) {
        this.amount = amount;
        this.userId = userId;
    }


    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", amount=" + amount +
                ", userId=" + userId +
                '}';
    }
}
