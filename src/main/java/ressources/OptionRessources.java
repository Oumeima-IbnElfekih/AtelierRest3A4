package ressources;
import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

@Path("/options")
public class OptionRessources {
    public static  OptionBusiness optionBusiness= new OptionBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouterOption(Option o){
         if(optionBusiness.addOption(o))
             return Response.status(Status.OK).build();
         return Response.status(Status.NOT_FOUND).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListOptions(@QueryParam("domaine")
                                           String domaine){
        List<Option> res ;
        if(domaine == null){
            res =optionBusiness.getListeOptions();
        } else {
            res =optionBusiness.getOptionsByDomaine(domaine);
        }
       if(res.isEmpty())
           return Response.status(Status.NOT_FOUND).build();
       return Response.status(Status.OK).entity(res).build();
    }
    @DELETE
    @Path("/{code}")
    public Response supprimerOption(@PathParam("code") int codeOption){
        if(optionBusiness.deleteOption(codeOption))
            return Response.status(Status.NO_CONTENT).build();
        return Response.status(Status.NOT_FOUND).build();
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response modifierOption(Option o,
                                   @PathParam("id") int codeOption){
        if(optionBusiness.updateOption(codeOption,o))
            return Response.status(Status.OK).build();
        return Response.status(Status.NOT_FOUND).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{code}")
    public Response getOptionbyCode(@PathParam("code") int codeOption){
      Option result=optionBusiness.getOptionByCode(codeOption);
        if( result != null)
          return Response.status(Status.OK).entity(result).build();
      return Response.status(Status.NOT_FOUND).build();

    }
}
