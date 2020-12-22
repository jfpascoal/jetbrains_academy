class Time {

    int hours;
    int minutes;
    int seconds;

    public Time(int hours) {
        this(hours, 0, 0);
    }

    public Time(int hours, int minutes) {
        this(hours, minutes, 0);
    }

    public Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }
}