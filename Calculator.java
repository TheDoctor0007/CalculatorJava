import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.text.*;
public class Calculator extends Applet implements ActionListener{
	//private TextArea display = new TextArea("0", 3, 20);	
	private TextField display = new TextField ("0",20);
	private ArrayList<Button> numberpad = new ArrayList<Button>(10);
	private String operationtype="nada";
	private double store;
	private double val1=0;
	private double val2=0;
	boolean operating=false;
	Timer timer;
	
	//for fun
	private boolean explode=false;
	private int counter = 0;
	
	public void init(){
		Font Courier= new Font("Courier", Font.BOLD, 12);
		
		addline();
		display.setEditable(false);
		add(display);
		addline();
		
		String[] buttonnames = {"OFF", "CLR", "fun", " / ", " 7 ", " 8 ",
				" 9 ", " * ", " 4 ", " 5 ", " 6 ", " - ", " 1 ", " 2 ", 
				" 3 ", " + ", " 0 ", " . ", "+/-", " = "};
		
		int count=0;
		for (int i=0; i<20; i++){
			Button temp = new Button(buttonnames[i]);
			temp.setFont(Courier);
			numberpad.add(i, temp);
			add(numberpad.get(i));
			numberpad.get(i).addActionListener(this);
			count++;
			if(count%4==0) {
				addline();
				count=0;
			}
		}
	}
	
	public void addline(){
		/* 
		 * A Canvas component represents a blank rectangular area of the screen onto 
		 * which the application can draw or from which the application can trap input 
		 * events from the user.  Here, we're making one just 1 pixel tall (and 10000
		 * pixels wide) for the sole purpose of forcing everything to move to a new line.
		 */
		Canvas line = new Canvas( );
		line.setSize(10,1);
		line.setBackground(Color.white);
		add(line);
	}
	
	public void numberinput (int num){
		if(operationtype=="="){
			val1=0;
			val2=0;
			store=0;
			this.display.setText("0");
			operationtype=""; 
		}
		if((val1==0 && !display.getText().contains("0."))||operating){
			this.setFormattedText(""+num);
			if(operating){
				operating=false;
			}
		}
		else{
			if(display.getText().length()-display.getText().indexOf(46)<=10){
				this.setFormattedText(display.getText()+num);
			}
	
		}
		val1 = Double.parseDouble(display.getText());
	}
	
	public void operation(){
		
		if(operationtype.contains("*")){
			store=val1*val2;
		}
		else if(operationtype.contains("/")){
			store=val2/val1;
		}
		else if(operationtype.contains("+")){
			store=val1+val2;
		}
		else if(operationtype.contains("-")){
			store=val2-val1;
		}
		else if(operationtype.contains("=")){
			store=val2;
		}
		else{
			store=val1;
		}
		this.setFormattedText("" + store);
		val2=store;
		operating=true;
	}
	
	public void setFormattedText(String set){
		DecimalFormat tendigits = new DecimalFormat("###.##########");
		double temp= Double.parseDouble(set);
		if(!set.endsWith(".")){
			set=tendigits.format(temp);
		}
		for(int i=set.length()-1; i>0; i--){
			if(set.endsWith(".0")){
				set=set.substring(0,i-1);
				i--;
			}
			if(set.endsWith("0")&& set.contains(".")){
				set=set.substring(0,i);
			}
		}
		display.setText(set);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == numberpad.get(0)) { //OFF Button
			System.exit(0);
		}
		else if (e.getSource() == numberpad.get(1)) {//CLR Button
			val1=0;
			val2=0;
			store=0;
			this.setFormattedText("0");
			operationtype="nada";
		}
		else if (e.getSource() == numberpad.get(2)) {//fun Button
			val1=0;
			val2=0;
			store=0;
			operationtype="nada";
			display.setText("Msg will slfdstrct in 5 scnds");
			timer = new Timer();
			timer.schedule(new Update(), 5000);
		}
		else if (e.getSource() == numberpad.get(3)) {// divide Button
			this.operation();
			operationtype="/";
		}
		else if (e.getSource() == numberpad.get(4)) {// 7
			numberinput(7);
		}
		else if (e.getSource() == numberpad.get(5)) {// 8
			numberinput(8);
		}
		else if (e.getSource() == numberpad.get(6)) {// 9 
			numberinput(9);
		}
		else if (e.getSource() == numberpad.get(7)) {// multiply
			this.operation();
			operationtype="*";
		}
		else if (e.getSource() == numberpad.get(8)) {// 4
			numberinput(4);
		}
		else if (e.getSource() == numberpad.get(9)) { // 5
			numberinput(5);
		}
		else if (e.getSource() == numberpad.get(10)) { // 6
			numberinput(6);
		}
		else if (e.getSource() == numberpad.get(11)) { // minus
			this.operation();
			operationtype="-";
		}
		else if (e.getSource() == numberpad.get(12)) { // 1
			numberinput(1);
		}
		else if (e.getSource() == numberpad.get(13)) { // 2
			numberinput(2);
		}
		else if (e.getSource() == numberpad.get(14)) { // 3
			numberinput(3);
		}
		else if (e.getSource() == numberpad.get(15)) { // plus
			this.operation();
			operationtype="+";
		}
		else if (e.getSource() == numberpad.get(16)) { // 0
			numberinput(0);
		}
		else if (e.getSource() == numberpad.get(17)) { // decimal
			if(operating){
				this.setFormattedText("0.");
				operating=false;
			}
			if(!display.getText().contains(".")){
				this.setFormattedText(display.getText()+".");
			}
		}
		else if (e.getSource() == numberpad.get(18)) { // positive/negative
			this.setFormattedText(((-1)*Double.parseDouble(display.getText()))+"");
			val1=Double.parseDouble(display.getText());
			store=val1;
		}
		else if (e.getSource() == numberpad.get(19)) { // equals
			operation();
			operationtype="=";
		}
	}
	
	class Update extends TimerTask {
	    public void run() {
	      //System.out.println("Time's up!");
	      //toolkit.beep();
	      //timer.cancel(); //Not necessary because we call System.exit
	      System.exit(0); //Stops the AWT thread (and everything else)
	    }
	  }

	
}