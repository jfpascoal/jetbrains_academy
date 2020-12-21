class Cat {

    String name;
    int age;
    static int counter;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
        counter += 1;
        if (counter > 5) {
            System.out.println("You have too many cats");
        }
    }

    public static int getNumberOfCats() {
        return counter;
    }
}
/*
class Main {
    public static void main(String args[]) {
        Cat snowball = new Cat("Snowball", 4);
        System.out.printf("This is %s! You have %d cats.\n", snowball.name, Cat.getNumberOfCats());
        Cat fluffy = new Cat("Fluffy", 2);
        System.out.printf("This is %s! You have %d cats.\n", fluffy.name, Cat.getNumberOfCats());
        Cat tiger = new Cat("Tiger", 5);
        System.out.printf("This is %s! You have %d cats.\n", tiger.name, Cat.getNumberOfCats());
        Cat arthur = new Cat("Arthur", 8);
        System.out.printf("This is %s! You have %d cats.\n", arthur.name, Cat.getNumberOfCats());
        Cat purr = new Cat("Purr", 1);
        System.out.printf("This is %s! You have %d cats.\n", purr.name, Cat.getNumberOfCats());
        Cat clementine = new Cat("Clementine", 4);
        System.out.printf("This is %s! You have %d cats.\n", clementine.name, Cat.getNumberOfCats());
    }
}
 */