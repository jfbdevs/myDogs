package com.example.android.mydogs;

/**
 * Created by fplacido on 11/05/2016.
 */
class Dog {
    private String name;
    private float age;

    // constructor
    public Dog(String name, float age) {
        this.name = name;
        this.age = age;

    }

    public float getAge() {
        return this.age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setAge(int newAge) {
        this.age = newAge;
    }
}