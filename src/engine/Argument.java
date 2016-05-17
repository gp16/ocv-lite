package engine;

public class Argument
{
    public final Type TYPE;
    private final Object VALUE;
    
    public Argument(Type type, Object value) {
	TYPE = type;
	VALUE = value;
    }

    /**
     * Infers TYPE based on the given value.
     * @param value 
     */
    public Argument(Object value) {
	VALUE = value;
	
	if(value instanceof Double || value instanceof Float)
	    TYPE = Type.FLOAT;
	else if (value instanceof Integer)
	    TYPE = Type.INT;
	else
	    TYPE =  Type.STR;
    }
    
    @Override
    public String toString() {
	return (String) VALUE;
    }
    
    public Integer toInt() {
	return (Integer) VALUE;
    }
    
    public Double toDouble() {
	if(VALUE instanceof Double)
	    return (Double) VALUE;
	else if(VALUE instanceof Integer)
	    return new Double((Integer) VALUE);
	else return null;
    }
}
