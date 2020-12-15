class Car {

    int yearModel;
    String make;
    int speed;

    public void accelerate() {
        this.speed += 5;
    }

    public void brake() {
        if (this.speed - 5 <= 0) {
            this.speed = 0;
        } else {
            this.speed -= 5;
        }
    }
}