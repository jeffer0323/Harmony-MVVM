package com.eholee.harmony_mvvm.repository;

/**
 * Author : Jeffer
 * Date : 2021/6/15
 * Desc :
 */
public class User {
    public String name;
    public int age;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
