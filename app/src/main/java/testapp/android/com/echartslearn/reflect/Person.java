package testapp.android.com.echartslearn.reflect;

public class Person {
    private String name;
    public String sex;
    public int age;
    private int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void clear(){
        name = "";
        sex = "";
        age = 0;
        num = 0;
    }
}
