package com.company;

import java.util.*;

abstract class Airplane implements Comparable<Airplane> {
    protected int maxSpeed;

    public Airplane(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public int compareTo(Airplane other) {
        return Integer.compare(this.maxSpeed, other.maxSpeed);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Airplane airplane = (Airplane) obj;
        return maxSpeed == airplane.maxSpeed;
    }

    @Override
    public String toString() {
        return "Max Speed: " + maxSpeed + " km/h";
    }
}

class CommercialAirplane extends Airplane {
    private int passengerSeats;

    public CommercialAirplane(int maxSpeed, int passengerSeats) {
        super(maxSpeed);
        this.passengerSeats = passengerSeats;
    }

    @Override
    public String toString() {
        return super.toString() + ", Passenger Seats: " + passengerSeats;
    }
}

class MilitaryAirplane extends Airplane {
    private boolean hasWeapons;

    public MilitaryAirplane(int maxSpeed, boolean hasWeapons) {
        super(maxSpeed);
        this.hasWeapons = hasWeapons;
    }

    @Override
    public String toString() {
        return super.toString() + ", Has Weapons: " + hasWeapons;
    }
}

class CargoAirplane extends Airplane {
    private int cargoCapacity;

    public CargoAirplane(int maxSpeed, int cargoCapacity) {
        super(maxSpeed);
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public String toString() {
        return super.toString() + ", Cargo Capacity: " + cargoCapacity + " kg";
    }
}



public class Main {
    public static void main(String[] args) {
        List<Airplane> airplanes = new ArrayList<>();
        airplanes.add(new CommercialAirplane(900, 200));
        airplanes.add(new MilitaryAirplane(1200, true));
        airplanes.add(new CargoAirplane(700, 50000));

        Collections.sort(airplanes);

        for (Airplane airplane : airplanes) {
            System.out.println(airplane);
        }
    }
}

