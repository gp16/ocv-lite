package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;
import ui.dockables.About;

public class WindowContainer extends JPanel
{
    public WindowContainer() {
	DockingWindow.registerDockableClass(About.class);
	
	setLayout(new GridLayout(0, 1));
	DockingWindow window = new DockingWindow(this);
	entangle(window, null, true);
    }
        
    public void entangle(Component window, Component with, boolean horizontal) {
	
	if(with == null) {
	    removeAll();
	    add(window, BorderLayout.CENTER);
	}
	else {
	    Component parent = with.getParent();

	    if(parent instanceof WindowContainer) {
		Component[] children = getComponents();
		removeAll();
		
		DirectedPane pane = new DirectedPane(horizontal);
		
		for (Component children1 : children) {
		    pane.insertAfter(children1, null);
		}
		
		pane.insertAfter(window, children[children.length - 1]);

		add(pane);
	    }
	    else if(parent instanceof DirectedPane) {
		DirectedPane parentPane = ((DirectedPane)parent);
		if(horizontal == parentPane.isHorizontal()) {
		    parentPane.insertAfter(window, with);
		}
		else {
		    Component sibling = parentPane.getPreviousSibling(with);
		    parentPane.removeComp(with);
		    DirectedPane newPane = new DirectedPane(horizontal);
		    newPane.insertAfter(with, null);
		    newPane.insertAfter(window, with);
		    
		    parentPane.insertAfter(newPane, sibling);
		}
	    }
	    else {
		System.out.println("Error!");
	    }
	}
    }
    
    public void removeWindow(Component window) {
	if(window.getParent().getParent() == this) {
	    DirectedPane parent = (DirectedPane) window.getParent();
	    parent.removeComp(window);
	    
	    if(parent.getChildrenCount() < 3) {
		remove(parent);
		add(parent.getComponent(0));
	    }
	}
	else if(window.getParent() instanceof DirectedPane) {
	    
	    DirectedPane parent = (DirectedPane) window.getParent();
	    parent.removeComp(window);
	    
	    if(parent.getChildrenCount() < 3) {

		if(parent.getParent() instanceof DirectedPane) {
		    DirectedPane grandparent = (DirectedPane) parent.getParent();
		    Component uncle = grandparent.getPreviousSibling(parent);
		    grandparent.removeComp(parent);
		    grandparent.insertAfter(parent.getComponent(0), uncle);
		    grandparent.revalidate();
		    grandparent.repaint();
		}
	    }
	    
	    parent.revalidate();
	    parent.repaint();
	}
    }
}

class DockingWindow extends JPanel {

    private final WindowContainer CONT;
    static final List<Class> dockableClasses = new ArrayList<>();
    private Dockable currentDockable = null;
    private final DockingComboBox combobox;
    private final JPanel navigation = new JPanel();
    
    public DockingWindow(WindowContainer container) {
	
	setLayout(new GridBagLayout());
	combobox = new DockingComboBox(this);
	CONT = container;
	
	setBorder(BorderFactory.createEtchedBorder());
	
	
	Component[] tempNavComps = navigation.getComponents();
	navigation.removeAll();
	navigation.add(combobox);
	for(int i = 0; i<tempNavComps.length; i++)
	    navigation.add(tempNavComps[i]);
	
	JPanel docking = new JPanel();	
	docking.add(new DockingButton(CONT, this, DockingButton.TYPE_DOUBLE_HOR));
	docking.add(new DockingButton(CONT, this, DockingButton.TYPE_DOUBLE_VER));
	docking.add(new DockingButton(CONT, this, DockingButton.TYPE_CLOSE));
	
	JPanel bar = new JPanel(new BorderLayout());
	bar.add(navigation, BorderLayout.WEST);
	bar.add(docking, BorderLayout.EAST);
	
	GridBagConstraints cons = new GridBagConstraints();
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.weightx = 1;
	cons.gridx = 0;
	cons.gridy = 0;
	cons.anchor = GridBagConstraints.NORTHWEST;
	add(bar, cons);
    }
    
    
    public static void registerDockableClass(Class clazz) {
	if(Dockable.class.isAssignableFrom(clazz)) {
	    dockableClasses.add(clazz);
	}
    }
    
    public void setDockable(Dockable dockable) {
	if(currentDockable != null) {
	    remove(currentDockable.getDockablePanel());
	    Component[] oldComps = navigation.getComponents();
	    for(int i = 0; i < oldComps.length; i++) {
		if(!(oldComps[i] instanceof DockingComboBox)) {
		    navigation.remove(oldComps[i]);
		}
	    }
	    currentDockable = null;
	}
	
	if(dockable == null)
	    return;
	
	GridBagConstraints cons = new GridBagConstraints();
	
	cons.gridy = 1;
	cons.weighty = 1;
	cons.fill = GridBagConstraints.BOTH;
	cons.anchor = GridBagConstraints.SOUTH;
	add(dockable.getDockablePanel(), cons);
	
	Component[] navComps = dockable.getNavigationComponents();
	if(navComps != null) {
	    for(int i=0; i<navComps.length; i++){
		navigation.add(navComps[i]);
		System.out.println("doing stuff!");
	    }
	}
	repaint();
	revalidate();
	
	this.currentDockable = dockable;
    }
    
