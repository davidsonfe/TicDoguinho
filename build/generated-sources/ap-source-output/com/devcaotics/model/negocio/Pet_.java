package com.devcaotics.model.negocio;

import com.devcaotics.model.negocio.CompartilharPet;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-01-02T14:18:58")
@StaticMetamodel(Pet.class)
public class Pet_ { 

    public static volatile SingularAttribute<Pet, Integer> codigo;
    public static volatile SingularAttribute<Pet, String> raca;
    public static volatile SingularAttribute<Pet, byte[]> imagem;
    public static volatile SingularAttribute<Pet, String> nome;
    public static volatile SingularAttribute<Pet, String> porte;
    public static volatile SingularAttribute<Pet, String> sexo;
    public static volatile ListAttribute<Pet, CompartilharPet> compartilhamentos;
    public static volatile SingularAttribute<Pet, String> codigoUnico;
    public static volatile SingularAttribute<Pet, String> mesAnoNascimento;

}