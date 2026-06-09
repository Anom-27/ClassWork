abstract class Vehicle {
    String vehicleId;
    double weightCapacity;
    Vehicle(String vehicleId, double weightCapacity) {
        this.vehicleId      = vehicleId;
        this.weightCapacity = weightCapacity;
        System.out.println("[Vehicle] Registered: " + vehicleId);
    }
    abstract void deliver(String destination, double packageWeight);
    void showInfo() {
        System.out.println("ID: " + vehicleId + " | Capacity: " + weightCapacity + "kg");
    }
}
class DroneVehicle extends Vehicle {
    int batteryLevel; 
    DroneVehicle(String vehicleId, double weightCapacity, int batteryLevel) {
        super(vehicleId, weightCapacity);     
        this.batteryLevel = batteryLevel;
        System.out.println("[DroneVehicle] Battery: " + batteryLevel + "%");
    }
    @Override
    void deliver(String destination, double packageWeight) {
        if (batteryLevel <= 0) {
            System.out.println("❌ " + vehicleId + ": Battery dead! Cannot deliver.");
            return;
        }
        if (packageWeight > weightCapacity) {
            System.out.println("❌ Package too heavy! Max: " + weightCapacity + "kg");
            return;
        }
        batteryLevel -= 20;
        System.out.println("✅ Delivered to " + destination + " | Battery left: " + batteryLevel + "%");
    }
    void recharge() {
        batteryLevel = 100;
        System.out.println("🔋 " + vehicleId + " recharged to 100%");
    }
}
class ExpressDrone extends DroneVehicle {
    int priorityLevel;
    ExpressDrone(String vehicleId, double weightCapacity,
                 int batteryLevel, int priorityLevel) {
        super(vehicleId, weightCapacity, batteryLevel); 
        this.priorityLevel = priorityLevel;
        System.out.println("[ExpressDrone] Priority Level: " + priorityLevel);
    }
    @Override
    void deliver(String destination, double packageWeight) {
        if (batteryLevel <= 0) {
            System.out.println("❌ " + vehicleId + ": Battery dead! Cannot deliver.");
            return;
        }
        batteryLevel -= (20 * priorityLevel);
        if (batteryLevel < 0) batteryLevel = 0;
        double charge = packageWeight * priorityLevel * 50;
        System.out.println("⚡ EXPRESS delivery to " + destination + " | Charge: Tk " + charge + 
                            " | Battery left: " + batteryLevel + "%");
    }
    void showPriority() {
        String label = priorityLevel == 3 ? "URGENT" :
                       priorityLevel == 2 ? "FAST" : "NORMAL";
        System.out.println("Priority: " + label + " (Level " + priorityLevel + ")");
    }
}
public class DeliverySystem {
    public static void main(String[] args) {
        System.out.println("=== Fleet Registration ===");
        Vehicle[] fleet = {
            new DroneVehicle("DRN-01", 5.0, 100),
            new ExpressDrone("EXP-01", 3.0, 100, 3)
        };
        System.out.println("\n=== Deliveries ===");
        fleet[0].deliver("Mirpur", 2.5);
        fleet[0].deliver("Dhanmondi", 2.5);
        fleet[0].deliver("Gulshan", 2.5);     
        fleet[0].deliver("Uttara", 2.5);
        fleet[0].deliver("Banani", 2.5);    
        System.out.println();
        fleet[1].deliver("Chittagong", 1.0);  
        System.out.println("\n=== Downcasting ===");
        for (Vehicle v : fleet) {
            if (v instanceof ExpressDrone) {
                ExpressDrone e = (ExpressDrone) v; 
                e.showPriority();
                e.recharge();                      
            }
        }
        System.out.println("\n=== Fleet Status ===");
        for (Vehicle v : fleet) {
            v.showInfo();
        }
    }
}