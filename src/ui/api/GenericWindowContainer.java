package ui.api;

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
import org.jdesktop.swingx.MultiSplitLayout.Divider;
import org.jdesktop.swingx.MultiSplitLayout.Leaf;
import org.jdesktop.swingx.MultiSplitLayout.Node;
import org.jdesktop.swingx.MultiSplitLayout.Split;
import ui.dockables.About;
import ui.dockables.Command;
import ui.dockables.Image;

public class GenericWindowContainer extends JPanel {
        
    public static void registerDockableClass(Class clazz) {
	DockingWindow.registerDockableClass(clazz);
    }
    
    static {
	registerDockableClass(About.class);
	registerDockableClass(Command.class);	
        registerDockableClass(Image.class);
    }
    
    public GenericWindowContainer() {
	
	// so that the child fills parent
	setLayout(new GridLayout(0, 1));
	
	// initial docking window
	DockingWindow window = new DockingWindow(this);
	
	// no other windows to entagle it with
	entangle(window, null, true);
    }
    
    public final void entangle(Component window, Component with, boolean horizontal) {

	if(with == null) {
	    // can't entagle with nothing, must remove all other comps
	    removeAll();
	    add(window);
	}
	else {
	    // the caller intends to entagle with something
	    
	    Component parent = with.getParent();


	    if(parent == this) {
		// the parent is us and accordingly, a GeneriWindowContainer
		
		// remove all children
		Component[] children = getComponents();
		removeAll();
		
		// create a new directed container to host the removed children
		DirectedWindowContainer directedWindowContainer = new DirectedWindowContainer(horizontal);
		for (Component child : children)
		    directedWindowContainer.insertAfter(child, null);
		
		// append the new window to the new container, i.e. after the
		// old children
		directedWindowContainer.insertAfter(window, children[children.length - 1]);
		
		// add new container on us
		add(directedWindowContainer);
	    }
	    else if(parent instanceof DirectedWindowContainer) {
		// parent is not us and is a directed container
		
		DirectedWindowContainer parentDirectedContainer = ((DirectedWindowContainer)parent);
		if(horizontal == parentDirectedContainer.isHorizontal()) {
		    // caller wants to be entagled in the same direction as parent
		    // simply append it
		    parentDirectedContainer.insertAfter(window, with);
		}
		else {
		    // caller wants to be entageled in a different direction;
		    // this means we should create a new directed container with
		    // that new direction.
		    DirectedWindowContainer newDirectedCont = new DirectedWindowContainer(horizontal);
		    
		    // get reference to the sibling because the new directed container
		    // will be entangled with it to componsate the removed window
		    Component sibling = parentDirectedContainer.getPreviousSibling(with);
		    
		    // now we can remove that window
		    parentDirectedContainer.removeComp(with);
		    
		    // ... to add it later on the new directed container
		    newDirectedCont.insertAfter(with, null);
		    
		    // ... but don't forget to append the new window, that's the
		    // point of the whole method
		    newDirectedCont.insertAfter(window, with);
		    
		    // so far, some comps where removed from parent container and
		    // we never inserted to it, that's the time to do so
		    parentDirectedContainer.insertAfter(newDirectedCont, sibling);
		}
	    }
	    else {
		// oops, unexpected parent
		
		//TODO: throw a exception
		System.out.println("Error!");
	    }
	}
    }
    
