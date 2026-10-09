package ressources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;
import java.util.List;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("etudiants")

public class EtudiantRessources {
    public static EtudiantBusiness etB = new EtudiantBusiness();
    public static OptionBusiness optB = new OptionBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant e) {
        if (e == null || e.getOption() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (etB.addEtudiant(e)) {
            return Response.status(Response.Status.OK).entity(e).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        List<Etudiant> l = etB.getAllEtudiants();
        if (l == null || l.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.OK).entity(l).build();
        }
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(@PathParam("code") String id) {
        Etudiant e = etB.getEtudiantByIdentifiant(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.OK).entity(e).build();
        }
    }

    @DELETE
    @Path("{code}")
    public Response deleteEtudiant(@PathParam("code") String id) {
        if (etB.deleteEtudiant(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String id, Etudiant e) {

        // Vérifier si l'étudiant existe
        Etudiant exist = etB.getEtudiantByIdentifiant(id);
        if (exist == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Vérifier les données reçues
        if (e == null || e.getOption() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Les informations de l'etudiant et son option sont obligatoires")
                    .build();
        }

        // Vérifier si l'option existe

        Option option = optB.getOptionByCode(e.getOption().getCodeOption());
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }


        e.setOption(option);
        e.setIdentifiant(id);

        if (etB.updateEtudiant(id, e)) {
            return Response.status(Response.Status.OK).entity(e).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int op) {

        //verifier l'existance de l'option
        Option option = optB.getOptionByCode(op);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Etudiant> l = etB.getEtudiantsByOption(option);
        if (l == null || l.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.OK).entity(new EtudiantList(l)).build();
        }


    }
}