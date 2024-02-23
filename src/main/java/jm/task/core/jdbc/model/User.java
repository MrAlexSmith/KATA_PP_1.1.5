package jm.task.core.jdbc.model;

public class User {
    private Long id;
    private String name;
    private String lastName;
    private Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nUser{")
                     .append("id=")
                     .append(id)
                     .append(" name='")
                     .append(name)
                     .append('\'')
                     .append(" lastName='")
                     .append(lastName)
                     .append('\'')
                     .append(" age=").append(age).append("}");
        return stringBuilder.toString();
    }
}
