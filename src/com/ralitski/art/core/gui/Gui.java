package com.ralitski.art.core.gui;

import java.awt.Button;
import java.awt.Component;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;

import com.ralitski.art.core.Controller;
import com.ralitski.art.core.Task;
import com.ralitski.art.core.event.EventHandler;
import com.ralitski.art.core.event.Listener;
import com.ralitski.art.core.events.EventCommand;
import com.ralitski.art.core.events.EventShutdown;

/**
 * add separate windows for loaded classes and scripts (which hook onto controller and refresh when new stuff is loaded)
 */
public class Gui implements Task, Listener {
	
	private volatile boolean running;
	private GuiFrame frame;
	private Path path;
	
	private Controller controller;
	
	public Gui(Controller controller) {
		this.controller = controller;
	}

	public int getWidth() {
		return 100;
	}

	public int getHeight() {
		return getWidth();
	}
	
	public GuiFrame getFrame() {
		return frame;
	}

	public boolean running() {
		return running;
	}

	public void stop() {
		running = false;
	}
	
	public void setup() {
		
		controller.getEventSystem().addHandler(this);
		
		frame = new GuiFrame(this);
		frame.setup();
		
		path = new Path(frame.getFrame());
		
		Panel pMain = new Panel();
		pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));
		addComponent("main", pMain);
		addComponent("main/label", new Label("Options:"));

		Button btnExtract = new Button("Extract Classes");
		btnExtract.addActionListener(new CmdListener("extract code"));
		addComponent("main/extractClasses", btnExtract);

		Button btnLoadClasses = new Button("Load Classes");
		btnLoadClasses.addActionListener(new CmdListener("load code"));
		addComponent("main/loadClasses", btnLoadClasses);

		Button btnExtractScripts = new Button("Extract Scripts");
		btnExtractScripts.addActionListener(new CmdListener("extract script"));
		addComponent("main/extractScripts", btnExtractScripts);

		Button btnLoadScripts = new Button("Load Scripts");
		btnLoadScripts.addActionListener(new CmdListener("load script"));
		addComponent("main/loadScripts", btnLoadScripts);

		Button btnLoadSettings = new Button("Reload Settings");
		btnLoadSettings.addActionListener(new CmdListener("settings load"));
		addComponent("main/reloadSettings", btnLoadSettings);
		
		Button btnSaveSettings = new Button("Save Settings");
		btnSaveSettings.addActionListener(new CmdListener("settings save"));
		addComponent("main/saveSettings", btnSaveSettings);
		frame.getFrame().pack();
	}
	
	public Component addComponent(String path, Component c) {
		return this.path.addComponent(path, c);
	}

	public void start() {
		if(running) {
			throw new IllegalStateException("Gui is already running");
		} else {
			running = true;
			while(running && frame.running()) {
				//loop
			}
			frame.stop();
			running = false;
			frame = null;
		}
	}
	
	private class CmdListener implements ActionListener {
	
	  private String cmd;
	
	  CmdListener(String cmd) {
	    this.cmd = cmd;
	  }

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.dispatchCommand(cmd);
		}
	}

	@Override
	public boolean isActive() {
		return running;
	}
	
	@EventHandler(EventShutdown.class)
	public void onShutdown() {
		stop();
	}
	
	@EventHandler(EventCommand.class)
	public void onCommand(EventCommand command) {
		if(!command.isPreExecution()) {
			if(command.getCommand().equals("load code")) {
				controller.dispatchCommand("gui code");
			} else if(command.getCommand().equals("load script")) {
				controller.dispatchCommand("gui script");
			}
		}
	}
	
}