    public int getComboBoxSelectedIndex() {
	return combobox.getSelectedIndex();
    }
    
    public void setComboBoxSelectedIndex(int index) {
	combobox.setSelectedIndex(index);
    }
}

class DockingComboBox extends JComboBox {
    
    public DockingComboBox(DockingWindow window) {
	
	addItemListener(new ItemListener()
	{
	    @Override
	    public void itemStateChanged(ItemEvent e)
	    {
		if(e.getStateChange() == ItemEvent.SELECTED) {
		    try {
			Class dockableClass = DockingWindow.dockableClasses.get(getSelectedIndex());
			Dockable dockable = (Dockable) dockableClass.newInstance();
			window.setDockable(dockable);
		    }
		    catch (InstantiationException | IllegalAccessException ex) {
			Logger.getLogger(DockingComboBox.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
	    }
	});
	
	reloadItems();
    }
    
    private void reloadItems() {
	removeAllItems();
	
	for (Class dockable : DockingWindow.dockableClasses) {
	    addItem(dockable.getSimpleName());
	}
    }
}

class DockingButton extends JButton {
    private static final ActionListener listener = new ActionListener()
    {
	@Override
	public void actionPerformed(ActionEvent e)
	{
	    if(e.getSource() instanceof DockingButton) {
		DockingButton button = (DockingButton)e.getSource();
		
		if(button.TYPE == TYPE_CLOSE) {
		    button.cont.removeWindow(button.window);
		}
		else {
		    DockingWindow newWindow = new DockingWindow(button.cont);
		    button.cont.entangle(newWindow, button.window, button.TYPE == TYPE_DOUBLE_HOR);
		    newWindow.setComboBoxSelectedIndex(button.window.getComboBoxSelectedIndex());
		}
		
		button.cont.repaint();
		button.cont.revalidate();
	    }
	}
    };
    
    public static final int TYPE_CLOSE = 0;
    public static final int TYPE_DOUBLE_VER = 1;
    public static final int TYPE_DOUBLE_HOR = 2;
        
    private final WindowContainer cont;
    private final DockingWindow window;
    private final int TYPE;
    
    public DockingButton(WindowContainer cont, DockingWindow panel, int type) {
	if(type == TYPE_CLOSE)
	    setText("x");
	else if(type == TYPE_DOUBLE_HOR)
	    setText("%");
	else if(type == TYPE_DOUBLE_VER)
	    setText("÷");
	
	this.cont = cont;
	this.window = panel;
	this.TYPE = type;

	addActionListener(listener);
    }
}

class DirectedPane extends JXMultiSplitPane
{
    private final MultiSplitLayout.Split model;
    private final ArrayList<MultiSplitLayout.Node> nodes;
    private final MultiSplitLayout layout;
    
    public DirectedPane(boolean horizontal) {
	
	model = new MultiSplitLayout.Split();
	model.setRowLayout(horizontal);
	layout = getMultiSplitLayout();
	nodes = (ArrayList<MultiSplitLayout.Node>) model.getChildren();
	
	layout.setFloatingDividers(true);
    }
    
    public void insertAfter(Component comp, Component after) {
	
	MultiSplitLayout.Leaf leaf = new MultiSplitLayout.Leaf("" + comp.hashCode());
	
	if(after == null) {
	    nodes.add(0, leaf);

	    if(nodes.size() > 1) {
		nodes.add(1, new MultiSplitLayout.Divider());
	    }
	}
	else {

	    MultiSplitLayout.Node afterNode = layout.getNodeForComponent(after);
	    	    
	    if(afterNode != null)
	    {
		int index = nodes.indexOf(afterNode);
		
		if(index >= 0) {
		    nodes.add(index+1, new MultiSplitLayout.Divider());
		    nodes.add(index+2, leaf);
		}
	    }
	}
	
	add(comp, "" + comp.hashCode());
	
	model.setChildren(nodes);
	layout.setModel(model);
    }
    
    public int getChildrenCount() {
	return nodes.size();
    }
    
    public int removeComp(Component comp) {
	MultiSplitLayout.Node node = layout.getNodeForComponent(comp);
	int index = nodes.indexOf(node);
	
	nodes.remove(index);
	
	if(index < nodes.size())
	    nodes.remove(index);
	else
	    nodes.remove(index-1);
	    
	model.setChildren(nodes);
	
	remove(comp);
	return index;
    }
    
    public Component getPreviousSibling(Component comp) {
	MultiSplitLayout.Node node = layout.getNodeForComponent(comp);
	
	if(node == null)
	    return null;
	
	int index = nodes.indexOf(node);
	
	if(index <= 0)
	    return null;
	
	return layout.getComponentForNode(nodes.get(index - 2));
    }
    
    public boolean isHorizontal() {
	return model.isRowLayout();
    }
}

