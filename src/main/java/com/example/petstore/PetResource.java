package com.example.petstore;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/pets")
@Produces("application/json")
public class PetResource {

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	public Response getPets() {

		return Response.ok(TempPetData.getInstance().getArrayList()).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("{petId}")
	public Response getPet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Pet pet = new Pet();
		pet.setPetId(petId);
		pet.setPetAge(3);
		pet.setPetName("Buula");
		pet.setPetType("Dog");

		return Response.ok(pet).build();
		
	}

	@Path("/add-pet")
	@Produces("application/json")
	@POST
	public Response addPetToStore(String request) throws JSONException {
		JSONObject jsonObject=new JSONObject(request);
		if(jsonObject.has("name") && jsonObject.has("type") && jsonObject.has("age")){
			Pet petData=new Pet();
			petData.setPetName(jsonObject.getString("name"));
			petData.setPetAge(Integer.parseInt(jsonObject.getString("age")));
			petData.setPetType(jsonObject.getString("type"));

			petData.setPetId(TempPetData.getInstance().getArrayList().get(TempPetData.getInstance().getArrayList().size()-1).getPetId()+1);

			List<Pet> petList=new ArrayList<Pet>() ;
			List<Pet> temp=new ArrayList<Pet>() ;

			petList.add(petData);
			temp.addAll(TempPetData.getInstance().getArrayList());
			temp.addAll(petList);
			TempPetData.getInstance().setArrayList(temp);

			return Response.ok(petList).build();
		}
		else {

			return Response.ok("{\n" + "\"Status\":false\n" + "\"Message\":\"New Pet Add Failed!\"\n"+ "}").build();

		}
	}


	@Path("/delete-pet/{petId}")
	@Produces("application/json")
	@DELETE
	public Response deletePetFromStore(@PathParam("petId") int petId){
		boolean isPetAvailableInStore;
		isPetAvailableInStore = false;

		for (int i=0;i<TempPetData.getInstance().getArrayList().size();i++){
			if(petId == TempPetData.getInstance().getArrayList().get(i).getPetId()){
				TempPetData.getInstance().getArrayList().remove(i);
				isPetAvailableInStore = true;
			}
		}

		if(isPetAvailableInStore) {
			return Response.ok("{\n" + "\"Status\":true\n" + "\"Message\":\"Pet Deleted Successfully!\"\n"+ "}").build();
		} else {
			return Response.ok("{\n" + "\"Status\":false\n" + "\"Message\":\"Pet Delete Failed!\"\n"+ "}").build();
		}

	}



	@Path("/update-pet/{petId}")
	@Produces("application/json")
	@PUT
	public Response updatePetDataInStore(@PathParam("petId") int petId,String editPetData) throws JSONException {
		JSONObject jsonObject = new JSONObject(editPetData);
		boolean isPetDataEdited;
		isPetDataEdited = false;
		int id;
		id = 0;

		if(jsonObject.has("name")){
			for (int i=0;i<TempPetData.getInstance().getArrayList().size();i++){
				if(petId == TempPetData.getInstance().getArrayList().get(i).getPetId()){
					TempPetData.getInstance().getArrayList().get(i).setPetName(jsonObject.getString("name"));
					isPetDataEdited=true;
					id = i;
				}
			}
		}

		if(jsonObject.has("type")){
			for (int i=0;i<TempPetData.getInstance().getArrayList().size();i++){
				if(petId == TempPetData.getInstance().getArrayList().get(i).getPetId()){
					TempPetData.getInstance().getArrayList().get(i).setPetType(jsonObject.getString("type"));
					isPetDataEdited=true;
					id = i;
				}
			}
		}

		if(jsonObject.has("age")){
			for (int i=0;i<TempPetData.getInstance().getArrayList().size();i++){
				if(petId == TempPetData.getInstance().getArrayList().get(i).getPetId()){
					TempPetData.getInstance().getArrayList().get(i).setPetAge(Integer.parseInt(jsonObject.getString("age")));
					isPetDataEdited=true;
					id = i;
				}
			}
		}

		if(isPetDataEdited){
			return Response.ok("{\n" + "\"Status\":true\n" + "\"Message\":\"Pet Updated Successfully!\"\n" + "}").build();
		}else{
			return Response.ok("{\n" + "\"Status\":false\n" + "\"Message\":\"Pet Update Failed!\"\n" + "}").build();
		}

	}


	@Path("/search-pet")
	@Produces("application/json")
	@GET
	public Response searchPetInStore(@DefaultValue("-1") @QueryParam("id") int petId,
							  @DefaultValue("null") @QueryParam("name") String petName,
							  @DefaultValue("0") @QueryParam("age") int petAge){
		boolean isPetInStore = false;
		int id = 0;

		if(petId != -1 && petName.equals("null") && petAge == 0){

			if (petId < 0) {
				return Response.status(Status.NOT_FOUND).build();
			}

			for (int i=0;i<TempPetData.getInstance().getArrayList().size();i++){
				if(petId == TempPetData.getInstance().getArrayList().get(i).getPetId()){
					isPetInStore = true;
					id = i;
				}
			}

			if(isPetInStore){
				return Response.ok(TempPetData.getInstance().getArrayList().get(id)).build();
			}else{
				return Response.ok("There is no pet with id = "+petId).build();
			}

		}else if(petId == -1 && !petName.equals("null") && petAge == 0){

			for (int i=0;i<TempPetData.getInstance().getArrayList().size();i++){
				if(petName.equals(TempPetData.getInstance().getArrayList().get(i).getPetName())){
					isPetInStore = true;
					id = i;
				}
			}

			if(isPetInStore){
				return Response.ok(TempPetData.getInstance().getArrayList().get(id)).build();
			}else{
				return Response.ok("There is no pet with name = "+petName).build();
			}

		}else if(petId == -1 && petName.equals("null") && petAge != 0){

			List<Pet> temp = new ArrayList<Pet>();

			for (int i=0;i<TempPetData.getInstance().getArrayList().size();i++){
				if(petAge == TempPetData.getInstance().getArrayList().get(i).getPetAge()){
					isPetInStore = true;
					id = i;
					temp.add(TempPetData.getInstance().getArrayList().get(id));
				}
			}
			if(isPetInStore){
				return Response.ok(temp).build();
			}else{
				return Response.ok("There is no pet with age = "+petAge).build();
			}
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}


	}
}
