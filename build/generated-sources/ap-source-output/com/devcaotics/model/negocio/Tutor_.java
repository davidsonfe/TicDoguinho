package com.devcaotics.model.negocio;

import com.devcaotics.model.negocio.CompartilharPet;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-01-02T14:18:58")
@StaticMetamodel(Tutor.class)
public class Tutor_ { 

    public static volatile SingularAttribute<Tutor, Boolean> mamae;
    public static volatile SingularAttribute<Tutor, String> senha;
    public static volatile ListAttribute<Tutor, CompartilharPet> compartilharPets;
    public static volatile SingularAttribute<Tutor, Integer> codigo;
    public static volatile SingularAttribute<Tutor, byte[]> foto;
    public static volatile SingularAttribute<Tutor, String> usuario;
    public static volatile SingularAttribute<Tutor, String> email;

}