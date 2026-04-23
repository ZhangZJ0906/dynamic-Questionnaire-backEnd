package com.example.quiz_1150119.response;

public class GetUserRes extends BasicRes {
    private String email;

    private String name;

    private String phone;

    private int age;

    public GetUserRes() {
        super(); // 呼叫父類別的無參數建構子
    }

    public GetUserRes(String message, int code) {
        super(message, code);
        // TODO Auto-generated constructor stub
    }

    // 2. 全欄位建構子
    public GetUserRes(String message, int code, String email, String name, String phone, int age) {
        super(message, code);
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
