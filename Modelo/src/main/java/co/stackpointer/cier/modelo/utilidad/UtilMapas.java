/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.utilidad;

/**
 *
 * @author nruiz
 */
public class UtilMapas {
 
    public static double convertirGraMinSecADecimal(double grados, double minutos, double segundos, String signo)
    {   
       double decimal;
        //TODO ESPECIFICAR SI EL SUR ES NEGATIVO Y SI EL OESTE TAMBIEN ES NEGATIVO
       decimal = grados + minutos/60 + segundos/3600;
       if("S".equals(signo) || "O".equals(signo)){
           decimal=-decimal;
       }

       return  decimal;
    }
   
    
    
    
    public static double[] convertUTMToLatLong(Integer zona, String zonaLat, Double esteOeste, Double norteSur)
    {
        double arc,mu,ei,ca,cb,cc,cd,n0,r0,_a1,dd0,t0,Q0,
                lof1,lof2,lof3,_a2,phi1,fact1,fact2,fact3,fact4,zoneCM,_a3;
    double b = 6356752.314;
    double a = 6378137;
    double e = 0.081819191;
    double e1sq = 0.006739497;
    double k0 = 0.9996;
    
      double[] latlon = { 0.0, 0.0 };
      String hemisphere = "N";
      if ("ACDEFGHJKLM".indexOf(zonaLat) > -1)
      {
        hemisphere = "S";
      }
      double latitude = 0.0;
      double longitude = 0.0;

      if (hemisphere.equals("S"))
      {
        norteSur = 10000000 - norteSur;
      }
      arc = norteSur / k0;
      mu = arc
          / (a * (1 - POW(e, 2) / 4.0 - 3 * POW(e, 4) / 64.0 - 5 * POW(e, 6) / 256.0));

      ei = (1 - POW((1 - e * e), (1 / 2.0)))
          / (1 + POW((1 - e * e), (1 / 2.0)));

      ca = 3 * ei / 2 - 27 * POW(ei, 3) / 32.0;

      cb = 21 * POW(ei, 2) / 16 - 55 * POW(ei, 4) / 32;
      cc = 151 * POW(ei, 3) / 96;
      cd = 1097 * POW(ei, 4) / 512;
      phi1 = mu + ca * SIN(2 * mu) + cb * SIN(4 * mu) + cc * SIN(6 * mu) + cd
          * SIN(8 * mu);

      n0 = a / POW((1 - POW((e * SIN(phi1)), 2)), (1 / 2.0));

      r0 = a * (1 - e * e) / POW((1 - POW((e * SIN(phi1)), 2)), (3 / 2.0));
      fact1 = n0 * TAN(phi1) / r0;

      _a1 = 500000 - esteOeste;
      dd0 = _a1 / (n0 * k0);
      fact2 = dd0 * dd0 / 2;

      t0 = POW(TAN(phi1), 2);
      Q0 = e1sq * POW(COS(phi1), 2);
      fact3 = (5 + 3 * t0 + 10 * Q0 - 4 * Q0 * Q0 - 9 * e1sq) * POW(dd0, 4)
          / 24;

      fact4 = (61 + 90 * t0 + 298 * Q0 + 45 * t0 * t0 - 252 * e1sq - 3 * Q0
          * Q0)
          * POW(dd0, 6) / 720;

      //
      lof1 = _a1 / (n0 * k0);
      lof2 = (1 + 2 * t0 + Q0) * POW(dd0, 3) / 6.0;
      lof3 = (5 - 2 * Q0 + 28 * t0 - 3 * POW(Q0, 2) + 8 * e1sq + 24 * POW(t0, 2))
          * POW(dd0, 5) / 120;
      _a2 = (lof1 - lof2 + lof3) / COS(phi1);
      _a3 = _a2 * 180 / Math.PI;
      
      latitude = 180 * (phi1 - fact1 * (fact2 + fact3 + fact4)) / Math.PI;

      if (zona > 0)
      {
        zoneCM = 6 * zona - 183.0;
      }
      else
      {
        zoneCM = 3.0;

      }

      longitude = zoneCM - _a3;
      if (hemisphere.equals("S"))
      {
        latitude = -latitude;
      }

      latlon[0] = latitude;
      latlon[1] = longitude;
      return latlon;

    }

    private static double POW(double a, double b)
  {
    return Math.pow(a, b);
  }

  private static double SIN(double value)
  {
    return Math.sin(value);
  }

  private static double COS(double value)
  {
    return Math.cos(value);
  }

  private static double TAN(double value)
  {
    return Math.tan(value);
  }
}
