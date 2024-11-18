package pl.kacperszot.trafficcontrol.model.trafficlight;

public enum TrafficLightSignal {
    RED, // You must wait
    RED_YELLOW,//you wait but it's about to turn green
    GREEN,//you can go
    YELLOW,//you must, wait it's about to turn red
}
