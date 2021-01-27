package code;

public class Student {
//变量
    String id;
    String name;
//构造函数
    Student(){}
    Student(String name,String id){
        this.name=name;
        this.id=id;
    }
//    功能函数
    public void display(){
        System.out.println(this.name+" "+this.id);
    }
}
