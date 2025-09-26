package Zoo;

public class Animal {
    private String name;
    private int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // getters / setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // methods
    public String makeSound() {
        return "The animal makes a sound.";
    }

    public String getInfo() {
        return String.format("Name: %s, Age: %d", name, age);
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
