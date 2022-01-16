/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author nruiz
 */
public enum UnidadFuncionalAmbiente {
    AULA("'1','3','4','5','10'"),
    LABORATORIO_CIENCIAS("'27','29','31'"),
    LABORATORIO_MULTIMEDIAL("'33'"),
    LABORATORIO_COMPUTACION("'28'"),
    BIBLIOTECA("'16','20','22','25'"),
    SALA_MUSICA("'18'"),
    SALA_ARTES("'12','44','45','46','53','54','55','56','57','58'"),
    PLAYON_DEPORTIVO("'59','60','61','62','63','64','65','66','69','70','71','72','73'"),
    EXPANSIONES_RECREATIVAS("'67','68','74','75','76','77','78','79','80'"),
    SALON_USOS_MULTIPLES("'81','83','84','87','88','89'"),
    PSICOPEDAGOGIA_ENFERMERIA("'90','91','92','93','94'"),
    SERVICIOS_SANITARIOS("'117','118','119','120','121','122','123','124'"),
    ALIMENTOS("'129','130','131','132'"),
    OFICINA_ADMINISTRACION("'128','116','156','157','158','159','160','161','162','163','164','165','166','167','168','169'");
    
    private String valor;

    private UnidadFuncionalAmbiente(String valor) {
        this.valor = valor;
    }
    
    public String getValor() {
        return valor;
    }
}
