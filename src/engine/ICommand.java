package engine;

public interface ICommand
{
    public String getName();
    public String getMan();
    public Parameter[] getParams();
    public Object execute(Object... arguments);
}