package Zoo;

public class Mammal extends Animal {
    private String furColor;

    public Mammal(String name, int age, String furColor) {
        super(name, age);
        this.furColor = furColor;
    }

    public String getFurColor() {
        return furColor;
    }

    public void setFurColor(String furColor) {
        this.furColor = furColor;
    }

    @Override
    public String makeSound() {
        return "The mammal makes a sound.";
    }

    @Override
    public String getInfo() {
        return String.format("Name: %s, Age: %d, Fur Color: %s",
                getName(), getAge(), furColor == null || furColor.isEmpty() ? "Unknown" : furColor);
    }

}
