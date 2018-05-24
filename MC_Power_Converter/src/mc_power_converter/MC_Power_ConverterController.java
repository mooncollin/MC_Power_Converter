package mc_power_converter;

import javafx.event.ActionEvent;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.Scanner;

/**
 *
 * @author cmoon
 */
public class MC_Power_ConverterController
{
	private static final String SPLASHES_URL = "https://raw.githubusercontent.com/mooncollin/MC_Power_Converter/master/splashes.txt";
	
    @FXML
    private Label splashText;
    
    @FXML
    private Pane pane;
    
    @FXML
    private MenuButton menuButton;
    
    @FXML
    private TextField userInput;
    
    @FXML
    private TextField mj;
    
    @FXML
    private TextField j;
    
    @FXML
    private TextField rf;
    
    @FXML
    private TextField eu;
    
    @FXML
    private TextField ae;
    
    @FXML
    private TextField w;
    
    @FXML
    private TextField t;
    
    @FXML
    private TextField fe;
    
    private String selected;
    
    private SplashThread splashThread;
    
    private List<String> lines;
    
    @FXML
    public void splashButton(ActionEvent event)
    {
    	splashText.setText(getRandomSplash());
    }
    
    @FXML
    public void menuButton(ActionEvent event)
    {
    	menuButton.setText(((MenuItem)event.getSource()).getText());
    	selected = ((MenuItem)event.getSource()).getId();
    }
    
    @FXML
    public void calculateButton(ActionEvent event)
    {
    	double howMany;
    	try
    	{
    		howMany = Double.valueOf(userInput.getText());
    	}
    	catch(NumberFormatException e)
    	{
    		return;
    	}
    	
    	if(howMany < 0)
    		return;
    	
    	double mjValue = 0;
    	double jValue = 0;
    	double rfValue = 0;
    	double euValue = 0;
    	double aeValue = 0;
    	double wValue = 0;
    	double tValue = 0;
    	double feValue = 0;
    	
    	if(selected.equals("mj"))
    	{
    		mjValue = howMany;
    		jValue = ConvertUnits.mjToJ(howMany);
    		rfValue = ConvertUnits.mjToRF(howMany);
    		euValue = ConvertUnits.mjToEU(howMany);
    		aeValue = ConvertUnits.mjToAE(howMany);
    		wValue = ConvertUnits.mjToW(howMany);
    		tValue = ConvertUnits.mjToT(howMany);
    		feValue = ConvertUnits.mjToRF(howMany);
    	}
    	else if(selected.equals("j"))
    	{
    		mjValue = ConvertUnits.jToMJ(howMany);
    		jValue = howMany;
    		rfValue = ConvertUnits.jToRF(howMany);
    		euValue = ConvertUnits.jToEU(howMany);
    		aeValue = ConvertUnits.jToAE(howMany);
    		wValue = ConvertUnits.jToW(howMany);
    		tValue = ConvertUnits.jToT(howMany);
    		feValue = ConvertUnits.jToRF(howMany);
    	}
    	else if(selected.equals("rf") || selected.equals("fe"))
    	{
    		mjValue = ConvertUnits.rfToMJ(howMany);
    		jValue = ConvertUnits.rfToJ(howMany);
    		rfValue = howMany;
    		euValue = ConvertUnits.rfToEU(howMany);
    		aeValue = ConvertUnits.rfToAE(howMany);
    		wValue = ConvertUnits.rfToW(howMany);
    		tValue = ConvertUnits.rfToT(howMany);
    		feValue = howMany;
    	}
    	else if(selected.equals("eu"))
    	{
    		mjValue = ConvertUnits.euToMJ(howMany);
    		jValue = ConvertUnits.euToJ(howMany);
    		rfValue = ConvertUnits.euToRF(howMany);
    		euValue = howMany;
    		aeValue = ConvertUnits.euToAE(howMany);
    		wValue = ConvertUnits.euToW(howMany);
    		tValue = ConvertUnits.euToT(howMany);
    		feValue = ConvertUnits.euToRF(howMany);
    	}
    	else if(selected.equals("ae"))
    	{
    		mjValue = ConvertUnits.aeToMJ(howMany);
    		jValue = ConvertUnits.aeToJ(howMany);
    		rfValue = ConvertUnits.aeToRF(howMany);
    		euValue = ConvertUnits.aeToEU(howMany);
    		aeValue = howMany;
    		wValue = ConvertUnits.aeToW(howMany);
    		tValue = ConvertUnits.aeToT(howMany);
    		feValue = ConvertUnits.aeToRF(howMany);
    	}
    	else if(selected.equals("w"))
    	{
    		mjValue = ConvertUnits.wToMJ(howMany);
    		jValue = ConvertUnits.wToJ(howMany);
    		rfValue = ConvertUnits.wToRF(howMany);
    		euValue = ConvertUnits.wToEU(howMany);
    		aeValue = ConvertUnits.wToAE(howMany);
    		wValue = howMany;
    		tValue = ConvertUnits.wToT(howMany);
    		feValue = ConvertUnits.wToRF(howMany);
    	}
    	else if(selected.equals("t"))
    	{
    		mjValue = ConvertUnits.tToMJ(howMany);
    		jValue = ConvertUnits.tToJ(howMany);
    		rfValue = ConvertUnits.tToRF(howMany);
    		euValue = ConvertUnits.tToEU(howMany);
    		aeValue = ConvertUnits.tToAE(howMany);
    		wValue = ConvertUnits.tToW(howMany);
    		tValue = howMany;
    		feValue = ConvertUnits.tToRF(howMany);
    	}
    	else
    		return;
    	
    	mj.setText(String.format("%.2f", mjValue));
    	j.setText(String.format("%.2f", jValue));
    	rf.setText(String.format("%.2f", rfValue));
    	eu.setText(String.format("%.2f", euValue));
    	ae.setText(String.format("%.2f", aeValue));
    	w.setText(String.format("%.2f", wValue));
    	t.setText(String.format("%.2f", tValue));
    	fe.setText(String.format("%.2f", feValue));
    }
    
    public void initialize()
    {
    	ExecutorService es = Executors.newCachedThreadPool();
    	selected = "";
        initSplashes();
        splashText.setText(getRandomSplash());
       	splashThread = new SplashThread();
       	es.execute(splashThread);
       	es.shutdown();
    }
    
    private String getRandomSplash()
    {
    	if(lines == null)
    		return "Couldn't find splashes!";
    	
    	int chosen = (int)(Math.random() * lines.size());
        return lines.get(chosen);
    }
    
    private void initSplashes()
    {
    	try
    	{
    		URL url = new URL(SPLASHES_URL);
    		Scanner in = new Scanner(url.openStream());
    		lines = new ArrayList<String>();
    		while(in.hasNextLine())
    			lines.add(in.nextLine());
    		in.close();
    	}
    	catch(Exception e)
    	{
    		lines = null;
    	}
    }
    
    public void shutdown()
    {
    	if(splashThread != null)
    		splashThread.terminate();
    }
    
    private class SplashThread implements Runnable
    {
    	private boolean running = true;
    	
    	public void terminate()
    	{
    		running = false;
    	}
    	
    	public void run()
    	{
    		while(running)
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					running = false;
				}
				double r = Math.random();
				double g = Math.random();
				double b = Math.random();
				splashText.setTextFill(new Color(r, g, b, 1));
			}
    	}
    }
}
