package model;

/** The InHouse class contains fields and methods for working with InHouse objects.
 InHouse objects have six fields inherited from the Part class (id, name, price, stock, min, max) as well as machineId which is unique to InHouse parts.
 */
public class InHouse extends Part{

    //Declare fields for InHouse class
    private int machineId;

    //Declare methods for InHouse class
    /** This InHouse object constructor has seven required parameters.
     All but machineId are inherited from the Part class. machineId is unique to parts created in house.
     * @param id is the unique part identifier
     * @param name is the part name
     * @param price is the part price
     * @param stock is the part inventory level
     * @param min is the part minimum inventory level
     * @param max is the part maximum inventory level
     * @param machineId is the part machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public int getMachineId() {

        return machineId;
    }

    /**
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {

        this.machineId = machineId;
    }
}
