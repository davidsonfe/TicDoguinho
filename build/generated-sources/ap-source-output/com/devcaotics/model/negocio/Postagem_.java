package com.devcaotics.model.negocio;

import com.devcaotics.model.negocio.Pet;
import com.devcaotics.model.negocio.Tutor;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-01-02T14:18:58")
@StaticMetamodel(Postagem.class)
public class Postagem_ { 

    public static volatile SingularAttribute<Postagem, byte[]> videoPet;
    public static volatile SingularAttribute<Postagem, Date> dataHoraPostagem;
    public static volatile SingularAttribute<Postagem, Long> id;
    public static volatile SingularAttribute<Postagem, byte[]> videoTutor;
    public static volatile SingularAttribute<Postagem, Pet> pet;
    public static volatile SingularAttribute<Postagem, Tutor> tutor;

}