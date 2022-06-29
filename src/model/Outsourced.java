package model;

/** The Outsourced class contains fields and methods for working with Outsourced objects.
 Outsourced objects have six fields inherited from the Part class (id, name, price, stock, min, max) as well as companyName which is unique to Outsourced parts.
 */
public class Outsourced extends Part{

    //Declare fields for Outsourced class
    private String companyName;

    //Declare methods for Outsourced class
    /** This Outsourced object constructor has seven required parameters.
     All but companyName are inherited from the Part class. companyName is unique to Outsourced parts.
     * @param id is the unique part identifier
     * @param name is the part name
     * @param price is the part price
     * @param stock is the part inventory level
     * @param min is the part minimum inventory level
     * @param max is the part maximum inventory level
     * @param companyName is the part machineId
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {

        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
}
