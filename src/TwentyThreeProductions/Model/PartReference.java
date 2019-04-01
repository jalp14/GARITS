package TwentyThreeProductions.Model;

import TwentyThreeProductions.Domain.Part;

public class PartReference {

    Part part;

    private static PartReference partReference = null;

    private PartReference() {}

    public static PartReference getInstance() {
        if(partReference == null) {
            partReference = new PartReference();
        }
        return partReference;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
}
