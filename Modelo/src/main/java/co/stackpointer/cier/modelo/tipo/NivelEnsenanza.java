/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author user
 */
public enum NivelEnsenanza {
    
    ALFABETIZACION          (1L),
    BASICA                  (2L),
    BASICA_ADULTOS          (3L),
    BASICA_MULTIGRADO       (4L),
    CCPREB                  (7L),
    CENTRO_EDUCACION_BASICA (8L),
    EDUCACION_LABORAL       (9L),
    EDUCACION_PARA_ADULTOS  (10L),
    ESPECIAL_BASICO_MEDIA   (11L),
    LNICIAL_CASA_INFANTIL   (12L),
    INICIAL_ESPECIAL        (13L),
    INICIAL_GUARDERIA       (14L),
    INICIAL_OTROS           (15L),
    MEDIA                   (16L),
    MEDIA_ADULTOS           (17L),
    MEDIA_ARTISTICO         (18L),
    MEDIA_HC_ADULTOS        (19L),
    MEDIA_HC                (20L),
    MEDIA_TP                (21L),
    MEDIA_TP_ADULTO         (22L),
    MEDIA_VOCACIONAL        (23L),
    MEDIO_GENERAL           (24L),
    MEDIO_TECNICO_BASICO    (25L),
    MEDIO_TECNICO_PROF     (26L),
    MEDIO_TEVECENTRO        (27L),
    PARVULARIA              (28L),
    PREBASICA               (29L),
    PREESCOLAR              (30L),
    OTRA                    (31L),
    SECUNDARIA              (6L),
    PRIMARIA                (5L);


    
    private Long nivel;

    private NivelEnsenanza(Long nivel) {
        this.nivel = nivel;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public boolean equals(Long obj) {
        if (obj == null) {
            return false;
        }
        if (this.nivel != obj && (this.nivel == null || !this.nivel.equals(obj))) {
            return false;
        }
        return true;
    }
     
        
}
