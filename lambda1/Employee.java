public class Employee {
private String lname;
private String fname;
private Integer age;private Double salary;
public Employee(String lname, String fname, Integer age,
Double salary) {
this.lname = lname;
this.fname = fname;
this.age = age;
this.salary = salary;
}
public String getLname() {
return lname;
}
public String getFname() {
return fname;
}
public Integer getAge() {
return age;
}
public Double getSalary() {
return salary;
}
public void setSalary(Double salary) {
this.salary = salary;
}
@Override
public String toString() {
return lname + " " + fname;
}
}