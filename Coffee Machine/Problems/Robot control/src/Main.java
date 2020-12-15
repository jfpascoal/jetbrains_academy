class Move {
    public static void moveRobot(Robot robot, int toX, int toY) {
        Direction dir = robot.getDirection();
        int distY = toY - robot.getY();
        int distX = toX - robot.getX();

        if (distX == 0 && distY == 0) { // if already in position
        } else if (distY == 0) { // if only needs to move X
            robot = move(robot, getTurns(dir, distX, "X"), distX);
        } else if (distX == 0) { // if only needs to move Y
            robot = move(robot, getTurns(dir, distY, "Y"), distY);
        } else { // determine best course
            int xTurns = getTurns(dir, distX, "X");
            int yTurns = getTurns(dir, distY, "Y");
            if (xTurns < yTurns) { // if number of turns for X direction <= Y direction
                robot = move(robot, xTurns, distX);
                yTurns = getTurns(robot.getDirection(), distY, "Y"); // recalculate Y turns
                robot = move(robot, yTurns, distY);
            } else { // if number of turns for Y direction < X direction
                robot = move(robot, yTurns, distY);
                xTurns = getTurns(robot.getDirection(), distX, "X"); // recalculate X turns
                robot = move(robot, xTurns, distX);
            }
        }
    }

    private static Robot move(Robot robot, int turns, int dist) {
        switch (turns) {
            case 1:
                robot.turnRight();
                System.out.println("Turn right");
                break;
            case 2:
                robot.turnLeft();
                System.out.println("Turn left");
                break;
            case 3:
                robot.turnRight();
                System.out.println("Turn right");
                robot.turnRight();
                System.out.println("Turn right");
                break;
            default:
                break;
        }
        for (int i = 0; i < Math.abs(dist); i++) {
            robot.stepForward();
            System.out.println("Step forward");
        }
        return robot;
    }

    private static int getTurns(Direction currDir, int dist, String axis) {
        // determine change in direction
        //// 0: no change in direction
        //// 1: one turn right
        //// 2: one turn left
        //// 3: two turns either way
        switch (axis) {
            case "X":
                if (dist > 0) { // RIGHT
                    if (currDir == Direction.RIGHT) {
                        return 0;
                    } else if (currDir == Direction.UP) {
                        return 1;
                    } else if (currDir == Direction.LEFT) {
                        return 3;
                    } else {
                        return 2;
                    }
                } else if (dist < 0) { // LEFT
                    if (currDir == Direction.LEFT) {
                        return 0;
                    } else if (currDir == Direction.DOWN) {
                        return 1;
                    } else if (currDir == Direction.RIGHT) {
                        return 3;
                    } else {
                        return 2;
                    }
                } else {
                    return 0;
                }
            case "Y":
                if (dist > 0) { // UP
                    if (currDir == Direction.UP) {
                        return 0;
                    } else if (currDir == Direction.LEFT) {
                        return 1;
                    } else if (currDir == Direction.DOWN) {
                        return 3;
                    } else {
                        return 2;
                    }
                } else if (dist < 0) { // DOWN
                    if (currDir == Direction.DOWN) {
                        return 0;
                    } else if (currDir == Direction.RIGHT) {
                        return 1;
                    } else if (currDir == Direction.UP) {
                        return 3;
                    } else {
                        return 2;
                    }
                } else {
                    return 0;
                }
            default:
                return -1;
        }
    }
}

//Don't change code below

enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Direction turnLeft() {
        switch (this) {
            case UP:
                return LEFT;
            case DOWN:
                return RIGHT;
            case LEFT:
                return DOWN;
            case RIGHT:
                return UP;
            default:
                throw new IllegalStateException();
        }
    }

    public Direction turnRight() {
        switch (this) {
            case UP:
                return RIGHT;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            case RIGHT:
                return DOWN;
            default:
                throw new IllegalStateException();
        }
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }
}

class Robot {
    private int x;
    private int y;
    private Direction direction;

    public Robot(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void turnLeft() {
        direction = direction.turnLeft();
    }

    public void turnRight() {
        direction = direction.turnRight();
    }

    public void stepForward() {
        x += direction.dx();
        y += direction.dy();
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}