package code;
import javax.swing.*;
import java.util.Scanner;
import java.util.ArrayList;
public class StudentList {
    public static void main(String[] args) {
        int num = 0;
        ArrayList<Student> list=new ArrayList<Student>();
        if(login()==false)
            return;
        while (true) {
            System.out.println("欢迎进入本系统");
            System.out.println("------------");
            System.out.println("请按1录入学生信息");
            System.out.println("请按2删除学生信息");
            System.out.println("请按3查询学生信息");
            System.out.println("请按4浏览全部学生信息");
            System.out.println("请按5修改学生信息");
            System.out.println("请按任意数字健退出");
            System.out.println("------------------");
            Scanner sc = new Scanner(System.in);
            num = sc.nextInt();
            switch (num) {
                case 1:
                    insert(list);   break;
                case 2:
                    delete(list);   break;
                case 3:
                    search(list);   break;
                case 4:
                    look(list);     break;
                case 5:
                    change(list);   break;
                default:
                    System.out.println("使用结束，即将退出");
                    return;
            }
        }
    }
    //登录账号密码验证程序
    public static boolean login(){
        Scanner sc=new Scanner(System.in);
        String username=new String("admin");
        String key=new String("admin");
        int times=0;
        while(times<3){
            System.out.println("请输入用户名，初始用户名为admin");
            String username1=sc.nextLine();
            System.out.println("请输入密码，初始密码为admin");
            String key1=sc.nextLine();
            if(username1.equals(username)&&key1.equals(key)){
                System.out.println("登陆成功");
                return true;
            }
            else{
                times++;
                System.out.println("输出有误，请重新输入 "+"("+(3-times)+"次错误之后账户将被锁定"+")");
            }
        }
        System.out.println("密码错误次数过多,账户已被锁定");
        return false;
    }
    //    判断名字是否相同
    public static boolean boolName(ArrayList<Student>list,String name){
        boolean flag=false;
        for(int i=0;i<list.size();i++){
            if(list.get(i).name.equals(name)) {
                flag = true;
            }
        }
        return flag;
    }
    //判断学号是否相同
    public static boolean boolID(ArrayList<Student>list,String ID){
        boolean flag=false;
        for(int i=0;i<list.size();i++){
            if(list.get(i).id.equals(ID)) {
                flag = true;
            }
        }
        return flag;
    }
    //录入信息
    public static void insert(ArrayList<Student>list){
        Scanner sc=new Scanner(System.in);
        Student s=new Student();
        while(true){
            System.out.println("请输入姓名");
            s.name=sc.nextLine();
            System.out.println("请输入学号");
            s.id=sc.nextLine();
            if(boolName(list,s.name)&&boolID(list,s.id))
                System.out.println("学号或姓名重复请重新输入");
            else
                break;

        }
        list.add(s);
        System.out.println("添加成功");
    }
    //删除信息
    public static void delete(ArrayList<Student>list){
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入您想删除的的学生姓名");
        String ne=sc.nextLine();
        for(int i=0;i<list.size();i++){
            if(list.get(i).name.equals(ne)){
                list.remove(i);
                return;
            }
        }
        System.out.println("查无此人");
        return;
    }
    //查找函数
    public static void search(ArrayList<Student>list){
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入您想查询的的学生姓名");
        String ne=sc.nextLine();
        for(int i=0;i<list.size();i++){
            if(list.get(i).name.equals(ne)){
                list.get(i).display();
                return;
            }
        }
        System.out.println("查无此人");
        return;
    }
    //修改函数，并且可以判断姓名或学号重复问题
    public static void change(ArrayList<Student>list){
        int position=-1;
        System.out.println("请输入您想修改的学生名字");
        Scanner sc=new Scanner(System.in);
        String NAME=sc.nextLine();
        for(int i=0;i<list.size();i++)
            if(list.get(i).name.equals(NAME)){
                position=i;
                break;
            }
        if(position==-1){
            System.out.println("查无此人");
            return;
        }
        Student s=list.get(position);
        list.remove(position);
        while(true){
            System.out.println("请输入新名字");
            String name1=sc.nextLine();
            System.out.println("请输入新学号");
            String id1=sc.nextLine();
            if((boolName(list,name1)==false)&&(boolID(list,id1)==false)){
                list.get(position).id=id1;
                list.get(position).name=name1;
                System.out.println("修改成功");
                return;
            }
            System.out.println("该名字或学号已经被使用，请重新输入");
        }
    }
    //浏览函数
    public static void look(ArrayList<Student>list){
        for(int i=0;i<list.size();i++)
            list.get(i).display();
    }

}

