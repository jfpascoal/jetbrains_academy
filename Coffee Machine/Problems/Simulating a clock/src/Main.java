class Clock {

    int hours = 12;
    int minutes = 0;

    void next() {
        if (this.minutes == 59) {
            this.minutes = 0;
            this.nextHour();
        } else {
            this.minutes += 1;
        }
    }

    public void nextHour() {
        if (this.hours == 12) {
            this.hours = 1;
        } else {
            this.hours += 1;
        }
    }
}