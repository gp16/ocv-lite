package engine;

public class Parameter {
    public final String NAME;
    public final Type TYPE;
    public final int MIN;
    public final int MAX;
    public final String MAN;
    
    // is it optional
    public final boolean OPT;
    
    // is it recurring
    public final boolean RECURR;
    
    
    public Parameter(String name, Type type, int min, int max, String man, boolean opt, boolean recurr) {
        this.NAME = name;
        this.TYPE = type;
        this.MIN = min;
        this.MAX = max;
        this.MAN = man;
        
        this.OPT = opt;
        this.RECURR = recurr;
    }
}
