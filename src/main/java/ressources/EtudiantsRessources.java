package ressources;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("etudiants")
public class EtudiantsRessources {
    public static EtudiantBusiness etuB= new EtudiantBusiness();
    public static OptionBusiness optionB = new OptionBusiness();
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantByDomaine(@QueryParam("codeOption") String option){
        Option o= optionB.getOptionByCode(Integer.parseInt(option));
        List<Etudiant> res = etuB.getEtudiantsByOption(o);
        GenericEntity<List<Etudiant>> newRes= new GenericEntity<>(res){};
        if(res.size()==0)
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.status(Response.Status.FOUND).entity(newRes).build();
    }
}
