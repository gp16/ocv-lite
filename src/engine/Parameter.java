package engine;

/**
 * Represents a type that a command expects.
 * 
 * @see ICommand#getParams() 
 */
public class Parameter {
    public final String NAME;
    public final Type TYPE;
    public final Integer MIN;
    public final Integer MAX;
    public final String MAN;
    
    // is it optional
    public final boolean OPT;
    
    // is it recurring
    public final boolean RECURR;
    
    
    public Parameter(String name, Type type, Integer min, Integer max, String man, boolean opt, boolean recurr) {
        this.NAME = name;
        this.TYPE = type;
        this.MIN = min;
        this.MAX = max;
        this.MAN = man;
        
        this.OPT = opt;
        this.RECURR = recurr;
    }
}
