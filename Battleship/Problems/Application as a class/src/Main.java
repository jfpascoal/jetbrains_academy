class Application {

    String name;

    void run(String[] args) {
        System.out.println(this.name);
        for (String par : args) {
            System.out.println(par);
        }
    }
}