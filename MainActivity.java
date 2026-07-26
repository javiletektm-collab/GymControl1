package com.gymcontrol.app;

import android.app.*;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {
  LinearLayout root, content; TextView title; SharedPreferences prefs;
  int purple=Color.rgb(124,58,237), dark=Color.rgb(17,24,39), bg=Color.rgb(244,246,248);

  @Override public void onCreate(Bundle b){super.onCreate(b);prefs=getSharedPreferences("gym",0);showLogin();}
  TextView text(String s,int size,boolean bold){TextView v=new TextView(this);v.setText(s);v.setTextSize(size);v.setTextColor(dark);v.setPadding(8,8,8,8);if(bold)v.setTypeface(null,Typeface.BOLD);return v;}
  Button button(String s){Button b=new Button(this);b.setText(s);b.setTextColor(Color.WHITE);b.setBackgroundColor(purple);return b;}
  LinearLayout card(){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(24,24,24,24);c.setBackgroundColor(Color.WHITE);LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.setMargins(16,12,16,12);c.setLayoutParams(lp);return c;}
  void showLogin(){
    ScrollView sv=new ScrollView(this); LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setGravity(Gravity.CENTER_HORIZONTAL);l.setPadding(32,100,32,32);l.setBackgroundColor(bg);sv.addView(l);
    TextView logo=text("GC",40,true);logo.setGravity(Gravity.CENTER);logo.setTextColor(Color.WHITE);logo.setBackgroundColor(dark);logo.setPadding(30,20,30,20);l.addView(logo);
    l.addView(text("GymControl",32,true));l.addView(text("Control de socios, accesos, rutinas y membresías",16,false));
    Button admin=button("Entrar como administrador");Button socio=button("Entrar como socio de prueba");l.addView(admin,new LinearLayout.LayoutParams(-1,-2));l.addView(socio,new LinearLayout.LayoutParams(-1,-2));
    admin.setOnClickListener(v->showApp(true));socio.setOnClickListener(v->showApp(false));setContentView(sv);
  }
  void showApp(boolean admin){
    root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setBackgroundColor(bg);
    LinearLayout header=new LinearLayout(this);header.setPadding(20,20,20,20);header.setBackgroundColor(dark);header.setGravity(Gravity.CENTER_VERTICAL);
    title=text(admin?"Resumen":"Hola, Laura",24,true);title.setTextColor(Color.WHITE);header.addView(title,new LinearLayout.LayoutParams(0,-2,1));Button out=button("Salir");header.addView(out);out.setOnClickListener(v->showLogin());root.addView(header);
    ScrollView sv=new ScrollView(this);content=new LinearLayout(this);content.setOrientation(LinearLayout.VERTICAL);content.setPadding(8,8,8,90);sv.addView(content);root.addView(sv,new LinearLayout.LayoutParams(-1,0,1));
    LinearLayout nav=new LinearLayout(this);nav.setBackgroundColor(Color.WHITE);String[] labels=admin?new String[]{"Inicio","Socios","Accesos","Rutinas","Cuotas"}:new String[]{"Inicio","Rutina","QR","Cuota"};
    for(String s:labels){Button n=new Button(this);n.setText(s);n.setTextSize(11);n.setBackgroundColor(Color.WHITE);nav.addView(n,new LinearLayout.LayoutParams(0,-2,1));n.setOnClickListener(v->{String x=((Button)v).getText().toString();if(admin)adminPage(x);else memberPage(x);});}
    root.addView(nav);setContentView(root);if(admin)adminPage("Inicio");else memberPage("Inicio");
  }
  void clear(String t){title.setText(t);content.removeAllViews();}
  void stat(String label,String value){LinearLayout c=card();c.addView(text(label,14,false));c.addView(text(value,30,true));content.addView(c);}
  void adminPage(String p){
    clear(p.equals("Inicio")?"Resumen":p);
    if(p.equals("Inicio")){stat("Socios totales","5");stat("Membresías activas","4");stat("Dentro ahora",prefs.getBoolean("inside",false)?"3":"2");stat("Ingresos estimados","675 €");list("Últimos accesos",new String[]{"Laura Martín — Entrada 09:13","Sofía Díaz — Entrada 08:42","Carlos Ruiz — Salida ayer"});}
    else if(p.equals("Socios")){list("Clientes",new String[]{"Laura Martín · Activa · 45 €","Carlos Ruiz · Activa · 45 €","Marta Gómez · Anual · 420 €","Antonio Pérez · Pendiente","Sofía Díaz · Trimestral · 120 €"});Button add=button("+ Añadir socio de prueba");content.addView(add);add.setOnClickListener(v->Toast.makeText(this,"Socio añadido en modo demostración",Toast.LENGTH_SHORT).show());}
    else if(p.equals("Accesos")){stat("Personas dentro",prefs.getBoolean("inside",false)?"3":"2");Button scan=button("Simular lector QR");content.addView(scan);scan.setOnClickListener(v->{boolean in=!prefs.getBoolean("inside",false);prefs.edit().putBoolean("inside",in).apply();Toast.makeText(this,in?"Entrada autorizada":"Salida registrada",Toast.LENGTH_SHORT).show();adminPage("Accesos");});list("Historial",new String[]{"Laura Martín — Entrada","Sofía Díaz — Entrada","Carlos Ruiz — Salida"});}
    else if(p.equals("Rutinas")){list("Rutinas disponibles",new String[]{"Lunes · Pecho y tríceps","Miércoles · Espalda y bíceps","Viernes · Piernas"});}
    else {list("Planes",new String[]{"Mensual — 45 €","Trimestral — 120 €","Anual — 420 €"});Button renew=button("Renovar socio 30 días");content.addView(renew);renew.setOnClickListener(v->Toast.makeText(this,"Membresía renovada",Toast.LENGTH_SHORT).show());}
  }
  void memberPage(String p){
    clear(p.equals("Inicio")?"Hola, Laura":p);
    if(p.equals("Inicio")){LinearLayout c=card();c.addView(text("Membresía activa",16,true));c.addView(text("Plan mensual · 45 €",24,true));c.addView(text("Válida hasta el 12/08/2026",15,false));content.addView(c);list("Próximo entrenamiento",new String[]{"Pecho y tríceps","4 ejercicios · lunes"});}
    else if(p.equals("Rutina")){list("Pecho y tríceps",new String[]{"Press de banca — 4 × 10","Press inclinado — 3 × 12","Aperturas — 3 × 12","Extensión de tríceps — 3 × 15"});}
    else if(p.equals("QR")){LinearLayout c=card();TextView qr=text("▦\n▦ ▦ ▦\n ▦▦ \n▦ ▦▦",38,true);qr.setGravity(Gravity.CENTER);c.addView(qr);Button scan=button(prefs.getBoolean("inside",false)?"Registrar salida":"Registrar entrada");c.addView(scan);scan.setOnClickListener(v->{boolean in=!prefs.getBoolean("inside",false);prefs.edit().putBoolean("inside",in).apply();Toast.makeText(this,in?"Entrada registrada":"Salida registrada",Toast.LENGTH_SHORT).show();memberPage("QR");});content.addView(c);}
    else {LinearLayout c=card();c.addView(text("Plan mensual",24,true));c.addView(text("45 € / mes",30,true));c.addView(text("Tarjeta •••• 4242",16,false));Button pay=button("Actualizar membresía");c.addView(pay);pay.setOnClickListener(v->Toast.makeText(this,"Pago simulado",Toast.LENGTH_SHORT).show());content.addView(c);}
  }
  void list(String h,String[] rows){LinearLayout c=card();c.addView(text(h,20,true));for(String r:rows){TextView x=text(r,16,false);x.setPadding(8,18,8,18);c.addView(x);}content.addView(c);}
}
