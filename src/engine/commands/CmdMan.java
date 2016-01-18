/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.commands;

import engine.AbstractCommand;
import engine.Parameter;
import engine.Engine;
import engine.ICommand;
import engine.Type;
/**
 *
 * @author Amr_Ayman
 */
public class CmdMan extends AbstractCommand
{
    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]
        {
            new Parameter("command", Type.CMD_ID, 1, null, "command name", false, false)
        };
    }

    @Override
    protected Object executeSafe() 
    {
       String cmd=getArgCmdId("command", 0);
       ICommand command=Engine.getInstance().getCommand(cmd);
       return command.getFullMan();
    }

    @Override
    public String getName() {
        return "man";
    }

    @Override
    public String getMan() {
        return "manual of the command";
    }

}
