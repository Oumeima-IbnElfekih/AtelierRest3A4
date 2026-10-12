package ressources;


import entities.Option;
import metiers.OptionBusiness;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


// @Path("/options") définit le chemin de base de cette ressource REST.
// Toutes les méthodes de cette classe sont accessibles à partir de
// l'URL de base de l'application suivie de /options.
//
// Exemple : http://localhost:8080/MonProjet/api/options
// Le préfixe /api dépend de la configuration de l'application.
@Path("/options")
public class OptionRessources {
 public static OptionBusiness optionBusiness = new OptionBusiness();


    // ============================================================
    // 1. AJOUTER UNE OPTION
    // ============================================================

    // @POST indique que cette méthode traite les requêtes HTTP POST.
    // POST est généralement utilisé pour créer une nouvelle ressource.
    @POST

    // @Consumes indique le format des données que le serveur accepte
    // dans le corps de la requête HTTP.
    // Ici, le client doit envoyer une représentation JSON de l'option.
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouterOption(Option o) {

        if (optionBusiness.addOption(o))

            return Response.status(Status.OK).build();

        return Response.status(Status.NOT_FOUND).build();
    }


    // ============================================================
    // 2. RÉCUPÉRER LA LISTE DES OPTIONS
    // ============================================================

    // @GET indique que cette méthode traite les requêtes HTTP GET.
    // GET sert à consulter ou récupérer des ressources.
    @GET

    // @Produces indique le format de la représentation retournée
    // par le serveur au client.
    // Ici, la réponse contenant les options est au format JSON.
    @Produces(MediaType.APPLICATION_JSON)
    public List<Option> getListOptions(

            // @QueryParam("domaine") récupère la valeur du paramètre
            // "domaine" présent dans la chaîne de requête de l'URL.
            //
            // Exemple :
            // GET /options?domaine=Informatique
            //
            // La valeur "Informatique" sera affectée à la variable domaine.
            //
            // Si le paramètre n'est pas présent, domaine vaut normalement
            // null pour ce paramètre de type String.
            @QueryParam("domaine") String domaine) {


        List<Option> res;

        // Vérifie si le paramètre domaine n'a pas été fourni.
        if (domaine == null) {

            // Aucun filtre n'a été demandé :
            // récupère toutes les options.
            res = optionBusiness.getListeOptions();

        } else {

            // Un domaine a été fourni :
            // récupère uniquement les options correspondant à ce domaine.
            res = optionBusiness.getOptionsByDomaine(domaine);
        }

        // Vérifie si la liste récupérée est vide.
        // isEmpty() retourne true si elle ne contient aucun élément.
        if (res.isEmpty())

            // Retourne HTTP 404 Not Found.
            // Cela signifie ici qu'aucune option n'a été trouvée.
            return null;

        return res;
    }


    // ============================================================
    // 3. SUPPRIMER UNE OPTION
    // ============================================================

    // @DELETE indique que cette méthode traite les requêtes HTTP DELETE.
    // DELETE sert à supprimer une ressource existante.
    @DELETE
    @Path("/{code}")
    public Response supprimerOption(

            // @PathParam("code") récupère la valeur du segment "code"
            // dans le chemin de l'URL.
            //
            // Pour DELETE /options/5, codeOption vaudra 5.
            //
            // JAX-RS convertit la valeur textuelle "5" en entier int.
            @PathParam("code") int codeOption) {


        if (optionBusiness.deleteOption(codeOption))

            // Retourne HTTP 204 No Content.
            // La suppression a réussi et la réponse ne contient aucun corps.
            return Response.status(Status.NO_CONTENT).build();

        // Si la suppression échoue, retourne HTTP 404 Not Found.
        // Ce choix est pertinent si l'option recherchée n'existe pas.
        return Response.status(Status.NOT_FOUND).build();
    }


    // ============================================================
    // 4. MODIFIER UNE OPTION
    // ============================================================

    // @PUT indique que cette méthode traite les requêtes HTTP PUT.
    // PUT est généralement utilisé pour remplacer ou mettre à jour
    // la représentation d'une ressource identifiée.
    @PUT

    // Le client doit envoyer les nouvelles données de l'option au format JSON.
    @Consumes(MediaType.APPLICATION_JSON)

    @Path("{id}")
    public Response modifierOption(

            // Objet Option construit à partir du JSON envoyé
            // dans le corps de la requête HTTP.
            Option o,
              @PathParam("id") int codeOption) {

         if (optionBusiness.updateOption(codeOption, o))

             return Response.status(Status.OK).build();

        return Response.status(Status.NOT_FOUND).build();
    }


    // ============================================================
    // 5. RÉCUPÉRER UNE OPTION PAR SON CODE
    // ============================================================

    // @GET indique qu'il s'agit d'une requête de consultation.
    @GET

    // La réponse est produite au format JSON.
    @Produces(MediaType.APPLICATION_JSON)

    // Le chemin contient le code de l'option recherchée.
    //
    // Exemple :
    // GET /options/5
    //
    // À distinguer de GET /options, qui récupère la liste.
    @Path("{code}")
    public Response getOptionbyCode(@PathParam("code") int codeOption) {

       Option result = optionBusiness.getOptionByCode(codeOption);
        // Vérifie si une option a été trouvée.
        if (result != null)

             return Response.status(Status.OK).entity(result).build();

        return Response.status(Status.NOT_FOUND).build();
    }
}

