/*
* Dateiname: FormularFunktionen.java
* Projekt  : WollMux
* Funktion : In WollMux-Formularen verwendete Funktionen des KVR.
* 
* Copyright: Landeshauptstadt München
*
* Änderungshistorie:
* Datum      | Wer | Änderungsgrund
* -------------------------------------------------------------------
* 02.03.2007 | BNK | Erstellung
* -------------------------------------------------------------------
*
*/
package de.muenchen.kvr;

import java.math.*;
import java.text.*;

public class FormularFunktionen
{
  /**
  * Liefert Urlaubsanspruch - BereitsBeantragt - JetztBeantragt zurück.
  * Falls einer der Werte keine Zahl ist, wird der String "Fehlerhafte Eingabe"
  * zurückgeliefert. Das Resultat wird auf 2 Nachkommastellen gerundet, jedoch
  * nur mit Nachkommastellen dargestellt, wenn tatsächlich welche vorhanden sind.
  * 1000er werden mit "." gruppiert.
  */
  public static String resturlaub(String strUrlaubsanspruch, String strBereitsBeantragt, String strJetztBeantragt)
  { try{
    double urlaubsAnspruch = parse(strUrlaubsanspruch);    //Eingabe-String in Zahl umwandeln.
    double bereitsBeantragt = parse(strBereitsBeantragt);  //dito
    double jetztBeantragt = parse(strJetztBeantragt);      //dito
    
    double ergebnis = urlaubsAnspruch - bereitsBeantragt - jetztBeantragt;
    
    //min. 0 Nachkommastellen, max. 2 Nachkommastellen, 1000er mit "." gruppieren
    return format(ergebnis,0,2,true);
    
    } catch(Exception e) { return ""; }
  }
  
  /**
  * Liefert Urlaubsanspruch - BereitsBeantragt - JetztBeantragt zurück.
  * Falls einer der Werte keine Zahl ist, wird der String "Fehlerhafte Eingabe"
  * zurückgeliefert. Das Resultat wird auf 2 Nachkommastellen gerundet, jedoch
  * nur mit Nachkommastellen dargestellt, wenn tatsächlich welche vorhanden sind.
  * 1000er werden mit "." gruppiert.
  */
  public static String verbleibendesGuthaben(String strGuthabenAusUeberstunden, String strEinzubringendesGuthaben)
  { try{
    double guthabenAusUeberstunden = parse(strGuthabenAusUeberstunden); //Eingabe-String in Zahl umwandeln.
    double einzubringendesGuthaben = parse(strEinzubringendesGuthaben); //dito
    
    double ergebnis = guthabenAusUeberstunden - einzubringendesGuthaben;
    
    //min. 0 Nachkommastellen, max. 2 Nachkommastellen, 1000er mit "." gruppieren
    return format(ergebnis,0,2,true);
    
    } catch(Exception e) { return ""; }
  }







  /**
  * Liefert eine Darstellung von wert gerundet auf max_nachkommastellen Nachkommastellen.
  * Die Darstellung enthält immer mindestens min_nachkommastellen Nachkommastellen,
  * auch wenn sie alle 0 sind. Gerundet wird immer in Richtung weg von der 0,
  * d.h. dass z.B. runde(0.15, 1) == "0.2" und runde(-0.15, 1) == "-0.2".
  * Falls group == true, so werden 1000er mit "." getrennt.
  */
  public static String format(double wert, int min_nachkommastellen, int max_nachkommastellen, boolean group)
  {
    //BigDecimal dec = new BigDecimal(wert, new MathContext(15,RoundingMode.HALF_EVEN));
    BigDecimal dec = new BigDecimal(""+wert);
    dec = dec.setScale(max_nachkommastellen, RoundingMode.HALF_UP);
    NumberFormat f = NumberFormat.getInstance();
    f.setMinimumFractionDigits(min_nachkommastellen);
    f.setMaximumFractionDigits(max_nachkommastellen);
    f.setGroupingUsed(group);
    //return dec.toPlainString();
    StringBuffer buffy = new StringBuffer();
    return f.format(dec, buffy, new FieldPosition(NumberFormat.INTEGER_FIELD)).toString();
  }

  /**
  * Liefert den Zahlenwert des Strings wert als double. Geparst wird gemäß Landeseinstellungen,
  * d.h. für Deutschland ist das Dezimaltrennzeichen ",". 1000er Gruppierungen mit "." sind nicht
  * erlaubt. Konnte nicht der ganze String als Zahl geparst werden, fliegt eine Exception.
  */
  public static double parse(String wert) throws Exception
  {
    NumberFormat format = NumberFormat.getInstance();
    format.setGroupingUsed(false); //Gruppierung abschalten, um nicht "." und "," zu verwechseln
    ParsePosition p = new ParsePosition(0);
    double val = format.parse(wert, p).doubleValue();
    if (p.getIndex() != wert.length()) throw new NumberFormatException();
    return val;
  }
  
}
