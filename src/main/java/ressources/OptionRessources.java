package ressources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("options")
public class OptionRessources {

    public  static OptionBusiness optB = new OptionBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOptions(@QueryParam("domaine") String d) {
        List<Option> l;
        if (d == null || d.isEmpty()) {
            // No filter supplied → return all options
            l = optB.getListeOptions();
        } else {
            l = optB.getOptionsByDomaine(d);
        }
        if (l.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(200).entity(l).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AjoutOption(Option op) {
        if(optB.addOption(op)){
            return Response.status(200).entity(op).build();
        }else{
            return Response.status(400).build();

        }
    }

    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int id) {
        if(optB.deleteOption(id)){
            return Response.status(204).entity(id).build();
        }
        else {
            return Response.status(404).build();
        }
    }

    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code")  int code, Option op) {;
        // Vérifier si l'option existe
        Option optionExistante = optB.getOptionByCode(code);

        if (optionExistante == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (optB.updateOption(code, op)){
            return Response.status(Response.Status.OK).entity(op).build();
        }
        return Response.status(404).build();


    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionByCode( @PathParam("code") int code) {
        Option op = optB.getOptionByCode(code);
        if(op == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }else {
            return Response.status(200).entity(optB.getOptionByCode(code)).build();
        }
    }


}