package com.grechi.pacer;


import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.grechi.pacer.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	static final int HORA = 3600;
	static final int MINUTO = 60;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final NumberFormat nf = NumberFormat.getIntegerInstance();
		final DecimalFormat df_speed = new DecimalFormat("##.##");
		final DecimalFormat df_dist = new DecimalFormat("##.###");
		
		final FrameLayout fl = (FrameLayout) findViewById(R.id.FrameLayout2);
		
		final TextView paceValue = (TextView) findViewById(R.id.paceValue);
		final TextView speedValue = (TextView) findViewById(R.id.speedValue);
		final TextView timeValue = (TextView) findViewById(R.id.timeValue);
		final TextView distValue = (TextView) findViewById(R.id.distValue);
		
		final EditText etPaceM = (EditText) findViewById(R.id.etPaceM);
		final EditText etPaceS = (EditText) findViewById(R.id.etPaceS);
		final EditText etSpeed = (EditText) findViewById(R.id.etSpeed);
		final EditText etTimeH = (EditText) findViewById(R.id.etTimeH);
		final EditText etTimeM = (EditText) findViewById(R.id.etTimeM);
		final EditText etTimeS = (EditText) findViewById(R.id.etTimeS);
		final EditText etDist = (EditText) findViewById(R.id.etDist);
		
		
		
		
		Button btnCalc = (Button) findViewById(R.id.btnCalc);
		Button btnClean = (Button) findViewById(R.id.btnClean);
		Button btnVoltar = (Button) findViewById(R.id.button1);
		
			btnCalc.setOnClickListener(new OnClickListener() {

		
			@Override
			public void onClick(View v) {

				
				int erro = 0;
				
				boolean campoPace = false;
				boolean campoSpeed = false;
				boolean campoTime = false;
				boolean campoDist = false;
				
				double pace = 0;
				double time = 0;
				
				double paceM = 0;
				double paceS = 0;
				double speed = 0;
				double timeH = 0;
				double timeM = 0;
				double timeS = 0;
				double dist = 0;
					
				/**
				 * Verificando valores
				 */
				try {
					paceM = Double.parseDouble(etPaceM.getText().toString());

					if (paceM != 0) {campoPace = true;}
				} catch (NumberFormatException e) {e.printStackTrace();	}
				
				try{
					paceS = Double.parseDouble(etPaceS.getText().toString());
					
					if(paceS != 0){campoPace = true;}
				}catch(NumberFormatException e){e.printStackTrace();}		
				
				try{
					speed = Double.parseDouble(etSpeed.getText().toString());
					
					if(speed != 0){campoSpeed = true;}
				}catch(NumberFormatException e){e.printStackTrace();}
				
                try{
                	timeH = Double.parseDouble(etTimeH.getText().toString());
					
					if(timeH != 0){campoTime = true;}				
					}catch(NumberFormatException e){e.printStackTrace();}
                
                try{
					timeM = Double.parseDouble(etTimeM.getText().toString());
					
					if(timeM != 0){campoTime = true;}
				}catch(NumberFormatException e){e.printStackTrace();}
                
                try{
					timeS = Double.parseDouble(etTimeS.getText().toString());
					
					if(timeS != 0){campoTime = true;}
				}catch(NumberFormatException e){e.printStackTrace();}
                
                try{
                	dist = Double.parseDouble(etDist.getText().toString());
                	
                	if(dist != 0){campoDist = true;}
				}catch(NumberFormatException e){e.printStackTrace();}
				
				
				/**
				 * Verifica realidade dos dados
				 */
				if(paceM > 59) erro = 1;		
				if(paceS > 59) erro = 2;
				if(timeM > 59) erro = 3;
				if(timeS > 59) erro = 4;

				switch(erro){
				case 1: 
					Toast.makeText(getApplicationContext(), "Por favor, corriga os valores.", 5).show();
					etPaceM.setTextColor(Color.RED);
				    
					break;
				case 2: 
					Toast.makeText(getApplicationContext(), "Por favor, corriga os valores.", 5).show();
					etPaceS.setTextColor(Color.RED);
					break;
				case 3: 
					Toast.makeText(getApplicationContext(), "Por favor, corriga os valores.", 5).show();
					etTimeM.setTextColor(Color.RED);
					break;
				case 4: 
					Toast.makeText(getApplicationContext(), "Por favor, corriga os valores.", 5).show();
					etTimeS.setTextColor(Color.RED);
					break;
				case 0:
				
					/**
					 *  Calculando
					 */
					//Valores Nulos
					if((campoPace == false)&&(campoSpeed == false)&&(campoTime == false)&&(campoDist == false)){
						Toast.makeText(getApplicationContext(), "Por favor, informe no mínimo dois valores", 5).show();
						break;
					}
					
					//Valor único
					if((campoPace == true)&&(campoSpeed == false)&&(campoTime == false)&&(campoDist == false)){
						Toast.makeText(getApplicationContext(), "Por favor, informe mais valores", 5).show();
						break;
					}
					if((campoSpeed == true)&&(campoPace == false)&&(campoTime == false)&&(campoDist == false)){
						Toast.makeText(getApplicationContext(), "Por favor, informe mais valores", 5).show();
						break;
					}
					if((campoTime == true)&&(campoSpeed == false)&&(campoPace == false)&&(campoDist == false)){
						Toast.makeText(getApplicationContext(), "Por favor, informe mais valores", 5).show();
						break;
					}
					if((campoDist == true)&&(campoSpeed == false)&&(campoTime == false)&&(campoPace == false)){
						Toast.makeText(getApplicationContext(), "Por favor, informe mais valores", 5).show();
						break;
					}
					
					
					//Combinação: PACE e VELOCIDADE = null
					if((campoPace == true)&&(campoSpeed == true)&&(campoTime == false)&&(campoDist == false)){
						Toast.makeText(getApplicationContext(), "Por favor, informe TEMPO ou DISTÂNCIA", 5).show();
						break;
					}

					
					//Combinação: TEMPO e PACE
					if((campoPace == true)&&(campoTime == true)){
						pace = converterPace(paceM, paceS);
						speed = HORA / pace;
						
						time = converterTempo(timeH, timeM, timeS);
						dist = speed * (time / HORA);
					}
					
					
					//Combinação: TEMPO e VELOCIDADE
					if((campoTime == true)&&(campoSpeed == true)){
						time = converterTempo(timeH, timeM, timeS);
						dist = speed * (time / HORA);
						
						pace = HORA /speed;
						paceM = getPaceM(pace);
						paceS = getPaceS(pace, paceM);
					}
					
					//Combinação: TEMPO e DISTANCIA
					if((campoTime == true)&&(campoDist == true)){
						time = converterTempo(timeH, timeM, timeS);
						speed = (dist * HORA) / time;
						
						pace = HORA /speed;
						paceM = getPaceM(pace);
						paceS = getPaceS(pace, paceM);
					}
					
					//Combinação: DISTANCIA e PACE
					if((campoDist == true)&&(campoPace == true)){
						pace = converterPace(paceM, paceS);
						speed = HORA / pace;
					
						time = dist * pace;
						timeH = getTempoH(time);
						timeM = getTempoM(time, timeH);
						timeS = getTempoS(time, timeH, timeM);
					}
					
					//Combinação: DISTANCIA e VELOCIDADE
					if((campoDist == true)&&(campoSpeed == true)){
						pace = HORA / speed;
						paceM = getPaceM(pace);
						paceS = getPaceS(pace, paceM);
					
						time = (dist * HORA) / speed;
						timeH = getTempoH(time);
						timeM = getTempoM(time, timeH);
						timeS = getTempoS(time, timeH, timeM);
					}
					

					/**
					 * Formatação e Apresentação
					 */
					String PACEM, PACES, TIMEH,TIMEM,TIMES;
					
					PACEM = formater(paceM, nf);
					PACES = formater(paceS, nf);
					TIMEH = formater(timeH, nf);
					TIMEM = formater(timeM, nf);
					TIMES = formater(timeS, nf);				 
					
					paceValue.setText(PACEM + "\'" + PACES + "\" /km");	
					paceValue.setBackgroundColor(Color.WHITE);
					speedValue.setText(df_speed.format(speed) + " km/h");  
					speedValue.setBackgroundColor(Color.WHITE);
					timeValue.setText(TIMEH + "h" + TIMEM + "\'" + TIMES + "\""); 
					timeValue.setBackgroundColor(Color.WHITE);
					distValue.setText(df_dist.format(dist) + " km"); 
					distValue.setBackgroundColor(Color.WHITE);
	              
					fl.setVisibility(View.VISIBLE);

					((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
				    break;
				}
			}
		});
					
		
		btnClean.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			clear(etPaceM, etPaceS, etSpeed, etTimeH, etTimeM, etTimeS, etDist);
				
			
			((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
			}
		});
		
		btnVoltar.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v){
				
				clear(etPaceM, etPaceS, etSpeed, etTimeH, etTimeM, etTimeS, etDist);
				
				//Escondendo Layout
				fl.setVisibility(View.INVISIBLE);
				
				((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
			}
			
		});
		
		

	}
	
	/**
	 * Métodos auxilio
	 * 
	 */
	public void clear(EditText etPaceM, EditText etPaceS, EditText etSpeed, EditText etTimeH,
			EditText etTimeM, EditText etTimeS, EditText etDist){
		
		etPaceM.setText("");
		etPaceS.setText("");
		etSpeed.setText("");
		etTimeH.setText("");
		etTimeM.setText("");
		etTimeS.setText("");
		etDist.setText("");
	}
	
	public String formater(double x, NumberFormat nf){
		String abc;
		if(x == 0){
			abc = "00";
		}else{
			if(x < 10){
				abc = "0" + nf.format(x);
			}else{
				abc = nf.format(x);
			}
		}
		return abc;
		
	}
	
	
	/**
	 * Métodos para conversão
	 * 
	 */
	
	double converterTempo(double timeH, double timeM, double timeS){
	  double time = 0;
		
	  if (timeH != 0){
		  time += timeH * HORA;
	  }

	  if(timeM != 0){
		  time += timeM * MINUTO;
	  }

	  if(timeS != 0){
		  time += timeS;
	  }
                
		return time;
	}
	
	double getTempoH(double time){
		double timeH = 0;
		if(time > 3600){
        	timeH = (int) time/HORA;
        }
		return timeH;
	}
	
	double getTempoM(double time, double timeH){
		double timeM = 0;
		if((time - timeH*HORA) != 0){
    		timeM = (int) (time - timeH*HORA)/MINUTO;
    	}
		return timeM;
	}
	
	double getTempoS(double time, double timeH, double timeM){
		double timeS = 0;
		if((time - timeH*HORA - timeM*MINUTO) != 0){
			timeS = (int) (time - timeH*HORA - timeM*MINUTO);
		}
		return timeS;
	}
	
	double converterPace(double paceM, double paceS){
		double pace = 0;
		
		if(paceM != 0){
        	pace += paceM * MINUTO;
        }
        
        if(paceS != 0){
        	pace += paceS;
        }
        
        return pace;
	}
	
	double getPaceM(double pace){
		double paceM = 0;
		if(pace > MINUTO){
        	paceM = (int) pace / MINUTO;
        }
		return paceM;
	}
	
	double getPaceS(double pace, double paceM){
		double paceS = 0;
		if(pace - (pace/MINUTO) != 0){
    		paceS = (int) (pace - (paceM*MINUTO));
    	}
		return paceS;	}
	


}
