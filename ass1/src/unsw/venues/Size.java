package unsw.venues;

public enum Size {
    SMALL("small"),MEDIUM("medium"), LARGE("large");

    private String value;


    Size(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }

    public static Size check(String value) {
        value = value.toLowerCase();
        for (Size size: Size.values()) {
            if(size.getValue().equals(value)) {
               return size; 
            }
        }
        return null;
    }
}