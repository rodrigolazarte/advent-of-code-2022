package day16;


import java.util.*;

public class Valve {

    private final String name;
    private final long flowRate;
    private boolean isOpen;
    private Set<Valve> connectedValves;

    public Valve(String name, long flowRate) {
        this.name = name;
        this.flowRate = flowRate;
        connectedValves = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public long getFlowRate() {
        return flowRate;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Set<Valve> getConnectedValves() {
        return connectedValves;
    }

    public void setConnectedValves(Set<Valve> connectedValves) {
        this.connectedValves = connectedValves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valve valve = (Valve) o;
        return flowRate == valve.flowRate && isOpen == valve.isOpen && Objects.equals(name, valve.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, flowRate, isOpen);
    }
}
