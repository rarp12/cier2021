/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author david
 */
public enum BitacoraTransaccion {
    
    LOGIN(BitacoraModulo.AM, BitacoraOperacion.A, BitacoraOpcion.LOGIN),
    LOGOUT(BitacoraModulo.AM, BitacoraOperacion.A, BitacoraOpcion.LOGOUT),
    GENERAR_INDICADORES(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.GENERAR_INDICADORES),
    CONSULTA_CALIFICACION_AMBITO(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.CALIFICACION_AMBITO),
    EDITAR_CALIFICACION_AMBITO(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.CALIFICACION_AMBITO),
    CONSULTA_VARIABLE_AMBITO(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.VARIABLE_AMBITO),
    EDITAR_VARIABLE_AMBITO(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.VARIABLE_AMBITO),
    CONSULTA_PARAMETROS_CONSTRUCCION(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.PARAMETROS_CONSTRUCCION),
    CREAR_PARAMETROS_CONSTRUCCION(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.PARAMETROS_CONSTRUCCION),
    EDITAR_PARAMETROS_CONSTRUCCION(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.PARAMETROS_CONSTRUCCION),
    ELIMINAR_PARAMETROS_CONSTRUCCION(BitacoraModulo.AM, BitacoraOperacion.E, BitacoraOpcion.PARAMETROS_CONSTRUCCION),
    CONSULTA_ESTANDARES(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.ESTANDARES),
    EDITAR_ESTANDARES(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.ESTANDARES),
    CONSULTA_ESTANDAR_LOTE(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.ESTANDAR_LOTE),
    CREAR_ESTANDAR_LOTE(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.ESTANDAR_LOTE),
    EDITAR_ESTANDAR_LOTE(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.ESTANDAR_LOTE),
    ELIMINAR_ESTANDAR_LOTE(BitacoraModulo.AM, BitacoraOperacion.E, BitacoraOpcion.ESTANDAR_LOTE),
    CONSULTAR_ESTANDAR_COCINA(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.ESTANDAR_COCINA),
    CREAR_ESTANDAR_COCINA(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.ESTANDAR_COCINA),
    EDITAR_ESTANDAR_COCINA(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.ESTANDAR_COCINA),
    ELIMINAR_ESTANDAR_COCINA(BitacoraModulo.AM, BitacoraOperacion.E, BitacoraOpcion.ESTANDAR_COCINA),
    CONSULTAR_UNIDADES_FUNCIONALES(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.UNIDAD_FUNCIONAL),
    CREAR_UNIDADES_FUNCIONALES(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.UNIDAD_FUNCIONAL),
    EDITAR_UNIDADES_FUNCIONALES(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.UNIDAD_FUNCIONAL),
    CONSULTA_PRIORIZACION_UNIDADES_FUNCIONALES(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.PRIORIZACION_UNIDAD_FUNCIONAL),
    EDITAR_PRIORIZACION_UNIDADES_FUNCIONALES(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.PRIORIZACION_UNIDAD_FUNCIONAL),
    CONSULTA_COSTO_CONSTRUCCION(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.COSTO_CONSTRUCCION),
    EDITAR_COSTO_CONSTRUCCION(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.COSTO_CONSTRUCCION),
    CONSULTA_PRIORIZACION_SERVICIOS(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.PRIORIZACION_SERVICIOS),
    EDITAR_PRIORIZACION_SERVICIOS(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.PRIORIZACION_SERVICIOS),
    CONSULTAR_USUARIO(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.USUARIO),
    CREAR_USUARIO(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.USUARIO),
    EDITAR_USUARIO(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.USUARIO),
    CONSULTAR_ROL(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.ROL),
    CREAR_ROL(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.ROL),
    EDITAR_ROL(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.ROL),
    ELIMINAR_ROL(BitacoraModulo.AM, BitacoraOperacion.E, BitacoraOpcion.ROL),
    CREAR_TIPOLOGIA(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.TIPOLOGIA),
    EDITAR_TIPOLOGIA(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.TIPOLOGIA),
    ELIMINAR_TIPOLOGIA(BitacoraModulo.AM, BitacoraOperacion.E, BitacoraOpcion.TIPOLOGIA),
    CREAR_DPA(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.DPA),
    EDITAR_DPA(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.DPA),
    ELIMINAR_DPA(BitacoraModulo.AM, BitacoraOperacion.E, BitacoraOpcion.DPA),
    BACKUP_DPA(BitacoraModulo.AM, BitacoraOperacion.Q, BitacoraOpcion.BACKUP_DPA),
    CREAR_ESTABLECIMIENTO(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.ESTABLECIMIENTO),
    EDITAR_ESTABLECIMIENTO(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.ESTABLECIMIENTO),
    ELIMINAR_ESTABLECIMIENTO(BitacoraModulo.AM, BitacoraOperacion.E, BitacoraOpcion.ESTABLECIMIENTO),
    CREAR_SEDE(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.SEDE),
    EDITAR_SEDE(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.SEDE),
    ELIMINAR_SEDE(BitacoraModulo.AM, BitacoraOperacion.E, BitacoraOpcion.SEDE),
    CREAR_PREDIO(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.PREDIO),
    EDITAR_PREDIO(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.PREDIO),
    ELIMINAR_PREDIO(BitacoraModulo.AM, BitacoraOperacion.E, BitacoraOpcion.PREDIO),
    CREAR_NIVEL_CONFIGURACION(BitacoraModulo.AM, BitacoraOperacion.C, BitacoraOpcion.NIVEL_CONFIGURACION),
    EDITAR_NIVEL_CONFIGURACION(BitacoraModulo.AM, BitacoraOperacion.M, BitacoraOpcion.NIVEL_CONFIGURACION),
    ELIMINAR_NIVEL_CONFIGURACION(BitacoraModulo.AM, BitacoraOperacion.E, BitacoraOpcion.NIVEL_CONFIGURACION),    
    CONSULTAR_GENERALIDADES(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.GENERALIDADES),
    CONSULTAR_ACCESIBILIDAD(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.ACCESIBILIDAD),
    CONSULTAR_ACC_INTERNA(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.ACCESIBILIDAD_INTERNA),    
    CONSULTAR_AMBIENTE_AULA(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_AULA),
    CONSULTAR_AMBIENTE_BIBLIOTECA(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_BIBLIOTECA),
    CONSULTAR_AMBIENTE_PLAYON_DEPORTIVO(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_PLAYON_DEPORTIVO),
    CONSULTAR_AMBIENTE_LABORATORIO_CIENCIAS(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_LABORATORIO_CIENCIAS),
    CONSULTAR_AMBIENTE_LABORATORIO_MULTIMEDIAL(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_LABORATORIO_MULTIMEDIAL),
    CONSULTAR_AMBIENTE_LABORATORIO_COMPUTACION(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_LABORATORIO_COMPUTACION),
    CONSULTAR_AMBIENTE_SALA_MUSICA(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_SALA_MUSICA),
    CONSULTAR_AMBIENTE_SALA_ARTES(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_SALA_ARTES),
    CONSULTAR_AMBIENTE_EXPANSIONES_RECREATIVAS(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_EXPANSIONES_RECREATIVAS),
    CONSULTAR_AMBIENTE_SALON_USOS_MULTIPLES(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_SALON_USOS_MULTIPLES),
    CONSULTAR_AMBIENTE_PSICOPEDAGOGIA_ENFERMERIA(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_PSICOPEDAGOGIA_ENFERMERIA),
    CONSULTAR_AMBIENTE_SERVICIOS_SANITARIOS(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_SERVICIOS_SANITARIOS),
    CONSULTAR_AMBIENTE_OFICINA_ADMINISTRACION(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_OFICINA_ADMINISTRACION),
    CONSULTAR_AMBIENTE_COCINA(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_COCINA),
    CONSULTAR_AMBIENTE_COMEDOR(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMBIENTE_COMEDOR),
    CONSULTAR_SERVICIO_AGUA(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.SERVICIO_AGUA),
    CONSULTAR_SERVICIO_ENERGIA_ELECTRICA(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.SERVICIO_ENERGIA_ELECTRICA),
    CONSULTAR_SERVICIO_GAS(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.SERVICIO_GAS),
    CONSULTAR_SERVICIO_INTERNET(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.SERVICIO_INTERNET),
    CONSULTAR_SERVICIO_RECOLECCION_BASURAS(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.SERVICIO_RECOLECCION_BASURAS),
    CONSULTAR_SERVICIO_RED_ALCANTARILLADO(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.SERVICIO_RED_ALCANTARILLADO),
    CONSULTAR_SERVICIO_RED_PLUVIAL(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.SERVICIO_RED_PLUVIAL),
    CONSULTAR_SERVICIO_SISTEMA_RECICLAJE(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.SERVICIO_SISTEMA_RECICLAJE),
    CONSULTAR_SERVICIO_TELEFONO(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.SERVICIO_TELEFONO),
    CONSULTAR_AMPLIACION(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.AMPLIACION),
    CONSULTAR_CONFORT(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.CONFORT),
    CONSULTAR_CONTROL_VIGILANCIA(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.CONTROL_VIGILANCIA),
    CONSULTAR_EDIFICIOS(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.EDIFICIOS),
    CONSULTAR_ESPACIOS(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.ESPACIOS),
    CONSULTAR_PROPIEDAD(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.PROPIEDAD),
    CONSULTAR_REDISTRIBUCION(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.REDISTRIBUCION),
    CONSULTAR_RIESGOS_ANTROPICOS(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.RIESGOS_ANTROPICOS),
    CONSULTAR_SEGURIDAD(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.SEGURIDAD),
    CONSULTAR_SERVICIOS_CONSOLIDADOS(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.SERVICIOS_CONSOLIDADOS),
    CONSULTAR_SOSTENIBILIDAD(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.SOSTENIBILIDAD),
    CONSULTAR_CALIFICACION(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.CALIFICACION),
    CONSULTAR_DINAMICA(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.DINAMICA),
    CONSULTAR_DIAGNOSTICO_GENERAL(BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.DIAGNOSTICO_GENERAL),    
    CONSULTAR_RIESGO_NATURALES (BitacoraModulo.CO, BitacoraOperacion.Q, BitacoraOpcion.RIESGOS_NATURALES),
    MODIFICAR_INSTRUMENTO(BitacoraModulo.DG,BitacoraOperacion.M,BitacoraOpcion.INSTRUMENTOS),
    ANULAR_INSTRUMENTO(BitacoraModulo.DG,BitacoraOperacion.E,BitacoraOpcion.INSTRUMENTOS),
    CREAR_INSTRUMENTO(BitacoraModulo.DG,BitacoraOperacion.C,BitacoraOpcion.INSTRUMENTOS),
    MODIFICAR_ELEMENTO(BitacoraModulo.DG,BitacoraOperacion.M,BitacoraOpcion.ELEMENTOS),
    ELIMINAR_ELEMENTO(BitacoraModulo.DG,BitacoraOperacion.E,BitacoraOpcion.ELEMENTOS),
    CREAR_ELEMENTO(BitacoraModulo.DG,BitacoraOperacion.C,BitacoraOpcion.ELEMENTOS);
   ;
    
    private BitacoraModulo modulo;
    private BitacoraOperacion operacion;
    private BitacoraOpcion opcion;
    
     private BitacoraTransaccion(BitacoraModulo modulo, BitacoraOperacion operacion, BitacoraOpcion opcion) {
        this.modulo = modulo;
        this.operacion = operacion;
        this.opcion = opcion;
    }

    public BitacoraModulo getModulo() {
        return modulo;
    }

    public BitacoraOperacion getOperacion() {
        return operacion;
    }

    public BitacoraOpcion getOpcion() {
        return opcion;
    }
    
}
