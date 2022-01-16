/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.informeGeneral.predio;

import java.util.List;

/**
 *
 * @author nruiz
 */
public class ReporteInformeGeneralPredio {
    
        List<EstablecimientosAdicionales> listaEstablecimientosAdicionales;
        List<ControlVigilancia> listaControlVigilancia;
        List<SeguridadAccesibilidad> listaSeguridadAccesibilidad;

        public List<EstablecimientosAdicionales> getListaEstablecimientosAdicionales() {
            return listaEstablecimientosAdicionales;
        }

        public void setListaEstablecimientosAdicionales(List<EstablecimientosAdicionales> listaEstablecimientosAdicionales) {
            this.listaEstablecimientosAdicionales = listaEstablecimientosAdicionales;
        }

        public List<ControlVigilancia> getListaControlVigilancia() {
            return listaControlVigilancia;
        }

        public void setListaControlVigilancia(List<ControlVigilancia> listaControlVigilancia) {
            this.listaControlVigilancia = listaControlVigilancia;
        }

        public List<SeguridadAccesibilidad> getListaSeguridadAccesibilidad() {
            return listaSeguridadAccesibilidad;
        }

        public void setListaSeguridadAccesibilidad(List<SeguridadAccesibilidad> listaSeguridadAccesibilidad) {
            this.listaSeguridadAccesibilidad = listaSeguridadAccesibilidad;
        }
}
