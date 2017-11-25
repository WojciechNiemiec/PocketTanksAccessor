package com.wniemiec.cheat.ptanks;

public class Tank {

    private TankAccessor invoker;

    public Tank(TankAccessor invoker) {
        this.invoker = invoker;
    }

    public Position getPosition() {
        return new Position(readX(), readY());
    }

    public void setPosition(Position position) {
        writeX(position.x);
        writeY(position.y);
    }

    public double getAngle() {
        return invoker.get(PocketTanksAccessor::getTankAngle);
    }

    public void setAngle(double value) {
        invoker.set(PocketTanksAccessor::setTankAngle, value);
    }

    public double getPower() {
        return invoker.get(PocketTanksAccessor::getTankPower);
    }

    public void setPower(double value) {
        invoker.set(PocketTanksAccessor::setTankPower, value);
    }

    private double readX() {
        return invoker.get(PocketTanksAccessor::getTankPositionX);
    }

    private double readY() {
        return invoker.get(PocketTanksAccessor::getTankPositionY);
    }

    private void writeX(double value) {
        invoker.set(PocketTanksAccessor::setTankPositionX, value);
    }

    private void writeY(double value) {
        invoker.set(PocketTanksAccessor::setTankPositionY, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tank tank = (Tank) o;

        return invoker == tank.invoker;
    }

    @Override
    public int hashCode() {
        return invoker != null ? invoker.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Tank{" +
                "position=" + getPosition() + ", " +
                "angle=" + getAngle() + ", " +
                "power=" + getPower() +
                '}';
    }
}