    public void removeWindow(Component window) {
	if(window.getParent().getParent() == this) {
	    // grandparent is us and we can only have one child, no more, no less.

	    DirectedWindowContainer parent = (DirectedWindowContainer) window.getParent();
	    parent.removeComp(window);
	    
	    if(parent.getChildrenCount() < 3) {
		remove(parent);
		add(parent.getComponent(0));
	    }
	}
	else if(window.getParent() instanceof DirectedWindowContainer) {
	    
	    DirectedWindowContainer parent = (DirectedWindowContainer) window.getParent();
	    parent.removeComp(window);
	    
	    
	    if(parent.getChildrenCount() < 3) {
		// parent is useless now, it has one window and maybe a divider
		// so it should be disolved, i.e. removed and having its children
		// hosted by grandparent
		
		if(parent.getParent() instanceof DirectedWindowContainer) {
		    DirectedWindowContainer grandparent = (DirectedWindowContainer) parent.getParent();
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

class DirectedWindowContainer extends JXMultiSplitPane {
    
    private final MultiSplitLayout.Split model;
    private final ArrayList<MultiSplitLayout.Node> nodes;
    private final MultiSplitLayout layout;
    
    public DirectedWindowContainer(boolean horizontal) {
	model = new Split();
	model.setRowLayout(horizontal);
	layout = getMultiSplitLayout();
	nodes = (ArrayList<Node>) model.getChildren();
    }
    
    public boolean isHorizontal() {
	return model.isRowLayout();
    }
    
    public void insertAfter(Component comp, Component after) {
	
	// create a leaf which name is as the hash code of its content
	Leaf leaf = new Leaf(Integer.toString(comp.hashCode()));
	
	if(after == null) {
	    // caller itends to prepend
	    
	    nodes.add(0, leaf);

	    // add a divider between the newly inserted comp and the previous
	    // comp, if any
	    if(nodes.size() > 1) {
		nodes.add(1, new Divider());
	    }
	}
	else {
	    // caller intends to insert after something
	    
	    Node afterNode = layout.getNodeForComponent(after);
	    
	    
	    if(afterNode != null)
	    {
		// a leaf for that comp was found
		
		int index = nodes.indexOf(afterNode);
		
		if(index >= 0) {
		    nodes.add(index+1, new Divider());
		    nodes.add(index+2, leaf);
		}
	    }
	}
	
	// assocciate comp with the leaf we just created
	add(comp, Integer.toString(comp.hashCode()));
	
	// update model and layout
	model.setChildren(nodes);
	layout.setModel(model);
    }
    
    public int removeComp(Component comp) {
	Node node = layout.getNodeForComponent(comp);
	int index = nodes.indexOf(node);
	
	nodes.remove(index);
	
	// remove divider
	if(index < nodes.size())
	    nodes.remove(index);
	else
	    // last node is a special case, we remove the previous divider
	    nodes.remove(index-1);
	    
	model.setChildren(nodes);
	
	remove(comp);
	return index;
    }
    
    public Component getPreviousSibling(Component comp) {
	Node node = layout.getNodeForComponent(comp);
	
	if(node == null)
	    return null;
	
	int index = nodes.indexOf(node);
	
	if(index <= 0)
	    return null;
	
	return layout.getComponentForNode(nodes.get(index - 2));
    }

    public int getChildrenCount() {
	return nodes.size();
    }    
}

class DockingWindow extends JPanel {

    private final GenericWindowContainer genericWindowContainer;
    static final List<Class> dockableClasses = new ArrayList<>();
    private Dockable currentDockable = null;
    private final DockingComboBox combobox;
    private final JPanel navigation = new JPanel();
    
    public DockingWindow(GenericWindowContainer container) {
	
	setLayout(new GridBagLayout());
	setBorder(BorderFactory.createEtchedBorder());
	combobox = new DockingComboBox(this);
	genericWindowContainer = container;
	
	// add combobox before anything in the navigation panel
	Component[] tempNavComps = navigation.getComponents();
	navigation.removeAll();
	navigation.add(combobox);
	for(int i = 0; i<tempNavComps.length; i++)
	    navigation.add(tempNavComps[i]);
	
	// create close and double buttons
	JPanel docking = new JPanel();	
	docking.add(new DockingButton(genericWindowContainer, this, DockingButton.TYPE_DOUBLE_HOR));
	docking.add(new DockingButton(genericWindowContainer, this, DockingButton.TYPE_DOUBLE_VER));
	docking.add(new DockingButton(genericWindowContainer, this, DockingButton.TYPE_CLOSE));
	
	// top bar
	JPanel bar = new JPanel(new BorderLayout());
	bar.add(navigation, BorderLayout.WEST);
	bar.add(docking, BorderLayout.EAST);
	
	// align it to top line
	GridBagConstraints cons = new GridBagConstraints();
	cons.weightx = 1;
	cons.gridx = 0;
	cons.gridy = 0;
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.anchor = GridBagConstraints.NORTHWEST;
	add(bar, cons);
    }
    
    public static void registerDockableClass(Class clazz) {
	// clazz must implement Dockable
	if(Dockable.class.isAssignableFrom(clazz)) {
	    dockableClasses.add(clazz);
	}
    }
    
    public void setCurrentDockable(Dockable dockable) {
	if(currentDockable != null) {
	    remove(currentDockable.getDockablePanel());
	    Component[] oldComps = navigation.getComponents();
	    for(int i = 0; i < oldComps.length; i++) {
		if(oldComps[i] != combobox) {
		    navigation.remove(oldComps[i]);
		}
	    }
	    currentDockable = null;
	}
	
	if(dockable == null)
	    return;
	
	// make the panel take all the area not taken by top bar
	GridBagConstraints cons = new GridBagConstraints();	
	cons.gridy = 1;
	cons.weighty = 1;
	cons.fill = GridBagConstraints.BOTH;
	cons.anchor = GridBagConstraints.SOUTH;
	add(dockable.getDockablePanel(), cons);
	
	
	// append nav comps defined by interface implementors
	Component[] newNavComps = dockable.getNavigationComponents();
	if(newNavComps != null)
	    for (Component navComp : newNavComps)
		navigation.add(navComp);

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
	
	addItemListener(new ItemListener() {
	    @Override
	    public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
		    try {
			Class dockableClass = DockingWindow.dockableClasses.get(getSelectedIndex());
			Dockable dockable = (Dockable) dockableClass.newInstance();
			window.setCurrentDockable(dockable);
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
    public static final int TYPE_CLOSE = 0;
    public static final int TYPE_DOUBLE_VER = 1;
    public static final int TYPE_DOUBLE_HOR = 2;
        
    private final GenericWindowContainer cont;
    private final DockingWindow window;
    private final int TYPE;

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
    
    public DockingButton(GenericWindowContainer cont, DockingWindow panel, int type) {
	if(type == TYPE_CLOSE) {
	    setText("x");
	    setToolTipText("Close this window!");
	}
	else if(type == TYPE_DOUBLE_HOR) {
	    setText("%");
	    setToolTipText("Split this window horizontally!");
	}
	else if(type == TYPE_DOUBLE_VER) {
	    setText("÷");
	    setToolTipText("Split this window vertically!");
	}
	
	this.cont = cont;
	this.window = panel;
	this.TYPE = type;

	addActionListener(listener);
    }
}
